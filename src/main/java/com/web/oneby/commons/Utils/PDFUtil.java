package com.web.oneby.commons.Utils;

import com.lowagie.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.web.oneby.commons.Enums.LogType;
import com.web.oneby.commons.Enums.ProductType;
import com.web.oneby.commons.Enums.Template;
import com.web.oneby.commons.Services.OBFileService;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Component
public class PDFUtil {

    private static OBFileService fileService;

    @Autowired
    public PDFUtil (OBFileService fileService) {
        PDFUtil.fileService = fileService;
    }

    public static byte[] generateBill(int language) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Document document = new Document(PageSize.A6)) {
            PdfWriter.getInstance(document, baos);
            document.setMargins(10f,10f, 10f,10f);
            document.open();
            document.add(getHeaderImage());
            document.add(getHeader(10L, language));
            document.add(new Chunk(getSpacer(Color.black)));
            writeCustomerInfo(document, language);
            document.add(new Chunk(getSpacer(Color.black)));
            writeProductsInfo(document, language);
            document.add(new Chunk(getSpacer(Color.black)));
            writeFooter(document, language);
            document.add(getSpaceCutter(Color.black));
            document.close();
            LogUtil.write("Payment bill is generated![id=?]", LogType.INFO);
            return baos.toByteArray();
        } catch (IOException e) {
            LogUtil.write(e.getMessage(), LogType.ERROR);
            return null;
        }
    }

    public static byte[] convertDocxToPdf(byte[] docxBytes) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(docxBytes);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            XWPFDocument document = new XWPFDocument(inputStream);
            PdfOptions options = PdfOptions.create();
            options.fontProvider(FontUtil.getTimesNewRomanFontProvider());
            PdfConverter.getInstance().convert(document, outputStream, options);
            return outputStream.toByteArray();
        } catch (IOException e) {
            LogUtil.write(e.getMessage(), LogType.ERROR);
            return null;
        }
    }


    public static byte[] generateDocument(Template template, Map<String, String> replacements, int lang) {
        try (InputStream inputStream = new FileInputStream(ConstantsUtil.TEMPLATES_DIRECTORY + template.getFileByLanguage(lang))) {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(inputStream);
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
            documentPart.variableReplace(getReplacements(replacements, lang));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wordMLPackage.save(outputStream);
            LogUtil.write("Document is generated![id=?]", LogType.INFO);
            return convertDocxToPdf(outputStream.toByteArray());
        } catch (Exception e) {
            LogUtil.write(e.getMessage(), LogType.ERROR);
            return null;
        }
    }

    private static Map<String, String> getReplacements(Map<String, String> replacements, int language) {
        Map<String, String> variables = new HashMap<>();
        for (Map.Entry<String, String> replacement : replacements.entrySet()) {
            if (replacement.getKey().equals("date")) {
                variables.put(replacement.getKey(), DateUtil.modifyStringDateForDocument(replacement.getValue(), language));
            } else {
                variables.put(replacement.getKey(), replacement.getValue());
            }
        }
        return variables;
    }

    private static Image getHeaderImage(){
        try {
            Image image = Image.getInstance(ConstantsUtil.LOGOS_DIRECTORY + "logo-black.png");
            image.scaleToFit(50, 50);
            return image;
        } catch (IOException e) {
            LogUtil.write(e.getMessage(), LogType.ERROR);
            return null;
        }
    }

    private static Paragraph getHeader(Long id, int language) {
        Map<String, Font> fonts = getMonoscapeFonts();
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase(TranslationUtil.getMessage("payment_bill", language), fonts.get("fontBold")));
        paragraph.add(new Chunk(getSpacer(Color.white)));
        paragraph.add(new Phrase("№ " + id, fonts.get("font")));
        return paragraph;
    }

    private static void writeCustomerInfo(Document document, int language) {
        Map<String, Font> fonts = getMonoscapeFonts();
        document.add(new Paragraph(TranslationUtil.getMessage("customer", language) + "\n", fonts.get("fontBold")));
        //full name
        Paragraph fullName = new Paragraph();
        fullName.add(new Phrase(TranslationUtil.getMessage("full_name", language) + ":", fonts.get("font")));
        fullName.add(new Chunk(getSpacer(Color.white)));
        fullName.add(new Phrase("Ramis Beishembiyev", fonts.get("font")));
        document.add(fullName);
        //Email
        Paragraph email = new Paragraph();
        email.add(new Phrase(TranslationUtil.getMessage("email", language) + ":", fonts.get("font")));
        email.add(new Chunk(getSpacer(Color.white)));
        email.add(new Phrase("b.ramis.2002@gmail.com", fonts.get("font")));
        document.add(email);
        //username
        Paragraph username = new Paragraph();
        username.add(new Phrase(TranslationUtil.getMessage("username", language) + ":", fonts.get("font")));
        username.add(new Chunk(getSpacer(Color.white)));
        username.add(new Phrase("imramo00", fonts.get("font")));
        document.add(username);
        //date
        Paragraph date = new Paragraph();
        date.add(new Phrase(TranslationUtil.getMessage("date", language) + ":", fonts.get("font")));
        date.add(new Chunk(getSpacer(Color.white)));
        date.add(new Phrase(DateUtil.getStringDateTimeFromDateTime(LocalDateTime.now(ZoneId.of("GMT+05:00")), language), fonts.get("font")));
        document.add(date);
    }

    private static void writeProductsInfo(Document document, int language) {
        Map<String, Font> fonts = getMonoscapeFonts();
        document.add(new Paragraph(TranslationUtil.getMessage("products", language) + "\n\n", fonts.get("fontBold")));
        PdfPTable table = new PdfPTable(3);
        //params of table
        table.setWidthPercentage(100);
        table.setWidths(new float[]{ 1.0f, 8f, 2f });
        //header of table
        table.addCell(new Paragraph(TranslationUtil.getMessage("№", language), fonts.get("fontBold")));
        table.addCell(new Paragraph(TranslationUtil.getMessage("product_name", language), fonts.get("fontBold")));
        table.addCell(new Paragraph(TranslationUtil.getMessage("product_value", language), fonts.get("fontBold")));
        //body of table
        //Books
        table.addCell(getTableProductHeader(ProductType.BOOKS.getTableName(), language));
        for (int i = 0; i < 10; i++) {
            table.addCell(new Paragraph(String.valueOf(i + 1), fonts.get("font")));
            table.addCell(new Paragraph("Book1 (BookAuthor)", fonts.get("font")));
            table.addCell(new Paragraph("100 T.", fonts.get("font")));
        }
        //footer of table
        PdfPCell cell = new PdfPCell();
        cell.setColspan(2);
        Paragraph total = new Paragraph(TranslationUtil.getMessage("total", language), fonts.get("fontBold"));
        total.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(total);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Paragraph("100 T.", fonts.get("fontBold")));
        table.addCell(cell);
        document.add(table);
    }

    private static void writeFooter(Document document, int language) {
        Map<String, Font> fonts = getMonoscapeFonts();
        document.add(new Paragraph(TranslationUtil.getMessage("terms_and_conditions", language), fonts.get("fontBold")));
        document.add(new Paragraph(TranslationUtil.getMessage("payment_is_due_within", language, "15"), fonts.get("font")));
        document.add(new Paragraph(TranslationUtil.getMessage("please_make_checks_payable_to", language, "OneBy"), fonts.get("font")));
        document.add(new Paragraph("\n", fonts.get("font")));
    }


    private static LineSeparator getSpacer(Color color) {
        LineSeparator spacer = new LineSeparator();
        spacer.setLineColor(color);
        return spacer;
    }

    private static PdfPCell getTableProductHeader(String productType, int language) {
        Map<String, Font> fonts = getMonoscapeFonts();
        PdfPCell header = new PdfPCell();
        header.setColspan(3);
        Paragraph headerStr = new Paragraph(TranslationUtil.getMessage(productType, language), fonts.get("fontBold"));
        headerStr.setAlignment(Paragraph.ALIGN_CENTER);
        header.addElement(headerStr);
        return header;
    }

    private static LineSeparator getSpaceCutter(Color color) {
        LineSeparator spacer = new LineSeparator();
        spacer.setLineWidth(1f);
        spacer.setLineColor(color);
        spacer = new LineSeparator() {
            @Override
            public void draw(PdfContentByte canvas, float llx, float lly, float urx, float ury, float y) {
                canvas.saveState();
                canvas.setLineWidth(lineWidth);
                canvas.setColorStroke(color);
                float dashLength = 5;
                float gapLength = 2;
                float totalLength = dashLength + gapLength;
                float x = llx;
                while (x < urx) {
                    canvas.moveTo(x, y);
                    canvas.lineTo(Math.min(x + dashLength, urx), y);
                    x += totalLength;
                }
                canvas.stroke();
                canvas.restoreState();
            }
        };
        return spacer;
    }

    private static Map<String, Font> getMonoscapeFonts() {
        return new HashMap<>() {{
            put("font", FontUtil.getMonoscapeFontProvider().getFont("Monoscape", BaseFont.IDENTITY_H, 8, 0, Color.BLACK));
            put("fontBold", FontUtil.getMonoscapeFontProvider().getFont("Monoscape", BaseFont.IDENTITY_H, 9, 1, Color.BLACK));
        }};
    }

    private static Map<String, Font> getTimesNewRomanFonts() {
        return new HashMap<>() {{
            put("font", FontUtil.getTimesNewRomanFontProvider().getFont("TimesNewRoman", BaseFont.IDENTITY_H, 8, 0, Color.BLACK));
            put("fontBold", FontUtil.getTimesNewRomanFontProvider().getFont("TimesNewRoman", BaseFont.IDENTITY_H, 9, 1, Color.BLACK));
            put("fontItalic", FontUtil.getTimesNewRomanFontProvider().getFont("TimesNewRoman", BaseFont.IDENTITY_H, 9, 2, Color.BLACK));
            put("fontBoldItalic", FontUtil.getTimesNewRomanFontProvider().getFont("TimesNewRoman", BaseFont.IDENTITY_H, 9, 3, Color.BLACK));

        }};
    }

}
