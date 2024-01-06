package com.web.oneby.Handlers;

import com.web.oneby.Enums.HTTPMessage;
import com.web.oneby.Enums.HTTPStatus;
import lombok.Data;

@Data
public class HTTPMessageHandler {
    private String name;
    private HTTPStatus status;

    public static HTTPMessageHandler fromHTTPMessage(HTTPMessage message, int language){
        HTTPMessageHandler messageHandler = new HTTPMessageHandler();
        messageHandler.name = message.getMessage(language);
        messageHandler.status = message.getStatus();
        return messageHandler;
    }
}
