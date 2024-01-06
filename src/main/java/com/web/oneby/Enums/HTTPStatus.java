package com.web.oneby.Enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HTTPStatus {
    SUCCESS("fas fa-check-circle", "green"),
    WARNING("fas fa-exclamation-circle", "yellow"),
    ERROR("fas fa-times-circle", "red");

    HTTPStatus(
        String icon,
        String color
    ){
        this.icon = icon;
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public String getColor() {
        return color;
    }

    @JsonProperty("icon")
    private String icon;
    @JsonProperty("color")
    private String color;

    @JsonValue
    public Object serialize() {
        return new Object() {
            @JsonProperty("icon")
            String icon = getIcon();

            @JsonProperty("color")
            String color = getColor();
        };
    }
}
