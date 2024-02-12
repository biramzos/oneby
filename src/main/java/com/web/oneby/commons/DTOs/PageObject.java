package com.web.oneby.commons.DTOs;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class PageObject<T> {
    private List<T> pageContent;
    private long totalSize;

    public PageObject (
        Page<T> pageObject
    ) {
        this.pageContent = pageObject.getContent();
        this.totalSize = pageObject.getTotalElements();
    }
}
