package uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository;

import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffenderRepository extends JpaRepository<OffenderEntity, Long> {

}
