package com.web.oneby.commons.Enums;

public enum FileType {
    PDF(".pdf"),
    DOCX(".docx"),
    DOC(".doc"),
    ;

    private final String format;

    FileType (String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
