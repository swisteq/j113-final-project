package pl.sdaacademy.conferenceroomreservationsystem.Organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class OrganizationServiceImpl implements OrganizationService{

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    @Override
    public List<OrganizationDTO> getOrganizationList() {
        return organizationRepository.findAll()
                .stream()
                .map(organization -> {
                    return organizationMapper.mapOrganizationToDTO(organization);
                }).collect(Collectors.toList());
    }

    @Override
    public OrganizationDTO addOrganization(AddOrganizationRequest request) {

        Organization organization = organizationRepository
                .findByName(request.getOrganizationName())
                .orElse(null);

        if (organization == null){
            organization = organizationMapper.mapAddOrganizationRequestToOrganization(request);
        }
        else {
            throw (new IllegalArgumentException());
        }

        organization = organizationRepository.save(organization);

        return organizationMapper.mapOrganizationToDTO(organization);
    }

    @Override
    public Organization deleteOrganization(String name) {
        Organization organizationToDelete = organizationRepository.findByName(name)
                .orElseThrow(()-> new NoSuchElementException("No organization found!"));
        organizationRepository.delete(organizationToDelete);
        return organizationToDelete;
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        Organization organizationToUpdate = organizationRepository.findById(organization.getId())
                .orElseThrow(()->new NoSuchElementException("No organization to update found!"));
        organizationRepository.findByName(organization.getName())
                .ifPresent(o ->{
                    throw new IllegalArgumentException("Organization name already exists");
                });
        if (organization.getName() != null) {
            organizationToUpdate.setName(organization.getName());
        }
        return organizationRepository.save(organizationToUpdate);
    }
}
