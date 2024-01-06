package com.web.oneby.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class PageObject<T> {
    private List<T> pageContent;
    private int totalSize;

    public PageObject (
        Page<T> pageObject
    ) {
        this.pageContent = pageObject.getContent();
        this.totalSize = (int) pageObject.getTotalElements();
    }
}
