package uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
@Table(name = "offender")
public class OffenderEntity {

  @Id
  @GeneratedValue
  private Long id;

  private String nomsNumber;
  private String crnNumber;
  private String croNumber;
  private String pncNumber;

  @ManyToOne
  @JoinColumn(name="staff_id")
  private StaffEntity staff;

  @ManyToOne
  @JoinColumn(name="team_id", nullable = false)
  private TeamEntity team;
}
