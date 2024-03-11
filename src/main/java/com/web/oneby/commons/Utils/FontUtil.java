package com.web.oneby.commons.Utils;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.awt.*;

public class FontUtil {

//    public static IFontProvider getFontProvider() {
//        return new IFontProvider() {
//            @Override
//            public Font getFont(String s, String s1, float v, int i, Color color) {
//                return null;
//            }
//
//            private Map<String, String> fontMappings = createFontMappings();
//
//
//            private Map<String, String> createFontMappings() {
//                Map<String, String> fontMappings = new HashMap<>();
//                // Add font mappings for each language
//                fontMappings.put("Arial", "Arial");
//                fontMappings.put("Calibri", "Calibri");
//                fontMappings.put("Times New Roman", "Times New Roman");
//                // Add mappings for other languages as needed
//                return fontMappings;
//            }
//        };
//    }

    public static IFontProvider getFontProvider() {
        return new IFontProvider() {

            @SneakyThrows
            @Override
            public Font getFont(String fontFamily, String encoding, float size, int style, Color color) {
                boolean isBold = style == Font.BOLD;
                boolean isItalic = style == Font.ITALIC;
                boolean isBoth = style == Font.BOLDITALIC;
                String fontPath = "";
                if (isBold) {
                    fontPath = new ClassPathResource("/static/fonts/TimesNewRoman/font_bold.ttf").getPath();
                } else if (isItalic) {
                    fontPath = new ClassPathResource("/static/fonts/TimesNewRoman/font_italic.ttf").getPath();
                } else if (isBoth) {
                    fontPath = new ClassPathResource("/static/fonts/TimesNewRoman/font_bold_italic.ttf").getPath();
                } else {
                    fontPath = new ClassPathResource("/static/fonts/TimesNewRoman/font.ttf").getPath();
                }
                return FontFactory.getFont(fontPath, BaseFont.IDENTITY_H, true);
            }

        };
    }


}
