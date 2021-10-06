package uk.gov.justice.digital.hmpps.deliusWiremock.mapper;

import org.modelmapper.ModelMapper;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.ManagedOffenderResponse;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.PrisonerResponse;

public class Mapper {

  private static final ModelMapper modelMapper = new ModelMapper();

  private Mapper() {
  }

  public static ManagedOffenderResponse fromEntityToManagedOffenderResponse(OffenderEntity offenderEntity) {
    return modelMapper.map(offenderEntity, ManagedOffenderResponse.class);
  }

  public static PrisonerResponse fromEntityToPrisonerResponse(OffenderEntity offenderEntity) {
    modelMapper.typeMap(OffenderEntity.class, PrisonerResponse.class).addMapping(OffenderEntity::getNomsNumber, PrisonerResponse::setPrisonerNumber);
    return modelMapper.map(offenderEntity, PrisonerResponse.class);
  }
}
