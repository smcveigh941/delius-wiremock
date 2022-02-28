package uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.TeamEntity;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

  Optional<TeamEntity> findByTeamCode(String teamCode);
  TeamEntity getByTeamCode(String teamCode);
}
