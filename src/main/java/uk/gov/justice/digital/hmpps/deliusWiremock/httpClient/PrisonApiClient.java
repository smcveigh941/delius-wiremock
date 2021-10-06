package uk.gov.justice.digital.hmpps.deliusWiremock.httpClient;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.dto.PrisonDetailsResponse;

@Component
public class PrisonApiClient {

  private final RestClient restClient;
  @Value("${prisonApi.host}")
  private String prisonApiHost;

  public PrisonApiClient(RestClient restClient) {
    this.restClient = restClient;
  }

  public List<String> getPrisonIds() {
    List<PrisonDetailsResponse> prisonList = this.restClient.getList(
        prisonApiHost + "/api/agencies/prison", new ParameterizedTypeReference<>() {
        });
    return prisonList.stream().map(PrisonDetailsResponse::getAgencyId).collect(Collectors.toList());
  }
}
