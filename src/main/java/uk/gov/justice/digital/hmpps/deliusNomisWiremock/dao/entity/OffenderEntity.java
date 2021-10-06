package uk.gov.justice.digital.hmpps.deliusNomisWiremock.dao.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  private String nomsNumber;
  private String firstName;
  private String lastName;
  private String crnNumber;
  private Date conditionalReleaseDate;
}
