package uk.gov.justice.digital.hmpps.deliusNomisWiremock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PrisonerResponse {
    String prisonerNumber;
    String firstName;
    String lastName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date conditionalReleaseDate;
}
