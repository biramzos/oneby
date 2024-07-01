package com.web.oneby.commons.DTOs;

import java.util.HashMap;

public class Response extends HashMap<String, Object> {

    public static Response getResponse(String name, Object object) {
        return new Response() {{
            put(name, object);
        }};
    }

    public static Response getResponse(Field... objects) {
        return new Response() {{
            for(Field object : objects) {
                put(object.name, object.value);
            }
        }};
    }

}
