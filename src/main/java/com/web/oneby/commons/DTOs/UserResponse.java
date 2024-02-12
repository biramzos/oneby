package com.web.oneby.commons.DTOs;

import com.web.oneby.commons.Models.User;
import com.web.oneby.commons.Utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

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
        userResponse.fullname = StringUtil.firstCapitalLetter(user.getLastname(language))
                + " " + StringUtil.firstCapitalLetter(user.getName(language));
        userResponse.shortname = StringUtil.firstCapitalLetter(user.getLastname(language))
                + " " + user.getName(language).toUpperCase().charAt(0) + ".";
        userResponse.username = user.getUsername();
        userResponse.email = user.getEmail();
        userResponse.token = user.getToken();
        userResponse.roles = user.getRoles().stream()
                .map((role) -> role.getName(language)).toList();
        userResponse.image = "/api/v1/auth/images/" + user.getId();
        userResponse.isActivated = user.isActive();
        return userResponse;
    }
}
