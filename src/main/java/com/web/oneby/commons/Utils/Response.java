package com.web.oneby.commons.Utils;

import java.util.HashMap;

public class Response extends HashMap<String, Object> {

    public static Response getResponse(String name, Object object) {
        return new Response() {{
            put(name, object);
        }};
    }
}
