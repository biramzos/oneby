package com.web.oneby.DTO;

import com.web.oneby.Models.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationRequest {
    private String name;
    private User user;
    private MultipartFile image;
}
