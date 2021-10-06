package uk.gov.justice.digital.hmpps.deliusWiremock.service;

import java.util.List;
import org.springframework.stereotype.Service;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.OffenderRepository;


@Service
public class DeliusService {

  private final OffenderRepository repository;

  public DeliusService(OffenderRepository repository) {
    this.repository = repository;
  }

  public List<OffenderEntity> getOffenders() {
    return this.repository.findAll();
  }
}
