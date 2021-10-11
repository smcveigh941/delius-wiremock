package uk.gov.justice.digital.hmpps.deliusWiremock.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ManagedOffenderResponse {

  Boolean currentOm = true;
  String nomsNumber;
  String crnNumber;
}
