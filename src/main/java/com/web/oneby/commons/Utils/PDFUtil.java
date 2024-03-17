package com.web.oneby.commons.Utils;

import com.lowagie.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Enums.LogType;
import com.web.oneby.commons.Enums.Template;
import com.web.oneby.commons.Services.OBFileService;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
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
import java.io.*;
import java.util.*;

@Slf4j
@Service
public class PDFUtil {

    @Autowired
    private static OBFileService fileService;

    public static byte[] generateBill(int language) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Document document = new Document(new Rectangle(210.0F, 300F))) {
            Font font = FontFactory.getFont(ConstantsUtil.FONTS_DIRECTORY + "Monoscape/font.ttf", BaseFont.IDENTITY_H, true, 8, 0);
            PdfWriter.getInstance(document, baos);
            document.setMargins(10f,10f, 10f,10f);
            document.open();
            Image image = Image.getInstance(ConstantsUtil.IMAGES_DIRECTORY + "userDefault.png");
            image.scaleToFit(40, 40);
            document.add(image);
            String word = TranslationUtil.getMessage("customer", language);
            System.out.println(word);
            document.add(new Paragraph("OneBy                                 № 1 \n", font));
            document.add(new Paragraph("------------------------------------------\n", font));
            document.add(new Paragraph(TranslationUtil.getMessage("customer", language) + "\n\n", font));
            document.add(new Paragraph("Full name: Ramis Beishembiyev", font));
            document.add(new Paragraph("Email: b.ramis.2002@gmail.com", font));
            document.add(new Paragraph("Username: imramo00", font));
            document.add(new Paragraph("------------------------------------------\n", font));
            document.add(new Paragraph("Products\n\n", font));
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell(new Paragraph("Title", font));
            table.addCell(new Paragraph("Price", font));
            table.addCell(new Paragraph("Book1 (BookAuthor)", font));
            table.addCell(new Paragraph("100 T.", font));
            document.add(table);
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



}
