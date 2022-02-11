package uk.gov.justice.digital.hmpps.deliusWiremock.config;

import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.TeamRepository;
import uk.gov.justice.digital.hmpps.deliusWiremock.exception.NotFoundException;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.PrisonerSearchApiClient;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.dto.PrisonerDetailsResponse;

@Component
public class DataLoader implements ApplicationRunner {

  private final OffenderRepository offenderRepository;
  private final TeamRepository teamRepository;
  private final PrisonerSearchApiClient prisonerSearchApiClient;

  @Value("classpath:privateBeta.txt")
  Resource privateBetaCases;

  @Value("classpath:dev.txt")
  Resource devCases;

  @Autowired
  public DataLoader(
      OffenderRepository offenderRepository,
      TeamRepository teamRepository,
      PrisonerSearchApiClient prisonerSearchApiClient
  ) {
    this.offenderRepository = offenderRepository;
    this.teamRepository = teamRepository;
    this.prisonerSearchApiClient = prisonerSearchApiClient;
  }

  @Transactional
  public void run(ApplicationArguments args) throws IOException {
    List<String> cvl = IOUtils.readLines(devCases.getInputStream());
    addCases(cvl, "cvl");

    //Exclude the devs from getting allocated to user research cases
    List<String> pb = IOUtils.readLines(privateBetaCases.getInputStream());
    addCases(pb, "pb", List.of(1000L, 2000L));
  }

  private void addCases(List<String> nomisIds, String teamCode, List<Long> staffIdsNotToAllocate) {
    Faker faker = new Faker();
    List<OffenderEntity> offenders = new ArrayList<>();
    List<PrisonerDetailsResponse> prisonerList = prisonerSearchApiClient.getPrisoners(nomisIds);

    TeamEntity team = this.teamRepository.findByTeamCode(teamCode)
        .orElseThrow(NotFoundException::new);

    List<StaffEntity> staffInTeam = team
        .getStaff()
        .stream()
        .filter(staffEntity -> !staffIdsNotToAllocate.contains(staffEntity.getStaffIdentifier()))
        .sorted(Comparator.comparing(StaffEntity::getStaffIdentifier))
        .collect(Collectors.toList());

    List<List<PrisonerDetailsResponse>> offendersSharedAmongTeamMembers = Lists.partition(
        prisonerList, prisonerList.size() / staffInTeam.size());

    for (int i = 0; i < offendersSharedAmongTeamMembers.size(); i++) {
      List<PrisonerDetailsResponse> shareOfCases = offendersSharedAmongTeamMembers.get(i);
      for (PrisonerDetailsResponse prisoner : shareOfCases) {
        OffenderEntity offenderEntity = new OffenderEntity();

        offenderEntity.setNomsNumber(prisoner.getPrisonerNumber());
        offenderEntity.setCrnNumber(faker.regexify("[A-Z][0-9]{6}"));
        offenderEntity.setCroNumber(faker.regexify("[0-9]{1}/[0-9]{5}"));
        offenderEntity.setPncNumber(faker.regexify("[0-9]{4}/[0-9]{5}"));
        offenderEntity.setStaff(i < staffInTeam.size() ? staffInTeam.get(i) : null);
        offenderEntity.setTeam(team);

        offenders.add(offenderEntity);
      }
    }

    offenderRepository.saveAllAndFlush(offenders);
  }

  private void addCases(List<String> nomisIds, String teamCode) {
    addCases(nomisIds, teamCode, Collections.emptyList());
  }
}
