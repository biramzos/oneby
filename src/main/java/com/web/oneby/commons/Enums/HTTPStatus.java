package com.web.oneby.commons.Enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HTTPStatus {
    SUCCESS,
    WARNING,
    ERROR;

    @JsonValue
    public Object serialize() {
        return new Object() {
            @JsonProperty("status")
            String status = name();
        };
    }
}
