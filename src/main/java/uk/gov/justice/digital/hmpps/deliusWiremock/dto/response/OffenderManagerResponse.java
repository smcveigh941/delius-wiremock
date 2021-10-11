package uk.gov.justice.digital.hmpps.deliusWiremock.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OffenderManagerResponse {

  Boolean active = true;
  StaffResponse staff;
  TeamResponse team;
  AreaResponse probationArea;
}
