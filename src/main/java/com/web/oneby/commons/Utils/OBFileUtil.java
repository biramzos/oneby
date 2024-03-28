package com.web.oneby.commons.Utils;

import com.web.oneby.commons.Enums.Template;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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
        try {
            String path = ConstantsUtil.TEMPLATES_DIRECTORY + template.getFileByLanguage(lang);
            Path templatePath = Path.of(path);

            Files.createDirectories(templatePath.getParent());

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, templatePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return true;
        } catch (IOException e) {
            LogUtil.write(e);
            return false;
        }
    }


}
