package com.web.oneby.modules.users.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String nameRU;
    private String nameEN;
    private String lastnameRU;
    private String lastnameEN;
    private String username;
    private String email;
    private String password;
    private MultipartFile image;

}
