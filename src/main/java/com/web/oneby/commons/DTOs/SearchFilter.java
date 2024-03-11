package com.web.oneby.commons.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort.Direction;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchFilter {
    private int pageNumber;
    private int countInPage;
    private Map<String, Object> filter;
    private Map<String, Direction> sort;
}
