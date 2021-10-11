package uk.gov.justice.digital.hmpps.deliusWiremock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StaffDetailResponse {

  Long staffIdentifier;
  String username;
  String email;
  String telephoneNumber;
}
