package uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;

@Repository
public interface OffenderRepository extends JpaRepository<OffenderEntity, Long> {

  Optional<OffenderEntity> findByNomsNumber(String nomisId);
  List<OffenderEntity> findAllByStaffIdEquals(Long staffId);
}
