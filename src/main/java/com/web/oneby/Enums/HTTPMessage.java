package com.web.oneby.Enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HTTPMessage {
    //Register
    SUCCESSFULLY_REGISTERED(
            "Сәтті тіркелу!",
            "Успешная регистрация!",
            "Successfully signed up!",
            HTTPStatus.SUCCESS),
    USER_CONFIRMED(
            "Сәтті расталды!",
            "Успешно подтверждено!",
            "Successfully confirmed!",
            HTTPStatus.SUCCESS),
    USER_NOT_CONFIRMED(
            "Жаңылыс!",
            "Неудавшийся!",
            "Failed!",
            HTTPStatus.ERROR),
    USERNAME_IS_EXIST(
            "Пайдаланушы аты бар!",
            "Имя пользователя существует!",
            "Username is exist!",
            HTTPStatus.ERROR),
    EMAIL_IS_EXIST(
            "Электрондық пошта бар!",
            "Электронная почта существует!",
            "Email is exist!",
            HTTPStatus.ERROR),
    USER_IS_EXIST(
            "Пайдаланушы бар!",
            "Пользователь существует!",
            "User is exist!",
            HTTPStatus.ERROR),
    //Login
    SUCCESSFULLY_LOGIN(
            "Сәтті кірдіңіз!",
            "Успешно авторизовался!",
            "Successfully signed in!",
            HTTPStatus.SUCCESS),
    USER_IS_NOT_EXIST(
            "Пайдаланушы жоқ!",
            "Пользователя не существует!",
            "User is not exit!",
            HTTPStatus.ERROR),
    USERNAME_OR_PASSWORD_IS_WRONG(
            "Пайдаланушы аты немесе құпия сөз дұрыс емес!",
            "Имя пользователя или пароль неверны!",
            "Username or password is wrong!",
            HTTPStatus.ERROR),
    ACCESS_DENIED(
            "Қатынасудан бас тартылды!",
            "Доступ запрещен!",
            "Access denied!",
            HTTPStatus.ERROR),
    ENTITY_IS_NOT_FOUND(
            "Табылмады!",
            "Не найдено!",
            "Not found!",
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

    @JsonValue
    public Object serialize() {
        return new Object() {
            @JsonProperty("messageEN")
            String messageEN = getMessageEN();
            @JsonProperty("messageKZ")
            String messageKZ = getMessageKZ();
            @JsonProperty("messageRU")
            String messageRU = getMessageRU();

            @JsonProperty("status")
            HTTPStatus status = HTTPMessage.this.status;
        };
    }

}
