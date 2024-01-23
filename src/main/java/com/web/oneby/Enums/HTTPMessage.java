package com.web.oneby.Enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HTTPMessage {

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
    USER_ALREADY_CONFIRMED(
            "Пайдаланушы расталған!",
            "Пользователь уже подтвержден!",
            "User has been already confirmed!",
            HTTPStatus.WARNING),
    USER_NOT_CONFIRMED(
            "Расталмады!",
            "Не удалось подтвердить!",
            "Failed to confirm!",
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
    SUCCESSFULLY_LOGIN(
            "Сәтті кірдіңіз!",
            "Успешно авторизовался!",
            "Successfully signed in!",
            HTTPStatus.SUCCESS),
    BOOK_IS_DELETED(
            "Кітап жойылды!",
            "Книга удалена!",
            "Book is deleted!",
            HTTPStatus.SUCCESS),
    BOOK_IS_NOT_DELETED(
            "Кітап жойылмайды!",
            "Книга не удалена!",
            "Book is not deleted!",
            HTTPStatus.ERROR),
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
    USER_IS_DISABLED(
            "Тіркелгі ажыратылады. Оны белсендіруіңізді сұраймыз!",
            "Учетная запись отключена. Пожалуйста, активируйте его!",
            "Account is disabled. Please activate it!",
            HTTPStatus.ERROR),
    ENTITY_IS_NOT_FOUND(
            "Табылмады!",
            "Не найдено!",
            "Not found!",
            HTTPStatus.ERROR),
    FAILED_AUTHENTICATION(
            "Аутентификациясы болмады!",
            "Не удалось пройти аутентификацию!",
            "Failed authentication!",
            HTTPStatus.ERROR),
    NULL_POINTER(
            "Объект табылмады немесе танылмады!",
            "Объект не найден или не распознан!",
            "The object is not found or not recognized!",
            HTTPStatus.ERROR),
    NO_RESOURCES_FOUND(
            "Ресурстар табылмады!",
            "Не нашли ресурсов!",
            "Not found the resources!",
            HTTPStatus.ERROR),
    CANNOT_PARSE_DATA(
            "Деректерді талдау мүмкін емес!",
            "Невозможно проанализировать данные!",
            "Cannot parse the data!",
            HTTPStatus.ERROR),
    ORGANIZATION_IS_EXIST(
            "Ұйым бұрыннан бар!",
            "Организация уже существует!",
            "Organization has already exist!",
            HTTPStatus.ERROR),
    USER_IS_NOT_SELLER(
            "Пайдаланушы сатушы емес!",
            "Пользователь не является продавцом!",
            "User is not seller!",
            HTTPStatus.ERROR),
    ERROR(
            "Ошибка!",
            "Қате!",
            "Error!",
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
