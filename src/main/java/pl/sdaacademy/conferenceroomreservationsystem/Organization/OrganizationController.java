package pl.sdaacademy.conferenceroomreservationsystem.Organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.sdaacademy.conferenceroomreservationsystem.SortType;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("api/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping()
    public List<OrganizationDTO> getAllOrganizations(@RequestParam(required = false) SortType sortType){
        return organizationService.getOrganizationList(sortType);
    }

    @GetMapping("/{name}")
    public Organization getByName(@PathVariable String name) {
        return organizationService.getByName(name);
    }

    @PostMapping()
    public OrganizationDTO addOrganization(@Valid @RequestBody AddOrganizationRequest request){
        return organizationService.addOrganization(request);
    }

    @DeleteMapping("/{organizationName}")
    public Organization delete(@PathVariable String organizationName) {
        return organizationService.deleteOrganization(organizationName);
    }

    @PutMapping
    public Organization update(@RequestBody Organization organization) {
        return organizationService.updateOrganization(organization);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
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
