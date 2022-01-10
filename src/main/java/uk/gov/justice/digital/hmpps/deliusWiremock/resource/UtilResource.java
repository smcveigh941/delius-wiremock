package uk.gov.justice.digital.hmpps.deliusWiremock.resource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.PrisonerSearchApiClient;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.dto.PrisonerDetailsResponse;

@RestController
@RequestMapping
public class UtilResource {

  @Autowired
  PrisonerSearchApiClient prisonerSearchApiClient;

  @GetMapping("/getNomisIds")
  public List<String> getNomisIds(@RequestParam String prisonCode) {
    List<PrisonerDetailsResponse> result = prisonerSearchApiClient.getPrisonersByPrison(prisonCode).getContent();
    result = result.stream()
        .filter(managedCase -> managedCase.getParoleEligibilityDate() == null)
        .filter(managedCase -> managedCase.getLegalStatus() == null || !managedCase.getLegalStatus().equals("DEAD"))
        .filter(managedCase -> managedCase.getStatus() != null && managedCase.getStatus().startsWith("ACTIVE"))
        .filter(managedCase -> !managedCase.getIndeterminateSentence())
        .filter(managedCase -> managedCase.getReleaseDate() == null || managedCase.getReleaseDate().isAfter(LocalDate.now().plusDays(365)))
        .filter(managedCase -> managedCase.getConditionalReleaseDate() != null && managedCase.getConditionalReleaseDate().isAfter(LocalDate.now().plusDays(365)))
        .filter(managedCase -> managedCase.getLicenceExpiryDate() != null && managedCase.getLicenceExpiryDate().isAfter(LocalDate.now().plusDays(365)))
        .collect(Collectors.toList());

    return result.stream().map(PrisonerDetailsResponse::getPrisonerNumber).collect(Collectors.toList());
  }
}
