package com.web.oneby.DTO;

import lombok.Data;

import java.util.Map;

@Data
public class SearchFilter {
    private int pageNumber;
    private int countInPart;
    private Map<String, Object> filter;
    private Map<String, String> sort;
}
