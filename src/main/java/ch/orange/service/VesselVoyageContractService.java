package ch.orange.service;

import ch.orange.service.dto.VesselVoyageContractDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ch.orange.domain.VesselVoyageContract}.
 */
public interface VesselVoyageContractService {
    /**
     * Save a vesselVoyageContract.
     *
     * @param vesselVoyageContractDTO the entity to save.
     * @return the persisted entity.
     */
    VesselVoyageContractDTO save(VesselVoyageContractDTO vesselVoyageContractDTO);

    /**
     * Partially updates a vesselVoyageContract.
     *
     * @param vesselVoyageContractDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VesselVoyageContractDTO> partialUpdate(VesselVoyageContractDTO vesselVoyageContractDTO);

    /**
     * Get all the vesselVoyageContracts.
     *
     * @return the list of entities.
     */
    List<VesselVoyageContractDTO> findAll();

    /**
     * Get the "id" vesselVoyageContract.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VesselVoyageContractDTO> findOne(Long id);

    /**
     * Delete the "id" vesselVoyageContract.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
