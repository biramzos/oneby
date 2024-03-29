package com.web.oneby.commons.Controllers;

import com.web.oneby.commons.DTOs.FileObject;
import com.web.oneby.commons.DTOs.TemplateDataObject;
import com.web.oneby.commons.Enums.HTTPMessage;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Enums.LogType;
import com.web.oneby.commons.Enums.Template;
import com.web.oneby.commons.Handlers.HTTPMessageHandler;
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
        byte[] billBytes = PDFUtil.generateBill(language.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "bill.pdf");
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
    public ResponseEntity<HTTPMessageHandler> replaceAndSaveTemplate(@RequestHeader(value = "Current-Language", defaultValue = "ru") Language language,
                                                                     @PathVariable("type") int type, @ModelAttribute FileObject file) {
        if (file.getFile().isEmpty()) {
            LogUtil.write(HTTPMessage.ERROR_WHILE_UPLOADING.getMessage(Language.en), LogType.ERROR);
            return ResponseEntity.badRequest().body(new HTTPMessageHandler(HTTPMessage.ERROR_WHILE_UPLOADING, language.getId()));
        }
        Template template = Template.getById(type);
        if (template == null) {
            LogUtil.write(HTTPMessage.NO_RESOURCES_FOUND.getMessage(Language.en), LogType.ERROR);
            return ResponseEntity.badRequest().body(new HTTPMessageHandler(HTTPMessage.NO_RESOURCES_FOUND, language.getId()));
        }
        OBFileUtil.saveTemplate(file.getFile(), template, language.getId());
        return ResponseEntity.ok().body(new HTTPMessageHandler(HTTPMessage.SUCCESSFULLY_UPLOADED, language.getId()));
    }

}
