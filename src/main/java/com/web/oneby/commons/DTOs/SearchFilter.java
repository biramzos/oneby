package com.web.oneby.commons.DTOs;

import lombok.Data;
import org.springframework.data.domain.Sort.Direction;

import java.util.Map;

@Data
public class SearchFilter {
    private int pageNumber;
    private int countInPart;
    private Map<String, Object> filter;
    private Map<String, Direction> sort;
}
