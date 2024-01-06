package com.web.oneby.DTO;

import lombok.Data;

import java.util.List;

@Data
public class BookSearchFilterRequest {
    private String name;
    private int year;
    private int stars;
    private List<Integer> genres;
}
