package com.web.oneby.commons.Models;

import com.web.oneby.commons.Enums.Language;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.web.oneby.commons.Enums.Module;

import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(name = "settings")
@NoArgsConstructor
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "name_kz")
    private String nameKK;
    @Column(name = "name_ru")
    private String nameRU;
    @Column(name = "name_en")
    private String nameEN;
    @Enumerated(EnumType.STRING)
    @Column(name = "module")
    private Module module;
    @Column(name = "value")
    private String value;

    public Setting (
            String code,
            String nameKK,
            String nameRU,
            String nameEN,
            Module module,
            String value
    ) {
        this.code = code;
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.module = module;
        this.value = value;
    }


    public String getName(Language language){
        Map<String, String> names = new HashMap<>() {{
            put(Language.kk.suffix(), nameKK);
            put(Language.ru.suffix(), nameRU);
            put(Language.en.suffix(), nameEN);
        }};
        return names.get(language.suffix());
    }
}
