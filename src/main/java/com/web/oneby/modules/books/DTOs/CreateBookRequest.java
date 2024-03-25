package com.web.oneby.modules.books.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {
    private String titleKK;
    private String titleRU;
    private String titleEN;
    private String descriptionKK;
    private String descriptionRU;
    private String descriptionEN;
    private String authorKK;
    private String authorRU;
    private String authorEN;
}
