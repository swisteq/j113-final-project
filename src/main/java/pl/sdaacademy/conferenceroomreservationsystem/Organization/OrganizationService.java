package pl.sdaacademy.conferenceroomreservationsystem.Organization;

import java.util.List;

public interface OrganizationService {
    List<OrganizationDTO> getOrganizationList();

    OrganizationDTO addOrganization(AddOrganizationRequest request);

    Organization deleteOrganization(String name);

    Organization updateOrganization(Organization organization);
}
