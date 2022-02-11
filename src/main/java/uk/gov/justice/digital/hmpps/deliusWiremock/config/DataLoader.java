package uk.gov.justice.digital.hmpps.deliusWiremock.config;

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
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.StaffRepository;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.TeamRepository;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.PrisonerSearchApiClient;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.dto.PrisonerDetailsResponse;

@Component
public class DataLoader implements ApplicationRunner {

  private final OffenderRepository offenderRepository;
  private final StaffRepository staffRepository;
  private final TeamRepository teamRepository;
  private final PrisonerSearchApiClient prisonerSearchApiClient;

  @Value("classpath:privateBeta.txt")
  Resource privateBetaCases;

  @Value("classpath:dev.txt")
  Resource devCases;

  @Value("classpath:penTest1Cases.txt")
  Resource penTest1Cases;

  @Value("classpath:penTest2Cases.txt")
  Resource penTest2Cases;

  @Value("classpath:acCases.txt")
  Resource acCases;

  @Value("classpath:unallocatedCases.txt")
  Resource unallocatedCases;

  @Autowired
  public DataLoader(
      OffenderRepository offenderRepository,
      StaffRepository staffRepository,
      TeamRepository teamRepository,
      PrisonerSearchApiClient prisonerSearchApiClient
  ) {
    this.offenderRepository = offenderRepository;
    this.staffRepository = staffRepository;
    this.teamRepository = teamRepository;
    this.prisonerSearchApiClient = prisonerSearchApiClient;
  }

  public void run(ApplicationArguments args) throws IOException {
    List<String> pb = IOUtils.readLines(devCases.getInputStream());
    addCases(pb, 2000L, "cvl");

    List<String> dev = IOUtils.readLines(penTest1Cases.getInputStream());
    addCases(dev, 3000L, "pt1");

    List<String> pt1 = IOUtils.readLines(penTest2Cases.getInputStream());
    addCases(pt1, 4000L, "pt2");

    List<String> pt2 = IOUtils.readLines(acCases.getInputStream());
    addCases(pt2, 5000L, "ac");

    List<String> ac = IOUtils.readLines(privateBetaCases.getInputStream());
    addCases(ac, 6000L, "pb");

    List<String> unallocated = IOUtils.readLines(unallocatedCases.getInputStream());
    addCases(unallocated, null, "pb");
  }

  private void addCases(List<String> nomisIds, Long staffId, String teamCode) {
    Faker faker = new Faker();
    List<OffenderEntity> offenders = new ArrayList<>();
    List<PrisonerDetailsResponse> prisonerList = prisonerSearchApiClient.getPrisoners(nomisIds);
    for (PrisonerDetailsResponse prisoner : prisonerList) {
      OffenderEntity offender = new OffenderEntity();

      offender.setNomsNumber(prisoner.getPrisonerNumber());
      offender.setCrnNumber(faker.regexify("[A-Z][0-9]{6}"));
      offender.setCroNumber(faker.regexify("[0-9]{1}/[0-9]{5}"));
      offender.setPncNumber(faker.regexify("[0-9]{4}/[0-9]{5}"));
      offender.setStaff(this.staffRepository.findByStaffIdentifier(staffId).orElse(null));
      offender.setTeam(this.teamRepository.findByTeamCode(teamCode).orElse(null));

      offenders.add(offender);
    }

    offenderRepository.saveAllAndFlush(offenders);
  }
}
