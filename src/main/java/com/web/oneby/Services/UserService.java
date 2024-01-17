package com.web.oneby.Services;

import com.web.oneby.DTO.*;
import com.web.oneby.Enums.HTTPMessage;
import com.web.oneby.Enums.Language;
import com.web.oneby.Enums.UserRole;
import com.web.oneby.Handlers.HTTPMessageHandler;
import com.web.oneby.Models.User;
import com.web.oneby.Repositories.UserRepository;
import com.web.oneby.Utils.SortingUtil;
import com.web.oneby.Utils.StringUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final String SECRET_KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
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

    public PageObject<UserResponse> search(SearchFilter request, int language) {
        List<Sort.Order> orders = SortingUtil.getSortingOrders(request.getSort());
        Pageable pageable = PageRequest.of(
                request.getPageNumber() - 1,
                request.getCountInPart(),
                Sort.by(orders));
        return new PageObject<>(userRepository
                .findAll(UserService.filter(request.getFilter()), pageable)
                .map(user -> UserResponse.fromUser(user, language)));
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
            log.error(HTTPMessage.USERNAME_IS_EXIST.getMessageEN());
            messageHandler.set(HTTPMessage.USERNAME_IS_EXIST, language);
            return null;
        } else if (
                userRepository.findByUsername(createUserRequest.getUsername()).isEmpty() &&
                userRepository.findByEmail(createUserRequest.getEmail()).isPresent()
        ) {
            log.error(HTTPMessage.EMAIL_IS_EXIST.getMessageEN());
            messageHandler.set(HTTPMessage.EMAIL_IS_EXIST, language);
            return null;
        } else if (
                userRepository.findByUsername(createUserRequest.getUsername()).isPresent() &&
                userRepository.findByEmail(createUserRequest.getEmail()).isPresent()
        ) {
            log.error(HTTPMessage.USER_IS_EXIST.getMessageEN());
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
            log.info(HTTPMessage.SUCCESSFULLY_REGISTERED.getMessageEN());
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
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public User getById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User '#" + id + "' not found!"));
    }

    public void confirm(String token, HTTPMessageHandler messageHandler, int language){
        Optional<User> user = userRepository.findByToken(token);
        if (user.isPresent()) {
            if (user.get().isActive()) {
                log.info(HTTPMessage.USER_ALREADY_CONFIRMED.getMessageEN());
                messageHandler.set(HTTPMessage.USER_ALREADY_CONFIRMED, language);
            } else {
                user.get().setActive(true);
                userRepository.save(user.get());
                log.info(HTTPMessage.USER_CONFIRMED.getMessageEN());
                messageHandler.set(HTTPMessage.USER_CONFIRMED, language);
            }
        } else {
            log.info(HTTPMessage.USER_NOT_CONFIRMED.getMessageEN());
            messageHandler.set(HTTPMessage.USER_NOT_CONFIRMED, language);
        }
    }

    public static Specification<User> filter(Map<String, Object> filter){
        return ((root, query, criteriaBuilder) -> {
            Predicate predication = criteriaBuilder.conjunction();
            for (String key: filter.keySet()) {
                Object value = filter.get(key);
                if (key.equals("name") && StringUtil.isNotEmpty((String) value)) {
                    predication = criteriaBuilder.or(
                            criteriaBuilder.like(
                                    root.get("username"), "%" + (String) value + "%"
                            ),
                            criteriaBuilder.like(
                                    root.get("email"), "%" + (String) value + "%"
                            )
                    );
                }
                if (key.equals("roles") && !((List<String>) value).isEmpty()) {
                    Predicate rolePredicate = root.join("roles").in((List<String>) value);
                    predication = criteriaBuilder.and(predication, rolePredicate);
                }
            }
            return predication;
        });
    }
}
