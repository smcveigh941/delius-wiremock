package uk.gov.justice.digital.hmpps.deliusNomisWiremock.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.dto.ManagedOffenderResponse;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.dto.PrisonerResponse;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.dto.StaffDetailResponse;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.mapper.Mapper;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.service.DeliusService;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.service.NomisService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/nomis")
public class NomisResource {

  private final NomisService service;

  public NomisResource(NomisService service) {
    this.service = service;
  }

  @PostMapping(value="/prisoner-search/prisoner-numbers")
  public List<PrisonerResponse> getOffenders() {
    return service.getOffenders().stream()
            .map(Mapper::fromEntityToPrisonerResponse)
            .collect(Collectors.toList());
  }
}
