package ch.orange.web.rest;

import ch.orange.repository.SaleContractRepository;
import ch.orange.service.SaleContractService;
import ch.orange.service.dto.SaleContractDTO;
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
 * REST controller for managing {@link ch.orange.domain.SaleContract}.
 */
@RestController
@RequestMapping("/api")
public class SaleContractResource {

    private final Logger log = LoggerFactory.getLogger(SaleContractResource.class);

    private static final String ENTITY_NAME = "saleContract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaleContractService saleContractService;

    private final SaleContractRepository saleContractRepository;

    public SaleContractResource(SaleContractService saleContractService, SaleContractRepository saleContractRepository) {
        this.saleContractService = saleContractService;
        this.saleContractRepository = saleContractRepository;
    }

    /**
     * {@code POST  /sale-contracts} : Create a new saleContract.
     *
     * @param saleContractDTO the saleContractDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saleContractDTO, or with status {@code 400 (Bad Request)} if the saleContract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sale-contracts")
    public ResponseEntity<SaleContractDTO> createSaleContract(@Valid @RequestBody SaleContractDTO saleContractDTO)
        throws URISyntaxException {
        log.debug("REST request to save SaleContract : {}", saleContractDTO);
        if (saleContractDTO.getId() != null) {
            throw new BadRequestAlertException("A new saleContract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaleContractDTO result = saleContractService.save(saleContractDTO);
        return ResponseEntity
            .created(new URI("/api/sale-contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sale-contracts/:id} : Updates an existing saleContract.
     *
     * @param id the id of the saleContractDTO to save.
     * @param saleContractDTO the saleContractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleContractDTO,
     * or with status {@code 400 (Bad Request)} if the saleContractDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saleContractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sale-contracts/{id}")
    public ResponseEntity<SaleContractDTO> updateSaleContract(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SaleContractDTO saleContractDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SaleContract : {}, {}", id, saleContractDTO);
        if (saleContractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleContractDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SaleContractDTO result = saleContractService.save(saleContractDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saleContractDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sale-contracts/:id} : Partial updates given fields of an existing saleContract, field will ignore if it is null
     *
     * @param id the id of the saleContractDTO to save.
     * @param saleContractDTO the saleContractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleContractDTO,
     * or with status {@code 400 (Bad Request)} if the saleContractDTO is not valid,
     * or with status {@code 404 (Not Found)} if the saleContractDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the saleContractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sale-contracts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaleContractDTO> partialUpdateSaleContract(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SaleContractDTO saleContractDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SaleContract partially : {}, {}", id, saleContractDTO);
        if (saleContractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleContractDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaleContractDTO> result = saleContractService.partialUpdate(saleContractDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saleContractDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sale-contracts} : get all the saleContracts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saleContracts in body.
     */
    @GetMapping("/sale-contracts")
    public List<SaleContractDTO> getAllSaleContracts() {
        log.debug("REST request to get all SaleContracts");
        return saleContractService.findAll();
    }

    /**
     * {@code GET  /sale-contracts/:id} : get the "id" saleContract.
     *
     * @param id the id of the saleContractDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saleContractDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sale-contracts/{id}")
    public ResponseEntity<SaleContractDTO> getSaleContract(@PathVariable Long id) {
        log.debug("REST request to get SaleContract : {}", id);
        Optional<SaleContractDTO> saleContractDTO = saleContractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saleContractDTO);
    }

    /**
     * {@code DELETE  /sale-contracts/:id} : delete the "id" saleContract.
     *
     * @param id the id of the saleContractDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sale-contracts/{id}")
    public ResponseEntity<Void> deleteSaleContract(@PathVariable Long id) {
        log.debug("REST request to delete SaleContract : {}", id);
        saleContractService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
