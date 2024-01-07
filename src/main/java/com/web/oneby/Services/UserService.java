package com.web.oneby.Services;

import com.web.oneby.DTO.CreateUserRequest;
import com.web.oneby.DTO.PageObject;
import com.web.oneby.DTO.UserResponse;
import com.web.oneby.DTO.UserSearchFilterRequest;
import com.web.oneby.Enums.HTTPMessage;
import com.web.oneby.Enums.Language;
import com.web.oneby.Enums.UserRole;
import com.web.oneby.Handlers.HTTPMessageHandler;
import com.web.oneby.Models.User;
import com.web.oneby.Repositories.UserRepository;
import com.web.oneby.Utils.StringUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
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
        return new PageObject<>(userRepository.findAll(UserService.filter(request), Pageable.ofSize(sizeInPart).withPage(pageNumber - 1)).map(user -> UserResponse.fromUser(user, language)));
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
            messageHandler.set(HTTPMessage.USERNAME_IS_EXIST, language);
            return null;
        } else if (
                userRepository.findByUsername(createUserRequest.getUsername()).isEmpty() &&
                userRepository.findByEmail(createUserRequest.getEmail()).isPresent()
        ) {
            messageHandler.set(HTTPMessage.EMAIL_IS_EXIST, language);
            return null;
        } else if (
                userRepository.findByUsername(createUserRequest.getUsername()).isPresent() &&
                userRepository.findByEmail(createUserRequest.getEmail()).isPresent()
        ) {
            messageHandler.set(HTTPMessage.USER_IS_EXIST, language);
            return null;
        }
        else {
            emailService.send(host + "/api/v1/auth/confirm/" + generateToken(createUserRequest.getUsername())  + "/" + Language.getLanguageById(language).name(), createUserRequest.getEmail());

            byte [] image = null;
            if (createUserRequest.getImage() == null) {
                InputStream inputStream = getClass().getResourceAsStream("/static/userDefault.png");
                if (inputStream != null) {
                    image = inputStream.readAllBytes();
                }
            } else {
                image = createUserRequest.getImage().getBytes();
            }
            messageHandler.set(HTTPMessage.SUCCESSFULLY_REGISTERED, language);

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

    public void confirm(String token, HTTPMessageHandler messageHandler, int language){
        Optional<User> user = userRepository.findByToken(token);
        if (user.isPresent()) {
            if (user.get().isActive()) {
                messageHandler.set(HTTPMessage.USER_ALREADY_CONFIRMED, language);
            } else {
                user.get().setActive(true);
                userRepository.save(user.get());
                messageHandler.set(HTTPMessage.USER_CONFIRMED, language);
            }

        } else {
            messageHandler.set(HTTPMessage.USER_NOT_CONFIRMED, language);
        }
    }

    public String getImage (Long userId) {
        String sql = "SELECT image FROM users WHERE id = " + userId;
        String result = jdbcTemplate.query(sql, (ResultSet resultSet,  int index) -> {
            return resultSet.getString("image");
        }).toString();
        return result;
    }

    public static Specification<User> filter(UserSearchFilterRequest request){
        return ((root, query, criteriaBuilder) -> {
            Predicate predication = criteriaBuilder.conjunction();
            if (StringUtil.isNotEmpty(request.getName())) {
                predication = criteriaBuilder.or(
                    criteriaBuilder.like(
                        root.get("username"), "%" + request.getName() + "%"
                    ),
                    criteriaBuilder.like(
                        root.get("email"), "%" + request.getName() + "%"
                    )
                );
            }
            if (!request.getRoles().isEmpty()) {
                Predicate rolePredicate = root.join("roles").in(request.getRoles());
                predication = criteriaBuilder.and(predication, rolePredicate);
            }
            return predication;
        });
    }
}
