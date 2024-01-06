package com.web.oneby.Services;

import com.web.oneby.DTO.CreateUserRequest;
import com.web.oneby.DTO.PageObject;
import com.web.oneby.DTO.UserResponse;
import com.web.oneby.DTO.UserSearchFilterRequest;
import com.web.oneby.Enums.HTTPMessage;
import com.web.oneby.Enums.UserRole;
import com.web.oneby.Handlers.HTTPMessageHandler;
import com.web.oneby.Models.User;
import com.web.oneby.Repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final String SECRETKEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    @Value("${env.url}")
    private String host;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService,
            JdbcTemplate jdbcTemplate
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public PageObject<UserResponse> search(UserSearchFilterRequest request, Integer sizeInPart, Integer pageNumber, int language){
        return new PageObject<>(userRepository.findAll(request, Pageable.ofSize(sizeInPart).withPage(pageNumber - 1)).map(user -> UserResponse.fromUser(user, language)));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found!"));
    }

    public User create(CreateUserRequest createUserRequest, HTTPMessageHandler messageHandler, int language) throws IOException {
        if (
                userRepository.findByUsername(createUserRequest.getUsername()).isPresent() &&
                userRepository.findByEmail(createUserRequest.getEmail()).isEmpty()
        ) {
            messageHandler = HTTPMessageHandler.fromHTTPMessage(HTTPMessage.USERNAME_IS_EXIST, language);
            throw new RuntimeException("Username is exist!");
        } else if (
                userRepository.findByUsername(createUserRequest.getUsername()).isEmpty() &&
                userRepository.findByEmail(createUserRequest.getEmail()).isPresent()
        ) {
            messageHandler = HTTPMessageHandler.fromHTTPMessage(HTTPMessage.EMAIL_IS_EXIST, language);
            throw new RuntimeException("Email is exist!");
        } else if (
                userRepository.findByUsername(createUserRequest.getUsername()).isPresent() &&
                userRepository.findByEmail(createUserRequest.getEmail()).isPresent()
        ) {
            messageHandler = HTTPMessageHandler.fromHTTPMessage(HTTPMessage.USER_IS_EXIST, language);
            throw new RuntimeException("User is exist!");
        }
        else {
            emailService.send(host + "/api/v1/auth/confirm/" + generateToken(createUserRequest.getUsername()), createUserRequest.getEmail());

            byte [] image = null;
            if (createUserRequest.getImage() == null) {
                InputStream inputStream = getClass().getResourceAsStream("/static/userDefault.png");
                if (inputStream != null) {
                    image = inputStream.readAllBytes();
                }
            } else {
                image = createUserRequest.getImage().getBytes();
            }
            messageHandler = HTTPMessageHandler.fromHTTPMessage(HTTPMessage.SUCCESSFULLY_REGISTERED, language);

            return userRepository.save (
                new User (
                    createUserRequest.getUsername(),
                    createUserRequest.getEmail(),
                    passwordEncoder.encode(createUserRequest.getPassword()),
                    generateToken(createUserRequest.getUsername()),
                    List.of(UserRole.USER),
                    image,
                    false
                )
            );
        }
    }



    public String generateToken(String username){
        return Jwts
                .builder()
                .claim("username", username)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, SECRETKEY)
                .compact();
    }

    public User getById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User '#" + id + "' not found!"));
    }

    public boolean confirm(String token, HTTPMessageHandler messageHandler, int language){
        Optional<User> user = userRepository.findByToken(token);
        if (user.isPresent()) {
            user.get().setActive(true);
            userRepository.save(user.get());
            messageHandler = HTTPMessageHandler.fromHTTPMessage(HTTPMessage.USER_CONFIRMED, language);
            return true;
        }
        messageHandler = HTTPMessageHandler.fromHTTPMessage(HTTPMessage.USER_NOT_CONFIRMED, language);
        return false;
    }

    public String getImage (Long userId) {
        String sql = "SELECT image FROM users WHERE id = " + userId;
        String result = jdbcTemplate.query(sql, (ResultSet resultSet,  int index) -> {
            return resultSet.getString("image");
        }).toString();
        return result;
    }
}
