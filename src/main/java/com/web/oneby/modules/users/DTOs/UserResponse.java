package com.web.oneby.modules.users.DTOs;

import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Utils.StringUtil;
import com.web.oneby.modules.users.Models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String fullname;
    private String shortname;
    private String username;
    private String email;
    private List<String> roles;
    private String token;
    private String image;
    private boolean isActivated;


    public static UserResponse fromUser(User user, int language) {
        UserResponse userResponse = new UserResponse();
        userResponse.id = user.getId();
        userResponse.fullname = StringUtil.getFullName(user.getName(language), user.getLastname(language));
        userResponse.shortname = StringUtil.getFullNameShort(user.getName(language), user.getLastname(language));
        userResponse.username = user.getUsername();
        userResponse.email = user.getEmail();
        userResponse.token = user.getToken();
        userResponse.roles = user.getRoles().stream().map((role) -> role.getName(language)).toList();
        userResponse.image = "/api/v1/auth/images/" + user.getId();
        userResponse.isActivated = user.isActive();
        return userResponse;
    }

    public static UserResponse fromUser(User user, Language language) {
        UserResponse userResponse = new UserResponse();
        userResponse.id = user.getId();
        userResponse.fullname = StringUtil.getFullName(user.getName(language), user.getLastname(language));
        userResponse.shortname = StringUtil.getFullNameShort(user.getName(language), user.getLastname(language));
        userResponse.username = user.getUsername();
        userResponse.email = user.getEmail();
        userResponse.token = user.getToken();
        userResponse.roles = user.getRoles().stream().map((role) -> role.getName(language)).toList();
        userResponse.image = "/api/v1/auth/images/" + user.getId();
        userResponse.isActivated = user.isActive();
        return userResponse;
    }
}
