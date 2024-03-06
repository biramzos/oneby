package com.web.oneby.modules.books.Controllers;

import com.web.oneby.modules.books.Models.Organization;
import com.web.oneby.modules.books.Services.OrganizationService;
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
