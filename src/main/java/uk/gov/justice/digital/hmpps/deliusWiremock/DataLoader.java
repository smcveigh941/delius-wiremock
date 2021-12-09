package uk.gov.justice.digital.hmpps.deliusWiremock;

import com.github.javafaker.Faker;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.OffenderRepository;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.PrisonerSearchApiClient;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.dto.PrisonerDetailsResponse;

@Component
public class DataLoader implements ApplicationRunner {

  private final OffenderRepository offenderRepository;
  private final PrisonerSearchApiClient prisonerSearchApiClient;

  @Value("classpath:privateBeta.txt")
  Resource privateBetaCases;

  @Value("classpath:dev.txt")
  Resource devCases;

  @Autowired
  public DataLoader(OffenderRepository offenderRepository,
      PrisonerSearchApiClient prisonerSearchApiClient) {
    this.offenderRepository = offenderRepository;
    this.prisonerSearchApiClient = prisonerSearchApiClient;
  }

  public void run(ApplicationArguments args) throws IOException {
    List<String> pb = IOUtils.readLines(privateBetaCases.getInputStream());
    addCases(pb, true, false);

    List<String> dev = IOUtils.readLines(devCases.getInputStream());
    addCases(dev, false, true);
  }

  private void addCases(List<String> nomisIds, boolean privateBeta, boolean dev) {
    Faker faker = new Faker();
    List<OffenderEntity> offenders = new ArrayList<>();
    List<PrisonerDetailsResponse> prisonerList = prisonerSearchApiClient.getPrisoners(nomisIds);
    for (PrisonerDetailsResponse prisoner : prisonerList) {
      OffenderEntity offender = new OffenderEntity();

      offender.setNomsNumber(prisoner.getPrisonerNumber());
      offender.setCrnNumber(faker.regexify("[A-Z][0-9]{6}"));
      offender.setCroNumber(faker.regexify("[0-9]{1}/[0-9]{5}"));
      offender.setPncNumber(faker.regexify("[0-9]{4}/[0-9]{5}"));
      offender.setForPrivateBeta(privateBeta);
      offender.setForDevUsers(dev);

      offenders.add(offender);
    }

    offenderRepository.saveAllAndFlush(offenders);
  }
}
