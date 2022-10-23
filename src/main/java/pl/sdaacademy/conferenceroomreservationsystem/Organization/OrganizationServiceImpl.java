package pl.sdaacademy.conferenceroomreservationsystem.Organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.sdaacademy.conferenceroomreservationsystem.SortType;

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
    public List<OrganizationDTO> getOrganizationList(SortType sortType) {
        if (sortType != null){
            return organizationRepository.findAll(sortType.getSort("name"))
                    .stream()
                    .map(organization -> {
                        return organizationMapper.mapOrganizationToDTO(organization);
                    }).collect(Collectors.toList());
        } else {
            return organizationRepository.findAll()
                    .stream()
                    .map(organization -> {
                        return organizationMapper.mapOrganizationToDTO(organization);
                    }).collect(Collectors.toList());
        }
    }

    @Override
    public Organization getByName(String name) {
        return organizationRepository.findByName(name)
                .orElseThrow(()-> new NoSuchElementException("No organization found"));
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
            throw new IllegalArgumentException();
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
