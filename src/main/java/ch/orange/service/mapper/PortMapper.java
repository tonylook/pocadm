package ch.orange.service.mapper;

import ch.orange.domain.Port;
import ch.orange.service.dto.PortDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Port} and its DTO {@link PortDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PortMapper extends EntityMapper<PortDTO, Port> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PortDTO toDtoId(Port port);
}
