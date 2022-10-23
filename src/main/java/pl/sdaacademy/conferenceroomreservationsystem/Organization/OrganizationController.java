package pl.sdaacademy.conferenceroomreservationsystem.Organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping()
    public List<OrganizationDTO> getAllOrganizations(){
        log.info("getAllOrgs called");
        return organizationService.getOrganizationList();
    }

    @PostMapping()
    public OrganizationDTO addOrganization(@RequestBody AddOrganizationRequest request){
        log.info("getOrg called");
        return organizationService.addOrganization(request);
    }

    @DeleteMapping("/{organizationName}")
    Organization delete(@PathVariable String organizationName) {
        return organizationService.deleteOrganization(organizationName);
    }

    @PutMapping
    Organization update(@RequestBody Organization organization) {
        return organizationService.updateOrganization(organization);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
