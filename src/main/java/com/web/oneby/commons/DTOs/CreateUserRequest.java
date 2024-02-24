package com.web.oneby.commons.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String nameKK;
    private String nameRU;
    private String nameEN;
    private String lastnameKK;
    private String lastnameRU;
    private String lastnameEN;
    private String username;
    private String email;
    private String password;
    private MultipartFile image;

}
