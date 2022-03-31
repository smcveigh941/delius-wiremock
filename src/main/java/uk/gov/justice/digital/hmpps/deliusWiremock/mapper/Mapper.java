package uk.gov.justice.digital.hmpps.deliusWiremock.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.StaffEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.CaseloadResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.CommunityOrPrisonOffenderManager;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.OffenderManagerResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.ProbationerResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.StaffDetailResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.TeamResponse;

@Component
public class Mapper {

  private final ModelMapper modelMapper;

  public Mapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public StaffDetailResponse fromEntityToStaffDetailResponse(StaffEntity staffEntity) {
    StaffDetailResponse result = modelMapper.map(staffEntity, StaffDetailResponse.class);

    List<TeamResponse> teams = staffEntity.getTeams().stream()
        .map(team -> modelMapper.map(team, TeamResponse.class))
        .collect(Collectors.toList());

    result.setTeams(teams);

    return result;
  }

  public CaseloadResponse fromEntityToCaseloadResponse(OffenderEntity offenderEntity) {
    return modelMapper.map(offenderEntity, CaseloadResponse.class);
  }

  public CommunityOrPrisonOffenderManager fromEntityToCommunityOrPrisonOffenderManager(OffenderEntity offenderEntity) {
    return modelMapper.map(offenderEntity, CommunityOrPrisonOffenderManager.class);
  }

  public ProbationerResponse fromEntityToProbationerResponse(OffenderEntity offenderEntity) {
    ProbationerResponse result = modelMapper.map(offenderEntity, ProbationerResponse.class);

    if (offenderEntity.getStaff() != null) {
      OffenderManagerResponse offenderManager = modelMapper.map(offenderEntity.getStaff(), OffenderManagerResponse.class);
      offenderManager.setTeam(modelMapper.map(offenderEntity.getTeam(), TeamResponse.class));
      result.setOffenderManagers(List.of(offenderManager));
    } else {
      result.setOffenderManagers(List.of());
    }

    return result;
  }
}
