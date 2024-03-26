package com.web.oneby.modules.users.DTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "CANNOT_BE_BLANK")
    private String nameRU;
    @NotBlank(message = "CANNOT_BE_BLANK")
    private String nameEN;
    @NotBlank(message = "CANNOT_BE_BLANK")
    private String lastnameRU;
    @NotBlank(message = "CANNOT_BE_BLANK")
    private String lastnameEN;
    @NotBlank(message = "CANNOT_BE_BLANK")
    private String username;
    @Email(message = "NEED_EMAIL")
    private String email;
    @Size(min = 6, message = "PASSWORD_NEEDS_TO_BE_MORE")
    private String password;
    @NotNull(message = "CANNOT_BE_NULL")
    private MultipartFile image;

}
