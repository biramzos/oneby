package com.web.oneby.users.Service;

import com.web.oneby.commons.DTOs.PageObject;
import com.web.oneby.commons.DTOs.SearchFilter;
import com.web.oneby.modules.users.DTOs.UserResponse;
import com.web.oneby.modules.users.Models.User;
import com.web.oneby.modules.users.Services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    void filterTest() {
        SearchFilter filter = new SearchFilter(1, 2, new HashMap<>(), new HashMap<>());
        PageObject<UserResponse> users = userService.search(filter, 1);
        System.out.println(users.getTotalSize());
    }
}
