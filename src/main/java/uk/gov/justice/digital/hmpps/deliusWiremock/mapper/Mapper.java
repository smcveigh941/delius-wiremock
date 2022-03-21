package uk.gov.justice.digital.hmpps.deliusWiremock.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.StaffEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.TeamEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.AreaResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.CaseloadResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.CommunityOrPrisonOffenderManager;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.ManagedOffenderResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.OffenderManagerResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.response.ProbationerResponse;
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
    propertyMapper.addMappings(mapper -> mapper.<String>map(StaffEntity::getProbationAreaCode, (dest, v) -> dest.getProbationArea().setCode(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(StaffEntity::getProbationAreaDescription, (dest, v) -> dest.getProbationArea().setDescription(v)));

    TypeMap<TeamEntity, TeamResponse> teamMapper = modelMapper.createTypeMap(TeamEntity.class, TeamResponse.class);
    teamMapper.addMappings(mapper -> mapper.<String>map(TeamEntity::getBoroughCode, (dest, v) -> dest.getBorough().setCode(v)));
    teamMapper.addMappings(mapper -> mapper.<String>map(TeamEntity::getBoroughDescription, (dest, v) -> dest.getBorough().setDescription(v)));
    teamMapper.addMappings(mapper -> mapper.<String>map(TeamEntity::getDistrictCode, (dest, v) -> dest.getDistrict().setCode(v)));
    teamMapper.addMappings(mapper -> mapper.<String>map(TeamEntity::getDistrictDescription, (dest, v) -> dest.getDistrict().setDescription(v)));
    teamMapper.addMappings(mapper -> mapper.map(TeamEntity::getTeamCode, TeamResponse::setCode));
    teamMapper.addMappings(mapper -> mapper.map(TeamEntity::getTeamDescription, TeamResponse::setDescription));

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

  public static CaseloadResponse fromEntityToCaseloadResponse(OffenderEntity offenderEntity) {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    TypeMap<OffenderEntity, CaseloadResponse> propertyMapper = modelMapper.createTypeMap(OffenderEntity.class, CaseloadResponse.class);
    propertyMapper.addMappings(mapper -> mapper.map(OffenderEntity::getCrnNumber, CaseloadResponse::setOffenderCrn));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getStaff().getStaffForenames(), (dest, v) -> dest.getStaff().setForenames(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getStaff().getStaffSurname(), (dest, v) -> dest.getStaff().setSurname(v)));
    propertyMapper.addMappings(mapper -> mapper.<Long>map(src -> src.getStaff().getStaffIdentifier(), CaseloadResponse::setStaffIdentifier));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getTeamCode(), (dest, v) -> dest.getTeam().setCode(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getTeamDescription(), (dest, v) -> dest.getTeam().setDescription(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getBoroughCode(), (dest, v) -> dest.getTeam().getBorough().setCode(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getBoroughDescription(), (dest, v) -> dest.getTeam().getBorough().setDescription(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getDistrictCode(), (dest, v) -> dest.getTeam().getDistrict().setCode(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getDistrictDescription(), (dest, v) -> dest.getTeam().getDistrict().setDescription(v)));


    return modelMapper.map(offenderEntity, CaseloadResponse.class);
  }

  public static CommunityOrPrisonOffenderManager fromEntityToCommunityOrPrisonOffenderManager(OffenderEntity offenderEntity) {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    TypeMap<OffenderEntity, CommunityOrPrisonOffenderManager> propertyMapper = modelMapper.createTypeMap(OffenderEntity.class, CommunityOrPrisonOffenderManager.class);
    propertyMapper.addMappings(mapper -> mapper.map(src -> src.getStaff().getStaffIdentifier(), CommunityOrPrisonOffenderManager::setStaffId));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getStaff().getProbationAreaCode(), (dest, v) -> dest.getProbationArea().setCode(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getStaff().getProbationAreaDescription(), (dest, v) -> dest.getProbationArea().setDescription(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getTeamCode(), (dest, v) -> dest.getTeam().setCode(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getTeamDescription(), (dest, v) -> dest.getTeam().setDescription(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getBoroughCode(), (dest, v) -> dest.getTeam().getBorough().setCode(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getBoroughDescription(), (dest, v) -> dest.getTeam().getBorough().setDescription(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getDistrictCode(), (dest, v) -> dest.getTeam().getDistrict().setCode(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getDistrictDescription(), (dest, v) -> dest.getTeam().getDistrict().setDescription(v)));

    return modelMapper.map(offenderEntity, CommunityOrPrisonOffenderManager.class);
  }

  public static ProbationerResponse fromEntityToProbationerResponse(OffenderEntity offenderEntity) {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    TypeMap<OffenderEntity, ProbationerResponse> propertyMapper = modelMapper.createTypeMap(OffenderEntity.class, ProbationerResponse.class);
    propertyMapper.addMappings(mapper -> mapper.skip(ProbationerResponse::setOffenderManagers));
    propertyMapper.addMappings(mapper -> mapper.<String>map(OffenderEntity::getNomsNumber, (dest, v) -> dest.getOtherIds().setNomsNumber(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(OffenderEntity::getCrnNumber, (dest, v) -> dest.getOtherIds().setCrn(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(OffenderEntity::getCroNumber, (dest, v) -> dest.getOtherIds().setCroNumber(v)));
    propertyMapper.addMappings(mapper -> mapper.<String>map(OffenderEntity::getPncNumber, (dest, v) -> dest.getOtherIds().setPncNumber(v)));

    TypeMap<StaffEntity, OffenderManagerResponse> staffMapper = modelMapper.createTypeMap(StaffEntity.class, OffenderManagerResponse.class);
    staffMapper.addMappings(mapper -> mapper.skip(OffenderManagerResponse::setTeam));
    staffMapper.addMappings(mapper -> mapper.<String>map(StaffEntity::getStaffCode, (dest, v) -> dest.getStaff().setCode(v)));
    staffMapper.addMappings(mapper -> mapper.<String>map(StaffEntity::getStaffForenames, (dest, v) -> dest.getStaff().setForenames(v)));
    staffMapper.addMappings(mapper -> mapper.<String>map(StaffEntity::getStaffSurname, (dest, v) -> dest.getStaff().setSurname(v)));
    staffMapper.addMappings(mapper -> mapper.<String>map(StaffEntity::getProbationAreaCode, (dest, v) -> dest.getProbationArea().setCode(v)));
    staffMapper.addMappings(mapper -> mapper.<String>map(StaffEntity::getProbationAreaDescription, (dest, v) -> dest.getProbationArea().setDescription(v)));

    TypeMap<TeamEntity, TeamResponse> teamMapper = modelMapper.createTypeMap(TeamEntity.class, TeamResponse.class);
    teamMapper.addMappings(mapper -> mapper.map(TeamEntity::getTeamCode, TeamResponse::setCode));
    teamMapper.addMappings(mapper -> mapper.map(TeamEntity::getTeamDescription, TeamResponse::setDescription));
    teamMapper.addMappings(mapper -> mapper.<String>map(TeamEntity::getBoroughCode, (dest, v) -> dest.getBorough().setCode(v)));
    teamMapper.addMappings(mapper -> mapper.<String>map(TeamEntity::getBoroughDescription, (dest, v) -> dest.getBorough().setDescription(v)));
    teamMapper.addMappings(mapper -> mapper.<String>map(TeamEntity::getDistrictCode, (dest, v) -> dest.getDistrict().setCode(v)));
    teamMapper.addMappings(mapper -> mapper.<String>map(TeamEntity::getDistrictDescription, (dest, v) -> dest.getDistrict().setDescription(v)));

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
