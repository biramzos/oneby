package com.web.oneby.commons.Utils;

import com.lowagie.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Enums.LogType;
import com.web.oneby.commons.Enums.Template;
import com.web.oneby.commons.Services.OBFileService;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.impl.xb.xsdschema.WhiteSpaceDocument;
import org.docx4j.Docx4J;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.awt.*;
import java.io.*;
import java.util.*;

@Slf4j
@Service
public class PDFUtil {

    @Autowired
    private static OBFileService fileService;

    public static byte[] generateBill(int language) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Document document = new Document(PageSize.A6)) {
            PdfWriter.getInstance(document, baos);
            document.setMargins(10f,10f, 10f,10f);
            document.open();
            document.add(getHeaderImage());
            document.add(getHeader(10L));
            document.add(new Chunk(getSpacer(Color.black)));
            writeCustomerInfo(document, language);
            document.add(new Chunk(getSpacer(Color.black)));
            writeProductsInfo(document, language);
            document.add(new Chunk(getSpacer(Color.black)));
            document.close();
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
            documentPart.variableReplace(replacements);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wordMLPackage.save(outputStream);
            return convertDocxToPdf(outputStream.toByteArray());
        } catch (Exception e) {
            LogUtil.write(e.getMessage(), LogType.ERROR);
            return null;
        }
    }


    private static Image getHeaderImage(){
        try {
            Image image = Image.getInstance(ConstantsUtil.IMAGES_DIRECTORY + "userDefault.png");
            image.scaleToFit(40, 40);
            return image;
        } catch (IOException e) {
            LogUtil.write(e.getMessage(), LogType.ERROR);
            return null;
        }
    }

    private static Paragraph getHeader(Long id) {
        Map<String, Font> fonts = getMonoscapeFonts();
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase("OneBy", fonts.get("fontBold")));
        paragraph.add(new Chunk(getSpacer(Color.white)));
        paragraph.add(new Phrase("№ " + id, fonts.get("font")));
        return paragraph;
    }

    private static void writeCustomerInfo(Document document, int language) {
        Map<String, Font> fonts = getMonoscapeFonts();
        document.add(new Paragraph(TranslationUtil.getMessage("customer", language) + "\n\n", fonts.get("fontBold")));
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
    }

    private static void writeProductsInfo(Document document, int language) {
        Map<String, Font> fonts = getMonoscapeFonts();
        document.add(new Paragraph(TranslationUtil.getMessage("products", language) + "\n\n", fonts.get("fontBold")));
        PdfPTable table = new PdfPTable(3);
        //params of table
        table.setWidthPercentage(100);
        table.setWidths(new float[]{ 1.5f, 6.0f, 2.5f });
        //header of table
        table.addCell(new Paragraph(TranslationUtil.getMessage("№", language), fonts.get("fontBold")));
        table.addCell(new Paragraph(TranslationUtil.getMessage("product_name", language), fonts.get("fontBold")));
        table.addCell(new Paragraph(TranslationUtil.getMessage("product_value", language), fonts.get("fontBold")));
        //body of table
        table.addCell(new Paragraph(String.valueOf(100), fonts.get("font")));
        table.addCell(new Paragraph("Book1 (BookAuthor)", fonts.get("font")));
        table.addCell(new Paragraph("100 T.", fonts.get("font")));
        //footer of table
        PdfPCell cell = new PdfPCell();
        cell.setColspan(2);
        Paragraph total = new Paragraph(TranslationUtil.getMessage("total", language), fonts.get("font"));
        total.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(total);
        table.addCell(cell);
        table.addCell(new Paragraph("100 T.", fonts.get("font")));
        document.add(table);
    }

    private static void writeFooter(Document document, int language) {

    }


    private static LineSeparator getSpacer(Color color) {
        LineSeparator spacer = new LineSeparator();
        spacer.setLineColor(color);
        return spacer;
    }

    private static Map<String, Font> getMonoscapeFonts() {
        return new HashMap<>() {{
            put("font", FontUtil.getMonoscapeFontProvider().getFont("Monoscape", "Identify-H", 8, 0, Color.BLACK));
            put("fontBold", FontUtil.getMonoscapeFontProvider().getFont("Monoscape", "Identify-H", 9, 1, Color.BLACK));
        }};
    }


}
