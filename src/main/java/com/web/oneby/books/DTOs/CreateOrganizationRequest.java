package com.web.oneby.books.DTOs;

import com.web.oneby.commons.Models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
