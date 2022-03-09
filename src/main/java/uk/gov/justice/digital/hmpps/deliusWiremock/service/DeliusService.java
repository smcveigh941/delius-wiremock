package uk.gov.justice.digital.hmpps.deliusWiremock.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.StaffEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.TeamEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.OffenderRepository;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.StaffRepository;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.repository.TeamRepository;
import uk.gov.justice.digital.hmpps.deliusWiremock.exception.NotFoundException;

@Service
public class DeliusService {

  private final StaffRepository staffRepository;
  private final TeamRepository teamRepository;
  private final OffenderRepository offenderRepository;

  public DeliusService(
      StaffRepository staffRepository,
      TeamRepository teamRepository,
      OffenderRepository offenderRepository
  ) {
    this.staffRepository = staffRepository;
    this.teamRepository = teamRepository;
    this.offenderRepository = offenderRepository;
  }

  public Optional<StaffEntity> getStaffByUsername(String staffUsername) {
    return this.staffRepository.findByUsername(staffUsername);
  }

  public Optional<StaffEntity> getStaffByCode(String staffCode) {
    return this.staffRepository.findByStaffCode(staffCode);
  }

  public Optional<StaffEntity> getStaff(Long staffId) {
    return this.staffRepository.findByStaffIdentifier(staffId);
  }

  public List<StaffEntity> getStaff(List<String> staffUsernames) {
    return this.staffRepository.findByUsernameIn(staffUsernames);
  }

  public List<OffenderEntity> getAllOffendersByStaffId(Long staffId) {
    StaffEntity staff = this.staffRepository.findByStaffIdentifier(staffId).orElseThrow(() ->
        new NotFoundException(String.format("Staff with id %s not found", staffId)));;
    return staff.getManagedOffenders();
  }

  public List<OffenderEntity> getAllOffendersByTeamCode(String teamCode) {
    TeamEntity team = this.teamRepository.getByTeamCode(teamCode);
    return team.getManagedOffenders();
  }

  public OffenderEntity getOffenderByNomsId(String nomisId) {
    return this.offenderRepository.findByNomsNumber(nomisId).orElseThrow(() ->
        new NotFoundException(String.format("Offender with nomsNumber %s not found", nomisId)));
  }

  public OffenderEntity getOffenderByCrn(String crn) {
    return this.offenderRepository.findByCrnNumber(crn).orElseThrow(() ->
        new NotFoundException(String.format("Offender with crn %s not found", crn)));
  }

  public List<OffenderEntity> findOffendersByCrnIn(List<String> crns) {
    return this.offenderRepository.findByCrnNumberIn(crns);
  }

  public List<OffenderEntity> findOffendersByNomsNumberIn(List<String> nomsNumbers) {
    return this.offenderRepository.findByNomsNumberIn(nomsNumbers);
  }
}
