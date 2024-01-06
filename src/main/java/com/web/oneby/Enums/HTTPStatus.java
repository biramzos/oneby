package com.web.oneby.Enums;

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

    private String icon;
    private String color;
}
