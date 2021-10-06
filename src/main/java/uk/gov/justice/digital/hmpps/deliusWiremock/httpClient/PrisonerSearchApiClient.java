package uk.gov.justice.digital.hmpps.deliusWiremock.httpClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.dto.PrisonerSearchResponse;

@Component
public class PrisonerSearchApiClient {

  private final RestClient restClient;
  @Value("${prisonerSearchApi.host}")
  private String prisonerSearchApiHost;

  public PrisonerSearchApiClient(RestClient restClient) {
    this.restClient = restClient;
  }

  public PrisonerSearchResponse getPrisoners(String prisonId) {
    return this.restClient.get(
        prisonerSearchApiHost + "/prisoner-search/prison/" + prisonId + "?size=50",
        PrisonerSearchResponse.class);
  }
}
