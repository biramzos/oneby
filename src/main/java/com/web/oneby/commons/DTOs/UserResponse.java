package com.web.oneby.commons.DTOs;

import com.web.oneby.commons.Models.User;
import com.web.oneby.commons.Utils.StringUtil;
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
    private String roles;
    private String token;
    private String image;
    private String lastnameEN;

    public static UserResponse fromUser(User user, int language) {
        UserResponse userResponse = new UserResponse();
        userResponse.id = user.getId();
        userResponse.fullname = StringUtil.firstCapitalLetter(user.getLastname(language))
                + " " + StringUtil.firstCapitalLetter(user.getName(language));
        userResponse.shortname = StringUtil.firstCapitalLetter(user.getLastname(language))
                + " " + user.getName(language).toUpperCase().charAt(1) + ".";
        userResponse.username = user.getUsername();
        userResponse.email = user.getEmail();
        userResponse.token = user.getToken();
        userResponse.roles = user.getRole().getName(language);
        userResponse.image = "/api/v1/auth/images/" + user.getId();
        return userResponse;
    }
}
