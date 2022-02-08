package ch.orange.service;

import ch.orange.service.dto.PurchaseContractDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ch.orange.domain.PurchaseContract}.
 */
public interface PurchaseContractService {
    /**
     * Save a purchaseContract.
     *
     * @param purchaseContractDTO the entity to save.
     * @return the persisted entity.
     */
    PurchaseContractDTO save(PurchaseContractDTO purchaseContractDTO);

    /**
     * Partially updates a purchaseContract.
     *
     * @param purchaseContractDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PurchaseContractDTO> partialUpdate(PurchaseContractDTO purchaseContractDTO);

    /**
     * Get all the purchaseContracts.
     *
     * @return the list of entities.
     */
    List<PurchaseContractDTO> findAll();

    /**
     * Get the "id" purchaseContract.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseContractDTO> findOne(Long id);

    /**
     * Delete the "id" purchaseContract.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
