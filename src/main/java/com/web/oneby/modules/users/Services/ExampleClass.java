package com.web.oneby.modules.users.Services;

import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Utils.ClassUtil;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Component
@Data
public class ExampleClass  {

    private String nameKK = "nameKK";
    private String nameRU = "nameRU";
    private String nameEN = "nameEN";
    int age = 15;

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }
}
