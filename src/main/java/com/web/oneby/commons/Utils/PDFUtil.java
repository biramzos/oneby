package com.web.oneby.commons.Utils;

import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Enums.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
public class PDFUtil {

    public static byte[] generateBill() {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A6);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 8);
                contentStream.newLineAtOffset(10, 90); // Adjust position as needed
                contentStream.showText("Bill Content Here"); // Customize with your bill content
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 8);
                contentStream.newLineAtOffset(10, 90); // Adjust position as needed
                contentStream.showText("Bill Content Here"); // Customize with your bill content
                contentStream.endText();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static byte[] generateDocument(Map<String, String> contractData, int templateId, int lang)  {
        try (InputStream inputStream = new ClassPathResource("template_" + Template.getById(templateId).name() + "_" + Language.getLanguageById(lang).suffix() + ".docx").getInputStream();
             XWPFDocument document = new XWPFDocument(inputStream);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (Map.Entry<String, String> entry : contractData.entrySet()) {
                    String placeholder = entry.getKey();
                    String replacement = entry.getValue();
                    for (int i = 0; i < paragraph.getRuns().size(); i++) {
                        if (paragraph.getRuns().get(i).getText(0).contains("${" + placeholder + "}")) {
                            paragraph.getRuns().get(i).setText(paragraph.getRuns().get(i).getText(0).replace("${" + placeholder + "}", replacement), 0);
                        }
                    }
                }
            }
            document.write(outputStream);
            return convertWordToPdf(outputStream.toByteArray());
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static byte[] convertWordToPdf(byte[] wordBytes)  {
        try (InputStream inputStream = new ByteArrayInputStream(wordBytes);
             XWPFDocument document = new XWPFDocument(inputStream);
             ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream()) {

            PDDocument pdfDocument = new PDDocument();
            PDPage page = new PDPage();
            pdfDocument.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 700);
                for (XWPFParagraph paragraph : document.getParagraphs()) {
                    contentStream.showText(paragraph.getText());
                    contentStream.newLine();
                }

                contentStream.endText();
            }
            pdfDocument.save(pdfOutputStream);
            pdfDocument.close();
            return pdfOutputStream.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
