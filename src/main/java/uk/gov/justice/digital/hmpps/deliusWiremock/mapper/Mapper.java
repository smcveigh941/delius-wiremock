package uk.gov.justice.digital.hmpps.deliusWiremock.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.StaffEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.TeamEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.ManagedOffenderResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.StaffDetailResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.TeamManagedCaseResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.TeamResponse;

public class Mapper {

  private Mapper() {
  }

  public static StaffDetailResponse fromEntityToStaffDetailResponse(StaffEntity staffEntity) {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    TypeMap<StaffEntity, StaffDetailResponse> propertyMapper = modelMapper.createTypeMap(StaffEntity.class, StaffDetailResponse.class);
    propertyMapper.addMappings(mapper -> mapper.skip(StaffDetailResponse::setTeams));
    propertyMapper.addMappings(mapper -> mapper.<String>map(StaffEntity::getStaffForenames, (dest, v) -> dest.getStaff().setForenames(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(StaffEntity::getStaffSurname, (dest, v) -> dest.getStaff().setSurname(v)));

    TypeMap<TeamEntity, TeamResponse> teamMapper = modelMapper.createTypeMap(TeamEntity.class, TeamResponse.class);
    teamMapper.addMappings(mapper -> mapper.map(TeamEntity::getTeamCode, TeamResponse::setCode));
    teamMapper.addMappings(mapper -> mapper.map(TeamEntity::getTeamDescription, TeamResponse::setDescription));
    teamMapper.addMappings(mapper -> mapper.<String>map(TeamEntity::getLduCode, (dest, v) -> dest.getLocalDeliveryUnit().setCode(v)));
    teamMapper.addMappings(mapper -> mapper.<String>map(TeamEntity::getLduDescription, (dest, v) -> dest.getLocalDeliveryUnit().setDescription(v)));

    StaffDetailResponse result = modelMapper.map(staffEntity, StaffDetailResponse.class);

    List<TeamResponse> teams = staffEntity.getTeams().stream()
        .map(team -> modelMapper.map(team, TeamResponse.class))
        .collect(Collectors.toList());

    result.setTeams(teams);

    return result;
  }

  public static ManagedOffenderResponse fromEntityToManagedOffenderResponse(OffenderEntity offenderEntity) {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    return modelMapper.map(offenderEntity, ManagedOffenderResponse.class);
  }

  public static TeamManagedCaseResponse fromEntityToTeamManagedCaseResponse(OffenderEntity offenderEntity) {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    TypeMap<OffenderEntity, TeamManagedCaseResponse> propertyMapper = modelMapper.createTypeMap(OffenderEntity.class, TeamManagedCaseResponse.class);
    propertyMapper.addMappings(mapper -> mapper.skip(TeamManagedCaseResponse::setAllocated));
    propertyMapper.addMappings(mapper -> mapper.map(src -> src.getStaff().getStaffIdentifier(), TeamManagedCaseResponse::setStaffIdentifier));
    propertyMapper.addMappings(mapper -> mapper.map(src -> src.getStaff().getStaffForenames(), TeamManagedCaseResponse::setStaffForename));
    propertyMapper.addMappings(mapper -> mapper.map(src -> src.getStaff().getStaffSurname(), TeamManagedCaseResponse::setStaffSurname));

    TeamManagedCaseResponse result = modelMapper.map(offenderEntity, TeamManagedCaseResponse.class);

    result.setAllocated(offenderEntity.getStaff() != null);

    return result;
  }
}
