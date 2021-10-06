package uk.gov.justice.digital.hmpps.deliusWiremock.mapper;

import org.modelmapper.ModelMapper;
import uk.gov.justice.digital.hmpps.deliusWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusWiremock.dto.ManagedOffenderResponse;

public class Mapper {

  private static final ModelMapper modelMapper = new ModelMapper();

  private Mapper() {
  }

  public static ManagedOffenderResponse fromEntityToManagedOffenderResponse(
      OffenderEntity offenderEntity) {
    return modelMapper.map(offenderEntity, ManagedOffenderResponse.class);
  }
}
