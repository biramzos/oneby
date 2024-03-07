package com.web.oneby.commons.Enums;

public enum Template {
    CONTRACT_WORK_TOGETHER(1, "", "", ""),
    CONTRACT_EMPLOYEE(2, "", "", ""),
    CONTRACT_WITH_SHOPS(3, "", "", ""),
    CONTRACT_DEFAULT(4, "", "", ""),
    ;

    private int id;
    private String templateKK;
    private String templateRU;
    private String templateEN;

    Template (int id, String templateKK, String templateRU, String templateEN) {
        this.id = id;
        this.templateKK = templateKK;
        this.templateRU = templateRU;
        this.templateEN = templateEN;
    }

    public static Template getById (int id) {
        Template template = null;
        for (Template t: values()) {
            if (t.getId() == id) {
                template = t;
                break;
            }
        }
        return template;
    }


    public int getId() {
        return id;
    }

    public String getTemplateKK() {
        return templateKK;
    }

    public String getTemplateRU() {
        return templateRU;
    }

    public String getTemplateEN() {
        return templateEN;
    }
}
