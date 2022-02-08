package ch.orange.service.mapper;

import ch.orange.domain.VesselTimeContract;
import ch.orange.service.dto.VesselTimeContractDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VesselTimeContract} and its DTO {@link VesselTimeContractDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VesselTimeContractMapper extends EntityMapper<VesselTimeContractDTO, VesselTimeContract> {}
