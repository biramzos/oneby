package com.web.oneby.modules.users.Services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class InstanceClass extends ClassInitil{

    String simpleName = getClass().getSimpleName();
    int count = 17;

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
