package com.web.oneby.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    @NotEmpty(message = "")
    private String nameKZ;
    private String nameRU;
    private String nameEN;
    private String descriptionKZ;
    private String descriptionRU;
    private String descriptionEN;
    private String authorKZ;
    private String authorRU;
    private String authorEN;
    private int year;
    private List<Integer> genres;
    private MultipartFile image;
    private MultipartFile file;
}
