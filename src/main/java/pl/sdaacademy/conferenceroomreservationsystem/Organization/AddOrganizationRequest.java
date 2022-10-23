package pl.sdaacademy.conferenceroomreservationsystem.Organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddOrganizationRequest {
    @NotBlank(message = "Can't be null or blank!")
    @Size(min = 2, max = 20, message = "Must contain 2 to 20 characters!")
    private String organizationName;
}
