package uk.gov.justice.digital.hmpps.deliusWiremock.dto.response;

import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailResponse {

  String email;
  Boolean enabled = true;
  String firstName;
  String surname;
  List<Object> roles = Collections.emptyList();
  String username;
}
