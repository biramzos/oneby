package com.web.oneby.Enums;

public enum HTTPMessage {
    //Register
    SUCCESSFULLY_REGISTERED(
            "",
            "",
            "Successfully signed up!",
            HTTPStatus.SUCCESS),
    USER_CONFIRMED(
            "",
            "",
            "Successfully confirmed!",
            HTTPStatus.SUCCESS),
    USER_NOT_CONFIRMED(
            "",
            "",
            "Failed!",
            HTTPStatus.ERROR),
    USERNAME_IS_EXIST(
            "",
            "",
            "Username is exist!",
            HTTPStatus.ERROR),
    EMAIL_IS_EXIST(
            "",
            "",
            "Email is exist!",
            HTTPStatus.ERROR),
    USER_IS_EXIST(
            "",
            "",
            "User is exist!",
            HTTPStatus.ERROR),
    //Login
    SUCCESSFULLY_LOGIN(
            "",
            "",
            "Successfully signed in!",
            HTTPStatus.SUCCESS),
    USER_IS_NOT_EXIST(
            "",
            "",
            "User is not exit!",
            HTTPStatus.ERROR),
    PASSWORD_IS_WRONG(
            "",
            "",
            "Password is wrong!",
            HTTPStatus.ERROR),
    ;
    HTTPMessage(
        String messageKZ,
        String messageRU,
        String messageEN,
        HTTPStatus status
    ){
        this.messageKZ = messageKZ;
        this.messageRU = messageRU;
        this.messageEN = messageEN;
        this.status = status;
    }
    private String messageKZ;
    private String messageRU;
    private String messageEN;
    private HTTPStatus status;

    public String getMessageKZ() {
        return messageKZ;
    }

    public String getMessageRU() {
        return messageRU;
    }

    public String getMessageEN() {
        return messageEN;
    }

    public HTTPStatus getStatus() {
        return status;
    }

    public String getMessage(int language) {
        if (language == Language.kz.getId()) {
            return messageKZ;
        } else if (language == Language.ru.getId()) {
            return messageRU;
        } else {
            return messageEN;
        }
    }

}
