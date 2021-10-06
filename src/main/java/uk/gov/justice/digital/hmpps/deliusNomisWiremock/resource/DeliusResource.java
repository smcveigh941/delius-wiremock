package uk.gov.justice.digital.hmpps.deliusNomisWiremock.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.dto.ManagedOffenderResponse;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.dto.StaffDetailResponse;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.mapper.Mapper;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.service.DeliusService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/delius")
public class DeliusResource {

  private final DeliusService service;

  public DeliusResource(DeliusService service) {
    this.service = service;
  }

  @GetMapping(value="/secure/staff/username/{username}")
  public StaffDetailResponse getStaffDetail() {
    return new StaffDetailResponse(2000L);
  }

  @GetMapping(value="/secure/staff/staffIdentifier/{staffId}/managedOffenders")
  public List<ManagedOffenderResponse> getManagedOffenders() {
    return service.getOffenders().stream()
            .map(Mapper::fromEntityToManagedOffenderResponse)
            .collect(Collectors.toList());
  }
}
