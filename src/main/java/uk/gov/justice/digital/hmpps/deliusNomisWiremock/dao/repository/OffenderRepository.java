package uk.gov.justice.digital.hmpps.deliusNomisWiremock.dao.repository;

import uk.gov.justice.digital.hmpps.deliusNomisWiremock.dao.entity.OffenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OffenderRepository extends JpaRepository<OffenderEntity, Long> {

}
