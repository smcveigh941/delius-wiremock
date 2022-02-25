package uk.gov.justice.digital.hmpps.deliusWiremock.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProbationerIdsResponse {

  String nomsNumber;
  String crn;
  String croNumber;
  String pncNumber;
}
