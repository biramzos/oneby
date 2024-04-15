package com.web.oneby.modules.users.Services;

import com.web.oneby.commons.DTOs.PageObject;
import com.web.oneby.commons.DTOs.SearchFilter;
import com.web.oneby.commons.Enums.HTTPMessage;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Enums.LogType;
import com.web.oneby.commons.Handlers.HTTPMessageHandler;
import com.web.oneby.commons.Services.EmailService;
import com.web.oneby.commons.Utils.ConstantsUtil;
import com.web.oneby.commons.Utils.LogUtil;
import com.web.oneby.commons.Utils.SortingUtil;
import com.web.oneby.commons.Utils.TokenUtil;
import com.web.oneby.modules.users.DTOs.CreateUserRequest;
import com.web.oneby.modules.users.DTOs.UserResponse;
import com.web.oneby.modules.users.Enums.UserRole;
import com.web.oneby.modules.users.Models.User;
import com.web.oneby.modules.users.Repositories.UserRepository;
import com.web.oneby.modules.users.Specifications.UserSpecification;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private static UserRepository userRepository;
    private static PasswordEncoder passwordEncoder;
    private EmailService emailService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ){
        UserService.userRepository = userRepository;
        UserService.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public PageObject<UserResponse> search(SearchFilter request, Language language) {
        Pageable pageable = PageRequest.of(
                request.getPageNumber() - 1,
                request.getCountInPage(),
                Sort.by(SortingUtil.getSortingOrders(request.getSort(), language)));
        return new PageObject<>(userRepository
                .findAll(UserSpecification.getSpecification(request.getFilter()), pageable)
                .map(user -> UserResponse.fromUser(user, language)));
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        try {
            return userRepository.getUserByUsername(username);
        } catch (Exception e) {
            LogUtil.write(e);
            return null;
        }
    }

    public boolean create(CreateUserRequest createUserRequest, HTTPMessageHandler messageHandler, int language) {
        if (
                userRepository.findByUsername(createUserRequest.getUsername()).isPresent() &&
                userRepository.findByEmail(createUserRequest.getEmail()).isEmpty()
        ) {
            LogUtil.write(HTTPMessage.USERNAME_IS_EXIST.getMessageEN(), LogType.ERROR);
            messageHandler.set(HTTPMessage.USERNAME_IS_EXIST, language);
            return false;
        } else if (
                userRepository.findByUsername(createUserRequest.getUsername()).isEmpty() &&
                userRepository.findByEmail(createUserRequest.getEmail()).isPresent()
        ) {
            LogUtil.write(HTTPMessage.EMAIL_IS_EXIST.getMessageEN(), LogType.ERROR);
            messageHandler.set(HTTPMessage.EMAIL_IS_EXIST, language);
            return false;
        } else if (
                userRepository.findByUsername(createUserRequest.getUsername()).isPresent() &&
                userRepository.findByEmail(createUserRequest.getEmail()).isPresent()
        ) {
            LogUtil.write(HTTPMessage.USER_IS_EXIST.getMessageEN(), LogType.ERROR);
            messageHandler.set(HTTPMessage.USER_IS_EXIST, language);
            return false;
        }
        else {
            emailService.send(ConstantsUtil.getHostName() + "/api/v1/auth/confirm/" + TokenUtil.generateAccessTokenByUsername(createUserRequest.getUsername()), createUserRequest.getEmail());
            try {
                byte [] image = null;
                if (createUserRequest.getImage() == null) {
                    InputStream inputStream = getClass().getResourceAsStream("/static/images/userDefault.png");
                    if (inputStream != null) {
                        image = inputStream.readAllBytes();
                    }
                } else {
                    image = createUserRequest.getImage().getBytes();
                }
                LogUtil.write(HTTPMessage.SUCCESSFULLY_REGISTERED.getMessageEN(), LogType.INFO);
                messageHandler.set(HTTPMessage.SUCCESSFULLY_REGISTERED, language);
                userRepository.save(
                    new User(
                        createUserRequest.getNameKK(),
                        createUserRequest.getNameRU(),
                        createUserRequest.getNameEN(),
                        createUserRequest.getLastnameKK(),
                        createUserRequest.getLastnameRU(),
                        createUserRequest.getLastnameEN(),
                        createUserRequest.getUsername(),
                        createUserRequest.getEmail(),
                        passwordEncoder.encode(createUserRequest.getPassword()),
                        List.of(UserRole.USER),
                        image,
                        false
                    )
                );
                return true;
            } catch (Exception e) {
                LogUtil.write(e);
                return false;
            }
        }
    }

    public boolean onInit() {
        try {
            InputStream inputStream = new FileInputStream(ConstantsUtil.IMAGES_DIRECTORY + "userDefault.png");
            boolean isAdminExist = UserService.userRepository.countAllByRolesContaining(UserRole.ADMIN) > 0;
            if (!isAdminExist) {
                User user = new User(
                        ConstantsUtil.ADMIN_NAME_KZ,
                        ConstantsUtil.ADMIN_NAME_RU,
                        ConstantsUtil.ADMIN_NAME_EN,
                        ConstantsUtil.ADMIN_LASTNAME_KZ,
                        ConstantsUtil.ADMIN_LASTNAME_RU,
                        ConstantsUtil.ADMIN_LASTNAME_EN,
                        ConstantsUtil.ADMIN_USERNAME,
                        ConstantsUtil.ADMIN_EMAIL,
                        passwordEncoder.encode(ConstantsUtil.ADMIN_PASSWORD),
                        List.of(UserRole.ADMIN),
                        inputStream.readAllBytes(),
                        true
                );
                userRepository.save(user);
                LogUtil.write("Admin is created!", LogType.INFO);
                return true;
            }
            LogUtil.write("Admin is exist!", LogType.WARNING);
            return false;
        } catch (Exception e) {
            LogUtil.write(e);
            return false;
        }
    }

    public User getById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User '#" + id + "' not found!"));
    }

    public void confirm(String token, HTTPMessageHandler messageHandler, int language){
        Optional<User> user = userRepository.findByToken(token);
        if (user.isPresent()) {
            if (user.get().isActive()) {
                LogUtil.write(HTTPMessage.USER_ALREADY_CONFIRMED.getMessageEN(), LogType.INFO);
                messageHandler.set(HTTPMessage.USER_ALREADY_CONFIRMED, language);
            } else {
                user.get().setActive(true);
                userRepository.save(user.get());
                LogUtil.write(HTTPMessage.USER_CONFIRMED.getMessageEN(), LogType.INFO);
                messageHandler.set(HTTPMessage.USER_CONFIRMED, language);
            }
        } else {
            LogUtil.write(HTTPMessage.USER_NOT_CONFIRMED.getMessageEN(), LogType.WARNING);
            messageHandler.set(HTTPMessage.USER_NOT_CONFIRMED, language);
        }
    }
}
