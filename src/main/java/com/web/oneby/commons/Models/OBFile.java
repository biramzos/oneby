package com.web.oneby.commons.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.oneby.commons.Utils.LogUtil;
import com.web.oneby.commons.Utils.OBFileUtil;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Data
@Entity
@Table(name = "ob_files")
@NoArgsConstructor
@Slf4j
public class OBFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "format")
    private String format;
    @Column(name = "name")
    private String name;
    @JsonIgnore
    @Lob
    @Column(name = "content", columnDefinition = "LONGBLOB")
    private byte[] content;

    public OBFile (String format, String name, byte[] content) {
        this.format = format;
        this.name = name;
        this.content = content;
    }

    public OBFile (File file) {
        this.format = OBFileUtil.getFileFormat(file.getName());
        this.name = file.getName();
        this.content = OBFileUtil.getFileBytes(file);
    }

    public OBFile (MultipartFile file) {
        try {
            this.format = OBFileUtil.getFileFormat(file.getName());
            this.name = file.getName();
            this.content = file.getBytes();
        } catch (IOException e) {
            LogUtil.write(e);
        }
    }
}
