package com.web.oneby.commons.Enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    CANNOT_BE_BLANK(
            "Бос болуы мүмкін емес!",
            "Не может быть пустым!",
            "Cannot be blank!",
            HTTPStatus.ERROR
    ),
    CANNOT_BE_EMPTY(
            "Бос болуы мүмкін емес!",
            "Не может быть пустым!",
            "Cannot be empty!",
            HTTPStatus.ERROR
    ),
    NEED_EMAIL(
            "Ол электрондық пошта болуы керек!",
            "Это должна быть электронная почта!",
            "It needs to be email!",
            HTTPStatus.ERROR)
    ,
    PASSWORD_NEEDS_TO_BE_MORE(
            "Құпия сөз өлшемі 6-дан көп болуы керек!",
            "Размер пароля должен быть больше 6!",
            "Password size needs to be more than 6!",
            HTTPStatus.ERROR
    ),
    CANNOT_BE_NULL(
            "Алаң бос болмауы керек!",
            "Поле не должно быть пустым!",
            "The field must not be empty!",
            HTTPStatus.ERROR
    ),
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
    SUCCESSFULLY_LOGOUT(
            "Жүйеден сәтті шықтыңыз!",
            "Успешно вышли из системы!",
            "Successfully logged out!",
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
    LANGUAGE_IS_NOT_EXIST(
            "Тіл жоқ!",
            "Языка не существует!",
            "Language is not exist!",
            HTTPStatus.ERROR),
    USER_NOT_FOUND(
            "Пайдаланушы табылмады!",
            "Пользователь не найден!",
            "User isn't found!",
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
    MISSING_REQUEST_HEADER(
            "Сұрау тақырыбы жіберіп алды!",
            "Заголовок запроса пропущен!",
            "Request header is missed!",
            HTTPStatus.ERROR),
    SUCCESSFULLY_UPLOADED(
            "Сәтті кері жүктелді!",
            "Успешно загружено!",
            "Successfully uploaded!",
            HTTPStatus.SUCCESS),
    ERROR_WHILE_UPLOADING(
            "Кері жүктеу кезінде қате!",
            "Ошибка при загрузке!",
            "Error while uploading!",
            HTTPStatus.ERROR),
    ERROR(
            "Қате!",
            "Ошибка!",
            "Error!",
            HTTPStatus.ERROR),
    ;


    HTTPMessage(
        String messageKK,
        String messageRU,
        String messageEN,
        HTTPStatus status
    ){
        this.messageKK = messageKK;
        this.messageRU = messageRU;
        this.messageEN = messageEN;
        this.status = status;
    }
    private String messageKK;
    private String messageRU;
    private String messageEN;
    private HTTPStatus status;

    public String getMessageKZ() {
        return messageKK;
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

    public String getMessage(Language language) {
        Map<String, String> names = new HashMap<>() {{
            put(Language.kk.suffix(), messageKK);
            put(Language.ru.suffix(), messageRU);
            put(Language.en.suffix(), messageEN);
        }};
        return names.get(language.suffix());
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
