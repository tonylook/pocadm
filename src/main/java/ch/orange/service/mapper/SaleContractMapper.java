package ch.orange.service.mapper;

import ch.orange.domain.SaleContract;
import ch.orange.service.dto.SaleContractDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SaleContract} and its DTO {@link SaleContractDTO}.
 */
@Mapper(componentModel = "spring", uses = { PortMapper.class })
public interface SaleContractMapper extends EntityMapper<SaleContractDTO, SaleContract> {
    @Mapping(target = "port", source = "port", qualifiedByName = "id")
    SaleContractDTO toDto(SaleContract s);
}
