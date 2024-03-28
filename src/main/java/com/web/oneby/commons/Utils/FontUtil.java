package com.web.oneby.commons.Utils;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import java.awt.*;

public class FontUtil {

    public static IFontProvider getTimesNewRomanFontProvider() {
        return new IFontProvider() {
            @Override
            public Font getFont(String fontFamily, String encoding, float size, int style, Color color) {
                String fontPath = ConstantsUtil.FONTS_DIRECTORY + "TimesNewRoman/font.ttf";
                return FontFactory.getFont(fontPath, BaseFont.IDENTITY_H, false, size, style, color);
            }
        };
    }

    public static IFontProvider getMonoscapeFontProvider() {
        return new IFontProvider() {
            @Override
            public Font getFont(String fontFamily, String encoding, float size, int style, Color color) {
                String fontPath = ConstantsUtil.FONTS_DIRECTORY + "Monoscape/font.ttf";;
                return FontFactory.getFont(fontPath, BaseFont.IDENTITY_H, true, size, style, color);
            }
        };
    }


}
