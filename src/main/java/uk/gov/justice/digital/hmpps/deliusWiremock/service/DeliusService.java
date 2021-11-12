package uk.gov.justice.digital.hmpps.deliusWiremock.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.OffenderRepository;

@Service
public class DeliusService {

  private final OffenderRepository repository;

  public DeliusService(OffenderRepository repository) {
    this.repository = repository;
  }

  public List<OffenderEntity> getApOffenders() {
    return this.repository.findAllByLicenceEligibility("AP");
  }

  public List<OffenderEntity> getPssOffenders() {
    return this.repository.findAllByLicenceEligibility("PSS");
  }

  public List<OffenderEntity> getApPlusPssOffenders() {
    return this.repository.findAllByLicenceEligibility("AP_PSS");
  }

  public Optional<OffenderEntity> getOffender(String nomisId) {
    return this.repository.findByNomsNumber(nomisId);
  }
}
