package com.web.oneby.commons.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.web.oneby.commons.Enums.HTTPMessage;
import com.web.oneby.commons.Enums.HTTPStatus;
import com.web.oneby.commons.Enums.Language;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageData {
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private HTTPStatus status;

    public MessageData(
            HTTPMessage message,
            Language language
    ) {
        this.title = message.getMessage(language);
        this.status = message.getStatus();
    }

    public static MessageData fromMessage(HTTPMessage message, Language language){
        MessageData messageHandler = new MessageData();
        messageHandler.title = message.getMessage(language);
        messageHandler.status = message.getStatus();
        return messageHandler;
    }
    public void set(HTTPMessage message, Language language) {
        this.title = message.getMessage(language);
        this.status = message.getStatus();
    }
}
