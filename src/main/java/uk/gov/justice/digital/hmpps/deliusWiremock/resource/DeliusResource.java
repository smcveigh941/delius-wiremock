package uk.gov.justice.digital.hmpps.deliusWiremock.resource;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.StaffEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.request.SearchProbationerRequest;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.CommunityOrPrisonOffenderManager;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.ManagedOffenderResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.ProbationerResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.StaffDetailResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.TeamManagedCaseResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.exception.NotFoundException;
import uk.gov.justice.digital.hmpps.deliusWiremock.mapper.Mapper;
import uk.gov.justice.digital.hmpps.deliusWiremock.service.DeliusService;

@Transactional
@RestController
@RequestMapping
public class DeliusResource {

  private final DeliusService service;

  public DeliusResource(DeliusService service) {
    this.service = service;
  }

  @GetMapping(value = "/secure/staff/username/{username}")
  public StaffDetailResponse getStaffDetail(@PathVariable String username)
      throws NotFoundException {
    username = username.toLowerCase();

    StaffEntity staff = this.service.getStaff(username).orElse(this.service.getStaff(2000L).get());

    if (staff.getStaffIdentifier() == 2000L) {
      staff.setUsername(username);
    }

    return Mapper.fromEntityToStaffDetailResponse(staff);
  }

  @GetMapping(value = "/secure/staff/staffIdentifier/{staffId}")
  public StaffDetailResponse getStaffDetailByStaffIdentifier(@PathVariable Long staffId)
      throws NotFoundException {

    StaffEntity staff = this.service.getStaff(staffId).orElseThrow(() -> new NotFoundException("Staff member not found"));

    return Mapper.fromEntityToStaffDetailResponse(staff);
  }

  @PostMapping(value = "/secure/staff/list")
  public List<StaffDetailResponse> getStaffDetailByList(@RequestBody List<String> staffUsernames) {
    staffUsernames = staffUsernames.stream().filter(Objects::nonNull).map(String::toLowerCase).collect(Collectors.toList());

    List<StaffEntity> staff = this.service.getStaff(staffUsernames);

    List<StaffDetailResponse> response = staff.stream().map(Mapper::fromEntityToStaffDetailResponse).collect(Collectors.toList());

    if (response.size() != staffUsernames.size()) {
      staffUsernames.stream()
          .filter(s -> !response.stream().map(StaffDetailResponse::getUsername).collect(Collectors.toList()).contains(s))
          .forEach(s -> {
            StaffDetailResponse extraStaff = Mapper.fromEntityToStaffDetailResponse(this.service.getStaff(2000L).get());
            extraStaff.setUsername(s);
            response.add(extraStaff);
          });
    }

    return response;
  }

  @GetMapping(value = "/secure/staff/staffIdentifier/{staffId}/managedOffenders")
  public List<ManagedOffenderResponse> getManagedOffenders(@PathVariable long staffId) {
    return service.getAllOffendersByStaffId(staffId).stream()
        .map(Mapper::fromEntityToManagedOffenderResponse)
        .collect(Collectors.toList());
  }

  @Cacheable("teamOffenders")
  @GetMapping(value = "/secure/teams/managedOffenders")
  public List<TeamManagedCaseResponse> getManagedOffendersByTeam(
      @RequestParam(name = "teamCode") List<String> teamCodes) {
    return service.getAllOffendersByTeamCodes(teamCodes).stream()
        .map(Mapper::fromEntityToTeamManagedCaseResponse)
        .collect(Collectors.toList());
  }

  @GetMapping(value = "/secure/offenders/crn/{crn}/allOffenderManagers")
  public List<CommunityOrPrisonOffenderManager> getManagersForAnOffender(@PathVariable String crn) {
    return service.getAllManagersForAnOffender(crn).stream()
        .map(Mapper::fromEntityToCommunityOrPrisonOffenderManager)
        .collect(Collectors.toList());
  }

  @PostMapping(value = "/search")
  public List<ProbationerResponse> getProbationer(@RequestBody SearchProbationerRequest body) {
    ProbationerResponse response = Mapper.fromEntityToProbationerResponse(
        service.getOffender(body.getNomsNumber()));

    return List.of(response);
  }
}
