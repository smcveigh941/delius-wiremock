package uk.gov.justice.digital.hmpps.deliusNomisWiremock.service;

import org.springframework.stereotype.Service;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.dao.repository.OffenderRepository;

import java.util.List;


@Service
public class NomisService {
  private final OffenderRepository repository;

  public NomisService(OffenderRepository repository) {
    this.repository = repository;
  }

  public List<OffenderEntity> getOffenders() {
    return this.repository.findAll();
  }
}
