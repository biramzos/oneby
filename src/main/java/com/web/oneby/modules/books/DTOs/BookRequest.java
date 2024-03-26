package com.web.oneby.modules.books.DTOs;

import com.web.oneby.commons.Enums.Language;
import com.web.oneby.modules.books.Enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private Long id;
    private String titleKK;
    private String titleRU;
    private String titleEN;
    private String descriptionKK;
    private String descriptionRU;
    private String descriptionEN;
    private String authorKK;
    private String authorRU;
    private String authorEN;
    private Language language;
    private List<Genre> genres;
    private MultipartFile image;
    private MultipartFile file;
}
