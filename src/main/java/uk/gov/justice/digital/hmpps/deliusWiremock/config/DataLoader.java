package uk.gov.justice.digital.hmpps.deliusWiremock.config;

import com.github.javafaker.Faker;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.StaffEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.TeamEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.OffenderRepository;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.StaffRepository;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.TeamRepository;
import uk.gov.justice.digital.hmpps.deliusWiremock.exception.NotFoundException;
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

  @Value("classpath:tim.txt")
  Resource timCases;

  @Value("classpath:stephen.txt")
  Resource stephenCases;

  @Value("classpath:cvl_com.txt")
  Resource cvlComCases;

  @Value("classpath:unallocated.txt")
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

  @Transactional
  public void run(ApplicationArguments args) throws IOException {
    List<String> stephen = IOUtils.readLines(stephenCases.getInputStream());
    addCases(stephen, "cvl", 1000L);

    List<String> tim = IOUtils.readLines(timCases.getInputStream());
    addCases(tim, "cvl", 2000L);

    List<String> cvlCom = IOUtils.readLines(cvlComCases.getInputStream());
    addCases(cvlCom, "cvl", 3000L);

    List<String> pb = IOUtils.readLines(privateBetaCases.getInputStream());
    addCases(pb, "pb", 9000L);

    List<String> unallocated = IOUtils.readLines(unallocatedCases.getInputStream());
    addCases(unallocated, "cvl", null);
  }

  private void addCases(List<String> nomisIds, String teamCode, Long staffId) {
    Faker faker = new Faker(new Random(12345));
    List<OffenderEntity> offenders = new ArrayList<>();
    List<PrisonerDetailsResponse> prisonerList = prisonerSearchApiClient.getPrisoners(nomisIds);

    TeamEntity team = this.teamRepository.findByTeamCode(teamCode)
        .orElseThrow(NotFoundException::new);

    StaffEntity staff = this.staffRepository.findByStaffIdentifier(staffId).orElse(null);

    for (PrisonerDetailsResponse prisoner : prisonerList) {
      OffenderEntity offenderEntity = new OffenderEntity();

      offenderEntity.setNomsNumber(prisoner.getPrisonerNumber());
      offenderEntity.setCrnNumber(faker.regexify("[A-Z][0-9]{6}"));
      offenderEntity.setCroNumber(faker.regexify("[0-9]{1}/[0-9]{5}"));
      offenderEntity.setPncNumber(faker.regexify("[0-9]{4}/[0-9]{5}"));
      offenderEntity.setStaff(staff);
      offenderEntity.setTeam(team);

      offenders.add(offenderEntity);
    }

    offenderRepository.saveAllAndFlush(offenders);
  }
}
