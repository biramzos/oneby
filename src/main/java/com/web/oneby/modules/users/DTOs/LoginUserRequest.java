package com.web.oneby.modules.users.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequest {
    @NotBlank(message = "CANNOT_BE_BLANK")
    private String username;
    @Size(min = 6, message = "PASSWORD_NEEDS_TO_BE_MORE")
    private String password;
}
