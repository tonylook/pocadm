package ch.orange.service;

import ch.orange.service.dto.PortDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ch.orange.domain.Port}.
 */
public interface PortService {
    /**
     * Save a port.
     *
     * @param portDTO the entity to save.
     * @return the persisted entity.
     */
    PortDTO save(PortDTO portDTO);

    /**
     * Partially updates a port.
     *
     * @param portDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PortDTO> partialUpdate(PortDTO portDTO);

    /**
     * Get all the ports.
     *
     * @return the list of entities.
     */
    List<PortDTO> findAll();

    /**
     * Get the "id" port.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PortDTO> findOne(Long id);

    /**
     * Delete the "id" port.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
