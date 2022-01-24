package uk.gov.justice.digital.hmpps.deliusWiremock.resource;

import java.util.List;
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
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.AreaResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.ManagedOffenderResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.OffenderManagerResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.ProbationerIdsResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.ProbationerResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.StaffDetailResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.StaffResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.TeamManagedCaseResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.TeamResponse;
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

    long staffId = 2000L;

    if (username.equals("pt_com1")) {
      staffId = 3000L;
    }

    if (username.equals("pt_com2")) {
      staffId = 4000L;
    }

    if (username.equals("ac_com")) {
      staffId = 5000L;
    }

    if (username.equals("cvl_beta_dev")) {
      staffId = 6000L;
    }

    StaffEntity staff = this.service.getStaff(staffId)
        .orElseThrow(() -> new NotFoundException("Staff member not found"));

    return Mapper.fromEntityToStaffDetailResponse(staff);
  }

  @GetMapping(value = "/secure/staff/staffIdentifier/{staffIdentifier}")
  public StaffDetailResponse getStaffDetailByStaffId(@PathVariable Long staffIdentifier)
      throws NotFoundException {
    StaffEntity staff = this.service.getStaff(staffIdentifier)
        .orElseThrow(() -> new NotFoundException("Staff member not found"));

    return Mapper.fromEntityToStaffDetailResponse(staff);
  }
  
  @GetMapping(value = "/secure/staff/staffIdentifier/{staffId}/managedOffenders")
  public List<ManagedOffenderResponse> getManagedOffenders(@PathVariable long staffId) {
    return service.getAllOffendersByStaffId(staffId).stream()
        .map(Mapper::fromEntityToManagedOffenderResponse)
        .collect(Collectors.toList());
  }

  @Cacheable("teamOffenders")
  @GetMapping(value = "/secure/teams/managedOffenders")
  public List<TeamManagedCaseResponse> getManagedOffendersByTeam(@RequestParam(name = "teamCode") List<String> teamCodes) {
    return service.getAllOffendersByTeamCodes(teamCodes).stream()
        .map(Mapper::fromEntityToTeamManagedCaseResponse)
        .collect(Collectors.toList());
  }

  @PostMapping(value = "/search")
  public List<ProbationerResponse> getProbationer(@RequestBody SearchProbationerRequest body) {
    ProbationerResponse response = Mapper.fromEntityToProbationerResponse(service.getOffender(body.getNomsNumber()));

    return List.of(response);
  }
}
