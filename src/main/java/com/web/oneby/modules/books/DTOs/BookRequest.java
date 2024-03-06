package com.web.oneby.modules.books.DTOs;

import com.web.oneby.modules.books.Enums.AccessBook;
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
    private String nameKK;
    private String nameRU;
    private String nameEN;
    private String descriptionKZ;
    private String descriptionRU;
    private String descriptionEN;
    private String authorKZ;
    private String authorRU;
    private String authorEN;
    private int year;
    private AccessBook access;
    private int cost;
    private List<Genre> genres;
    private MultipartFile image;
    private MultipartFile file;
}
