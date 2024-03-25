package com.web.oneby.commons.Enums;

import java.util.HashMap;
import java.util.Map;

public enum Template {
    CONTRACT_DEFAULT(
            -1,
            "Әдепкі шарт",
            "Контракт по умолчанию",
            "Default contract",
            "Template_KK.docx",
            "Template_RU.docx",
            "Template_EN.docx"
    ),
    CONTRACT_WORK_TOGETHER(
            1,
            "Бірлесіп жұмыс істеу шарты",
            "Договор о совместной работе",
            "Contract of working together",
            "Template_1_KK.docx",
            "Template_1_RU.docx",
            "Template_1_EN.docx"
    ),
    CONTRACT_EMPLOYEE(
            2,
            "Қызметкерлерге арналған шарт",
            "Трудовой договор для сотрудников",
            "Contract for employees",
            "Template_2_KK.docx",
            "Template_2_RU.docx",
            "Template_2_EN.docx"
    ),
    ;

    private int id;
    private String templateKK;
    private String templateRU;
    private String templateEN;

    private String fileKK;
    private String fileRU;
    private String fileEN;

    private final static Map<Integer, Template> templates = new HashMap<>();

    static {
        for (Template template : values()) {
            templates.put(template.id, template);
        }
    }

    Template (int id, String templateKK, String templateRU, String templateEN, String fileKK, String fileRU, String fileEN) {
        this.id = id;
        this.templateKK = templateKK;
        this.templateRU = templateRU;
        this.templateEN = templateEN;
        this.fileKK = fileKK;
        this.fileRU = fileRU;
        this.fileEN = fileEN;
    }

    public static Template getById (int id) {
        return templates.get(id);
    }

    public String getTemplateName(int language) {
        Map<String, String> names = new HashMap<>() {{
            put(Language.kk.name(), templateKK);
            put(Language.ru.name(), templateRU);
            put(Language.en.name(), templateEN);
        }};
        return names.get(Language.getLanguageById(language).name());
    }

    public String getTemplateName(Language language) {
        Map<String, String> names = new HashMap<>() {{
            put(Language.kk.name(), templateKK);
            put(Language.ru.name(), templateRU);
            put(Language.en.name(), templateEN);
        }};
        return names.get(language.name());
    }

    public String getFileByLanguage (int lang) {
        Map<String, String> files = new HashMap<>() {{
            put(Language.kk.name(), fileKK);
            put(Language.ru.name(), fileRU);
            put(Language.en.name(), fileEN);
        }};
        return files.get(Language.getLanguageById(lang).name());
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

    public String getFileKK() {
        return fileKK;
    }

    public String getFileRU() {
        return fileRU;
    }

    public String getFileEN() {
        return fileEN;
    }
}
