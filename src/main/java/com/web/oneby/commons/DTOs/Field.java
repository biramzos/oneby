package com.web.oneby.commons.DTOs;

public class Field {
    public String name;
    public Object value;

    public Field (String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public static Field get(String name, Object value) {
        return new Field(name, value);
    }
}
