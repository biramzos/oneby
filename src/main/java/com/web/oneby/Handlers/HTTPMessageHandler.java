package com.web.oneby.Handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.web.oneby.Enums.HTTPMessage;
import com.web.oneby.Enums.HTTPStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HTTPMessageHandler {
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private HTTPStatus status;

    public HTTPMessageHandler(
            String title,
            HTTPStatus status
    ) {
        this.title = title;
        this.status = status;
    }

    public void set(HTTPMessage message, int language) {
        this.title = message.getMessage(language);
        this.status = message.getStatus();
    }

    public HTTPMessageHandler(
            HTTPMessage message,
            int language
    ) {
        this.title = message.getMessage(language);
        this.status = message.getStatus();
    }


    public static HTTPMessageHandler fromHTTPMessage(HTTPMessage message, int language){
        HTTPMessageHandler messageHandler = new HTTPMessageHandler();
        messageHandler.title = message.getMessage(language);
        messageHandler.status = message.getStatus();
        return messageHandler;
    }
}
