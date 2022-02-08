package ch.orange.service.mapper;

import ch.orange.domain.VesselVoyageContract;
import ch.orange.service.dto.VesselVoyageContractDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VesselVoyageContract} and its DTO {@link VesselVoyageContractDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VesselVoyageContractMapper extends EntityMapper<VesselVoyageContractDTO, VesselVoyageContract> {}
