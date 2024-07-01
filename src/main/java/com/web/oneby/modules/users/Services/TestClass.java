package com.web.oneby.modules.users.Services;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TestClass extends ClassInitil {

    String superName = getClass().getSimpleName();
    int number = 13;

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
