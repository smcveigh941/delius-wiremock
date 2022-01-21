package uk.gov.justice.digital.hmpps.deliusWiremock.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.request.SearchProbationerRequest;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.AreaResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.ManagedOffenderResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.OffenderManagerResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.ProbationerIdsResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.ProbationerResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.StaffDetailResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.StaffResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.TeamResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.mapper.Mapper;
import uk.gov.justice.digital.hmpps.deliusWiremock.service.DeliusService;

@RestController
@RequestMapping
public class DeliusResource {

  private final DeliusService service;

  @Value("#{'${devUsers}'}")
  private String devUsers;

  public DeliusResource(DeliusService service) {
    this.service = service;
  }

  @GetMapping(value = "/secure/staff/username/{username}")
  public StaffDetailResponse getStaffDetail(@PathVariable String username) {
    username = username.toLowerCase();

    long staffId = 2000L;

    if (Arrays.asList(devUsers.split(",")).contains(username)) {
      staffId = 3000L;
    }

    if (username.equals("pt_com1")) {
      staffId = 4000L;
    }

    if (username.equals("pt_com2")) {
      staffId = 5000L;
    }

    if (username.equals("ac_com")) {
      staffId = 6000L;
    }

    StaffResponse staff = new StaffResponse("COM", "User");
    AreaResponse localDeliveryUnit = new AreaResponse("N55UNA", "Nottingham City District");
    List<TeamResponse> teams = List.of(
        new TeamResponse("A", "Team A", localDeliveryUnit, "0800001066"),
        new TeamResponse("B", "Team B", localDeliveryUnit, "0800001066")
    );
    return new StaffDetailResponse(staffId, "X12345", staff, teams, username.contains("@") ? username : username + "@probation.gov.uk", "07786 989777");
  }

  @GetMapping(value = "/secure/staff/staffIdentifier/{staffId}/managedOffenders")
  public List<ManagedOffenderResponse> getManagedOffenders(@PathVariable long staffId) {
    return service.getAllOffendersByStaffId(staffId).stream()
        .map(Mapper::fromEntityToManagedOffenderResponse)
        .collect(Collectors.toList());
  }

  @PostMapping(value = "/search")
  public List<ProbationerResponse> getProbationer(@RequestBody SearchProbationerRequest body) {
    StaffResponse staff = new StaffResponse("COM", "User");
    AreaResponse localDeliveryUnit = new AreaResponse("N55UNA", "Nottingham City District");
    TeamResponse team = new TeamResponse("A", "Team A", localDeliveryUnit, "0800001066");
    AreaResponse probationArea = new AreaResponse("N55", "NPS Yorkshire and Humberside");

    OffenderManagerResponse om = new OffenderManagerResponse();
    om.setStaff(staff);
    om.setTeam(team);
    om.setProbationArea(probationArea);

    ProbationerIdsResponse ids = service.getOffender(body.getNomsNumber())
        .map(Mapper::fromEntityToProbationerIds).orElse(null);

    return List.of(new ProbationerResponse(ids, List.of(om)));
  }
}
