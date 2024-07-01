package com.web.oneby.commons.Controllers;

import com.web.oneby.commons.DTOs.FileObject;
import com.web.oneby.commons.DTOs.MessageData;
import com.web.oneby.commons.DTOs.TemplateDataObject;
import com.web.oneby.commons.Enums.*;
import com.web.oneby.commons.Utils.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/obfiles/")
public class OBFileController {

    public OBFileController () {}

    @GetMapping("/bill")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<byte[]> downloadBill(@RequestHeader(value = "Current-Language", defaultValue = "ru") Language language) {
        byte[] billBytes = PDFUtil.generateBill(language);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "bill" + FileType.PDF.getFormat());
        return new ResponseEntity<>(billBytes, headers, HttpStatus.OK);
    }


    @ResponseBody
    @PostMapping("/generate-document")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<byte[]> download(@RequestHeader(value = "Current-Language", defaultValue = "ru") Language language,
            @RequestBody TemplateDataObject dataHandler
    ) {
        Template template = Template.getById(dataHandler.getType());
        if (template != null) {
            byte[] billBytes = PDFUtil.generateDocument(template, dataHandler.getData(), language);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", template.name() + FileType.PDF.getFormat());
            return new ResponseEntity<>(billBytes, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @PostMapping(path = "/template/{type}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<MessageData> replaceAndSaveTemplate(@RequestHeader(value = "Current-Language", defaultValue = "ru") Language language,
                                                              @PathVariable("type") int type, @ModelAttribute FileObject file) {
        if (file.getFile().isEmpty()) {
            LogUtil.write(HTTPMessage.ERROR_WHILE_UPLOADING.getMessage(Language.en), LogType.ERROR);
            return ResponseEntity.badRequest().body(new MessageData(HTTPMessage.ERROR_WHILE_UPLOADING, language));
        }
        Template template = Template.getById(type);
        if (template == null) {
            LogUtil.write(HTTPMessage.NO_RESOURCES_FOUND.getMessage(Language.en), LogType.ERROR);
            return ResponseEntity.badRequest().body(new MessageData(HTTPMessage.NO_RESOURCES_FOUND, language));
        }
        OBFileUtil.saveTemplate(file.getFile(), template, language);
        return ResponseEntity.ok().body(new MessageData(HTTPMessage.SUCCESSFULLY_UPLOADED, language));
    }

}
