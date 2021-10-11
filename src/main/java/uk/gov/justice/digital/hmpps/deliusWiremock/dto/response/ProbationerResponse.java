package uk.gov.justice.digital.hmpps.deliusWiremock.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProbationerResponse {

  ProbationerIdsResponse otherIds;
  List<OffenderManagerResponse> offenderManagers;
}
