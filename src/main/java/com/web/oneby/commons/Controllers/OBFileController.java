package com.web.oneby.commons.Controllers;

import com.web.oneby.commons.DTOs.FileObject;
import com.web.oneby.commons.DTOs.TemplateDataObject;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Enums.Template;
import com.web.oneby.commons.Utils.OBFileUtil;
import com.web.oneby.commons.Utils.PDFUtil;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/obfiles/")
public class OBFileController {

    public OBFileController () {}

    @GetMapping("/bill")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<byte[]> downloadBill() {
        // Generate the bill PDF bytes
        byte[] billBytes = PDFUtil.generateBill();

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "bill.pdf"); // Set the filename for download

        // Return the response entity with the PDF bytes and headers
        return new ResponseEntity<>(billBytes, headers, HttpStatus.OK);
    }


    @ResponseBody
    @PostMapping("/generate-document")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<byte[]> download(
            @RequestHeader(value = "Current-Language", defaultValue = "ru") Language language,
            @RequestBody TemplateDataObject dataHandler
    ) {
        Template template = Template.getById(dataHandler.getType());
        if (template != null) {
            byte[] billBytes = PDFUtil.generateDocument(template, dataHandler.getData(), language.getId());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", template.name() + ".pdf");
            return new ResponseEntity<>(billBytes, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @PostMapping(path = "/template/{type}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<String> replaceAndSaveTemplate(@RequestHeader(value = "Current-Language", defaultValue = "ru") Language language,
                                                         @PathVariable("type") int type, @ModelAttribute FileObject file) {
        if (file.getFile().isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file.");
        }
        Template template = Template.getById(type);
        if (template == null) {
            return ResponseEntity.badRequest().body("There is no template with type of " + type);
        }
        OBFileUtil.saveTemplate(file.getFile(), template, language.getId());
        return ResponseEntity.ok().body("Uploaded!");
    }

}