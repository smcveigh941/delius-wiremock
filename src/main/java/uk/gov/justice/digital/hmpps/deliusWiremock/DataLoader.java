package uk.gov.justice.digital.hmpps.deliusWiremock;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.OffenderRepository;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.PrisonApiClient;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.PrisonerSearchApiClient;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.dto.PrisonerDetailsResponse;

@Component
public class DataLoader implements ApplicationRunner {

  private final OffenderRepository offenderRepository;
  private final PrisonApiClient prisonApiClient;
  private final PrisonerSearchApiClient prisonerSearchApiClient;
  @Value("#{'${dataset.size}'}")
  private Integer numberOfOffenders;

  @Autowired
  public DataLoader(OffenderRepository offenderRepository, PrisonApiClient prisonApiClient,
      PrisonerSearchApiClient prisonerSearchApiClient) {
    this.offenderRepository = offenderRepository;
    this.prisonApiClient = prisonApiClient;
    this.prisonerSearchApiClient = prisonerSearchApiClient;
  }

  public void run(ApplicationArguments args) {
    Faker faker = new Faker();
    List<OffenderEntity> offenders = new ArrayList<>();

    List<String> prisonIds = prisonApiClient.getPrisonIds();
    Collections.shuffle(prisonIds);

    List<PrisonerDetailsResponse> prisonerList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      prisonerList.addAll(prisonerSearchApiClient.getPrisoners(prisonIds.get(i)).getContent());
    }

    Collections.shuffle(prisonerList);

    for (int i = 0; i < numberOfOffenders; i++) {
      if (prisonerList.size() == i) {
        break;
      }

      OffenderEntity offender = new OffenderEntity();

      offender.setNomsNumber(prisonerList.get(i).getPrisonerNumber());
      offender.setCrnNumber(faker.regexify("[A-Z][0-9]{6}"));

      offenders.add(offender);
    }

    offenderRepository.saveAllAndFlush(offenders);
  }
}
