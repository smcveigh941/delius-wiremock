package uk.gov.justice.digital.hmpps.deliusWiremock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class TeamResponse {

  AreaResponse localDeliveryUnit;
}
