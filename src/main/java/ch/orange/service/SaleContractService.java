package ch.orange.service;

import ch.orange.service.dto.SaleContractDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ch.orange.domain.SaleContract}.
 */
public interface SaleContractService {
    /**
     * Save a saleContract.
     *
     * @param saleContractDTO the entity to save.
     * @return the persisted entity.
     */
    SaleContractDTO save(SaleContractDTO saleContractDTO);

    /**
     * Partially updates a saleContract.
     *
     * @param saleContractDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SaleContractDTO> partialUpdate(SaleContractDTO saleContractDTO);

    /**
     * Get all the saleContracts.
     *
     * @return the list of entities.
     */
    List<SaleContractDTO> findAll();

    /**
     * Get the "id" saleContract.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SaleContractDTO> findOne(Long id);

    /**
     * Delete the "id" saleContract.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
