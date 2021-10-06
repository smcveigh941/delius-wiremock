package uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PrisonerDetailsResponse {

  private String prisonerNumber;
  private LocalDate conditionalReleaseDate;
}
