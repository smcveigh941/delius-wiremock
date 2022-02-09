package uk.gov.justice.digital.hmpps.deliusWiremock.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CommunityOrPrisonOffenderManager {
  Boolean isResponsibleOfficer = true;
  Long staffId;
  AreaResponse probationArea;
  TeamResponse team;
}
