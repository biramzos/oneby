package com.web.oneby.commons.Utils;

import com.web.oneby.commons.Enums.Template;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OBFileUtil {

    public static String getFileFormat(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return null;
        }
        return fileName.substring(dotIndex + 1);
    }

    public static byte[] getFileBytes(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            int bytesRead = fis.read(buffer);
            if (bytesRead == -1) {
                return null;
            }
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean saveTemplate(MultipartFile file, Template template, int lang) {
        String path = template.getFileByLanguage(lang);
        try (InputStream inputStream = file.getInputStream()) {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(inputStream);
            File outputFile = new File(path);
            wordMLPackage.save(outputFile);
            return true;
        } catch (IOException | Docx4JException e) {
            e.printStackTrace();
            return false;
        }
    }

}
