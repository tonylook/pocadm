package ch.orange.service;

import ch.orange.service.dto.VesselTimeContractDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ch.orange.domain.VesselTimeContract}.
 */
public interface VesselTimeContractService {
    /**
     * Save a vesselTimeContract.
     *
     * @param vesselTimeContractDTO the entity to save.
     * @return the persisted entity.
     */
    VesselTimeContractDTO save(VesselTimeContractDTO vesselTimeContractDTO);

    /**
     * Partially updates a vesselTimeContract.
     *
     * @param vesselTimeContractDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VesselTimeContractDTO> partialUpdate(VesselTimeContractDTO vesselTimeContractDTO);

    /**
     * Get all the vesselTimeContracts.
     *
     * @return the list of entities.
     */
    List<VesselTimeContractDTO> findAll();

    /**
     * Get the "id" vesselTimeContract.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VesselTimeContractDTO> findOne(Long id);

    /**
     * Delete the "id" vesselTimeContract.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
