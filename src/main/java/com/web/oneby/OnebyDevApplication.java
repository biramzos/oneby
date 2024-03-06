package com.web.oneby;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnebyDevApplication
        implements CommandLineRunner
{

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
