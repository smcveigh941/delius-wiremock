package uk.gov.justice.digital.hmpps.deliusWiremock.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StaffResponse {

  String forenames;
  String surname;
  Boolean unallocated = false;
}
