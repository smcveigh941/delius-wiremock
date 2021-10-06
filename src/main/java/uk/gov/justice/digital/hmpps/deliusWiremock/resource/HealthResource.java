package uk.gov.justice.digital.hmpps.deliusWiremock.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.justice.digital.hmpps.deliusWiremock.service.DeliusService;

@RestController
@RequestMapping
public class HealthResource {

  public HealthResource(DeliusService service) {
  }

  @GetMapping(value = "/ping")
  public String getStaffDetail() {
    return "Pong";
  }
}
