package com.web.oneby.commons.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDataObject {
    private int type;
    private Map<String, String> data = new HashMap<>();
}
