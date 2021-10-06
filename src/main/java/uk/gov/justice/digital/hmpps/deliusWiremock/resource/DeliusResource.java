package uk.gov.justice.digital.hmpps.deliusWiremock.resource;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.ManagedOffenderResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.StaffDetailResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.mapper.Mapper;
import uk.gov.justice.digital.hmpps.deliusWiremock.service.DeliusService;

@RestController
@RequestMapping
public class DeliusResource {

  private final DeliusService service;

  public DeliusResource(DeliusService service) {
    this.service = service;
  }

  @GetMapping(value = "/secure/staff/username/{username}")
  public StaffDetailResponse getStaffDetail() {
    return new StaffDetailResponse(2000L);
  }

  @GetMapping(value = "/secure/staff/staffIdentifier/{staffId}/managedOffenders")
  public List<ManagedOffenderResponse> getManagedOffenders() {
    return service.getOffenders().stream()
        .map(Mapper::fromEntityToManagedOffenderResponse)
        .collect(Collectors.toList());
  }
}
