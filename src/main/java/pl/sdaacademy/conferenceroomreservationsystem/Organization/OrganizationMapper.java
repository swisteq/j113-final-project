package pl.sdaacademy.conferenceroomreservationsystem.Organization;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    @Mappings(value = {
            @Mapping(target = "organizationId", source = "id"),
            @Mapping(target = "organizationName", source = "name")
    })
    OrganizationDTO mapOrganizationToDTO(Organization organization);

    @Mappings(value = {
            @Mapping(target="id", ignore = true),
            @Mapping(target = "name", source = "organizationName")
    })
    Organization mapAddOrganizationRequestToOrganization(AddOrganizationRequest request);
}
