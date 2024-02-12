package com.web.oneby.books.Controllers;

import com.web.oneby.books.Models.Organization;
import com.web.oneby.books.Services.OrganizationService;
import com.web.oneby.commons.Utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationController {
    private OrganizationService organizationService;

    @Autowired
    public OrganizationController (
        OrganizationService organizationService
    ) {
        this.organizationService = organizationService;
    }

    @ResponseBody
    @GetMapping("/image/{organizationId}")
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public byte[] getImage (@PathVariable("organizationId") Organization organization) {
        return organization.getImage();
    }
}
