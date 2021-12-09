package uk.gov.justice.digital.hmpps.deliusWiremock.httpClient;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.dto.PrisonerDetailsResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.dto.SearchByListOfNomisIdRequest;

@Component
public class PrisonerSearchApiClient {

  private final RestClient restClient;
  @Value("${prisonerSearchApi.host}")
  private String prisonerSearchApiHost;

  public PrisonerSearchApiClient(RestClient restClient) {
    this.restClient = restClient;
  }

  public List<PrisonerDetailsResponse> getPrisoners(List<String> nomisIds) {
    return this.restClient.post(
        prisonerSearchApiHost + "/prisoner-search/prisoner-numbers",
        SearchByListOfNomisIdRequest.builder().prisonerNumbers(nomisIds).build(),
        new ParameterizedTypeReference<>() {
        });
  }
}
