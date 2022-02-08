package ch.orange.web.rest;

import ch.orange.repository.PurchaseContractRepository;
import ch.orange.service.PurchaseContractService;
import ch.orange.service.dto.PurchaseContractDTO;
import ch.orange.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ch.orange.domain.PurchaseContract}.
 */
@RestController
@RequestMapping("/api")
public class PurchaseContractResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseContractResource.class);

    private static final String ENTITY_NAME = "purchaseContract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchaseContractService purchaseContractService;

    private final PurchaseContractRepository purchaseContractRepository;

    public PurchaseContractResource(
        PurchaseContractService purchaseContractService,
        PurchaseContractRepository purchaseContractRepository
    ) {
        this.purchaseContractService = purchaseContractService;
        this.purchaseContractRepository = purchaseContractRepository;
    }

    /**
     * {@code POST  /purchase-contracts} : Create a new purchaseContract.
     *
     * @param purchaseContractDTO the purchaseContractDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchaseContractDTO, or with status {@code 400 (Bad Request)} if the purchaseContract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchase-contracts")
    public ResponseEntity<PurchaseContractDTO> createPurchaseContract(@Valid @RequestBody PurchaseContractDTO purchaseContractDTO)
        throws URISyntaxException {
        log.debug("REST request to save PurchaseContract : {}", purchaseContractDTO);
        if (purchaseContractDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseContract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseContractDTO result = purchaseContractService.save(purchaseContractDTO);
        return ResponseEntity
            .created(new URI("/api/purchase-contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchase-contracts/:id} : Updates an existing purchaseContract.
     *
     * @param id the id of the purchaseContractDTO to save.
     * @param purchaseContractDTO the purchaseContractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchaseContractDTO,
     * or with status {@code 400 (Bad Request)} if the purchaseContractDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchaseContractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchase-contracts/{id}")
    public ResponseEntity<PurchaseContractDTO> updatePurchaseContract(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PurchaseContractDTO purchaseContractDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PurchaseContract : {}, {}", id, purchaseContractDTO);
        if (purchaseContractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, purchaseContractDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!purchaseContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PurchaseContractDTO result = purchaseContractService.save(purchaseContractDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchaseContractDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /purchase-contracts/:id} : Partial updates given fields of an existing purchaseContract, field will ignore if it is null
     *
     * @param id the id of the purchaseContractDTO to save.
     * @param purchaseContractDTO the purchaseContractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchaseContractDTO,
     * or with status {@code 400 (Bad Request)} if the purchaseContractDTO is not valid,
     * or with status {@code 404 (Not Found)} if the purchaseContractDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the purchaseContractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/purchase-contracts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PurchaseContractDTO> partialUpdatePurchaseContract(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PurchaseContractDTO purchaseContractDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PurchaseContract partially : {}, {}", id, purchaseContractDTO);
        if (purchaseContractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, purchaseContractDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!purchaseContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PurchaseContractDTO> result = purchaseContractService.partialUpdate(purchaseContractDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchaseContractDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /purchase-contracts} : get all the purchaseContracts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchaseContracts in body.
     */
    @GetMapping("/purchase-contracts")
    public List<PurchaseContractDTO> getAllPurchaseContracts() {
        log.debug("REST request to get all PurchaseContracts");
        return purchaseContractService.findAll();
    }

    /**
     * {@code GET  /purchase-contracts/:id} : get the "id" purchaseContract.
     *
     * @param id the id of the purchaseContractDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchaseContractDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-contracts/{id}")
    public ResponseEntity<PurchaseContractDTO> getPurchaseContract(@PathVariable Long id) {
        log.debug("REST request to get PurchaseContract : {}", id);
        Optional<PurchaseContractDTO> purchaseContractDTO = purchaseContractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseContractDTO);
    }

    /**
     * {@code DELETE  /purchase-contracts/:id} : delete the "id" purchaseContract.
     *
     * @param id the id of the purchaseContractDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchase-contracts/{id}")
    public ResponseEntity<Void> deletePurchaseContract(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseContract : {}", id);
        purchaseContractService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
