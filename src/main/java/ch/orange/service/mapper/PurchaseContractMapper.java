package ch.orange.service.mapper;

import ch.orange.domain.PurchaseContract;
import ch.orange.service.dto.PurchaseContractDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseContract} and its DTO {@link PurchaseContractDTO}.
 */
@Mapper(componentModel = "spring", uses = { PortMapper.class })
public interface PurchaseContractMapper extends EntityMapper<PurchaseContractDTO, PurchaseContract> {
    @Mapping(target = "port", source = "port", qualifiedByName = "id")
    PurchaseContractDTO toDto(PurchaseContract s);
}
