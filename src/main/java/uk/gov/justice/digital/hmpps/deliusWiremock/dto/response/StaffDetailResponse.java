package uk.gov.justice.digital.hmpps.deliusWiremock.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StaffDetailResponse {

  Long staffIdentifier;
  String staffCode;
  StaffResponse staff;
  List<TeamResponse> teams;
  String email;
  String telephoneNumber;
}
