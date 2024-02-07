package com.web.oneby;

import com.web.oneby.commons.Enums.UserRole;
import com.web.oneby.commons.Models.User;
import com.web.oneby.commons.Repositories.UserRepository;
import com.web.oneby.commons.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class OnebyDevApplication implements CommandLineRunner {

    @Autowired
    public OnebyDevApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OnebyDevApplication.class, args);
    }

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.onInit();
    }
}
