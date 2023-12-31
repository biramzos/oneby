package com.web.oneby.Models;

import com.web.oneby.Enums.Language;
import com.web.oneby.Enums.Module;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String nameKZ;
    @Column(name = "name_ru")
    private String nameRU;
    @Column(name = "name_en")
    private String nameEN;
    @Column(name = "module")
    @Enumerated(EnumType.STRING)
    private Module module;
    @Column(name = "value")
    private String value;

    public Setting (
            String code,
            String nameKZ,
            String nameRU,
            String nameEN,
            Module module,
            String value
    ) {
        this.code = code;
        this.nameKZ = nameKZ;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.module = module;
        this.value = value;
    }

    public String getName(int language){
        if (language == Language.kz.getId()) {
            return nameKZ;
        } else if (language == Language.ru.getId()) {
            return nameRU;
        } else {
            return nameEN;
        }
    }
}
