package uk.gov.justice.digital.hmpps.deliusWiremock;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.OffenderRepository;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.PrisonerSearchApiClient;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.dto.PrisonerDetailsResponse;

@Component
public class DataLoader implements ApplicationRunner {

  private final OffenderRepository offenderRepository;
  private final PrisonerSearchApiClient prisonerSearchApiClient;

  @Value("#{'${dataset.size}'}")
  private Integer numberOfOffenders;

  @Autowired
  public DataLoader(OffenderRepository offenderRepository,
      PrisonerSearchApiClient prisonerSearchApiClient) {
    this.offenderRepository = offenderRepository;
    this.prisonerSearchApiClient = prisonerSearchApiClient;
  }

  public void run(ApplicationArguments args) {
    Faker faker = new Faker();
    List<OffenderEntity> offenders = new ArrayList<>();

    List<String> prisonCodes = Arrays.asList("BMI", "LEI", "MDI");

    List<PrisonerDetailsResponse> prisonerList = new ArrayList<>();

    for (String prisonCode : prisonCodes) {
      prisonerList.addAll(prisonerSearchApiClient.getPrisoners(prisonCode).getContent());
    }

    prisonerList = prisonerList.stream()
        .filter(prisoner -> prisoner.getConditionalReleaseDate() != null)
        .collect(Collectors.toList());

    for (int i = 0; i < numberOfOffenders; i++) {
      if (prisonerList.size() == i) {
        break;
      }

      OffenderEntity offender = new OffenderEntity();

      offender.setNomsNumber(prisonerList.get(i).getPrisonerNumber());
      offender.setCrnNumber(faker.regexify("[A-Z][0-9]{6}"));
      offender.setCroNumber(faker.regexify("[0-9]{1}/[0-9]{5}"));
      offender.setPncNumber(faker.regexify("[0-9]{4}/[0-9]{5}"));

      offenders.add(offender);
    }

    offenderRepository.saveAllAndFlush(offenders);
  }
}
