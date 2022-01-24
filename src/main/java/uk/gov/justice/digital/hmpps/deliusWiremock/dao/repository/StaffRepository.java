package uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.StaffEntity;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Long> {
  Optional<StaffEntity> findByStaffIdentifier(Long staffIdentifier);
  Optional<StaffEntity> findByStaffUsername(String staffUsername);
  List<StaffEntity> findByStaffUsernameIn(List<String> staffUsernames);
}
