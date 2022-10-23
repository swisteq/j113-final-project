package pl.sdaacademy.conferenceroomreservationsystem.Organization;

import pl.sdaacademy.conferenceroomreservationsystem.SortType;

import java.util.List;

public interface OrganizationService {
    List<OrganizationDTO> getOrganizationList(SortType sortType);

    Organization getByName(String name);

    OrganizationDTO addOrganization(AddOrganizationRequest request);

    Organization deleteOrganization(String name);

    Organization updateOrganization(Organization organization);
}
