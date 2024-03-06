package com.web.oneby.modules.books.DTOs;

import com.web.oneby.modules.books.Models.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationResponse {
    private String name;
    private String image;

    public static OrganizationResponse fromOrganization(Organization organization) {
        OrganizationResponse organizationResponse = new OrganizationResponse();
        organizationResponse.name = organization.getName();
        organizationResponse.image = "/api/v1/organizations/image/" + organization.getId();
        return organizationResponse;
    }
}
