package com.web.oneby.commons.Handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.web.oneby.commons.Enums.HTTPMessage;
import com.web.oneby.commons.Enums.HTTPStatus;
import com.web.oneby.commons.Enums.Language;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HTTPMessageHandler {
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private HTTPStatus status;

    public void set(HTTPMessage message, int language) {
        this.title = message.getMessage(language);
        this.status = message.getStatus();
    }

    public void set(HTTPMessage message, Language language) {
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

    public HTTPMessageHandler(
            HTTPMessage message,
            Language language
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

    public static HTTPMessageHandler fromHTTPMessage(HTTPMessage message, Language language){
        HTTPMessageHandler messageHandler = new HTTPMessageHandler();
        messageHandler.title = message.getMessage(language);
        messageHandler.status = message.getStatus();
        return messageHandler;
    }
}
