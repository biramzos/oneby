package com.web.oneby.DTO;

import com.web.oneby.Models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String roles;
    private String token;
    private String image;

    public static UserResponse fromUser(User user, int language) {
        UserResponse userResponse = new UserResponse();
        userResponse.id = user.getId();
        userResponse.username = user.getUsername();
        userResponse.email = user.getEmail();
        userResponse.token = user.getToken();
        userResponse.roles = String.join(", ", user.getRoles().stream().map(role -> role.getName(language)).toList());
        userResponse.image = "/api/v1/auth/images/" + user.getId();
        return userResponse;
    }
}
