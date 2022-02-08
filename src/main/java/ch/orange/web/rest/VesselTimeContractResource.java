package ch.orange.web.rest;

import ch.orange.repository.VesselTimeContractRepository;
import ch.orange.service.VesselTimeContractService;
import ch.orange.service.dto.VesselTimeContractDTO;
import ch.orange.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ch.orange.domain.VesselTimeContract}.
 */
@RestController
@RequestMapping("/api")
public class VesselTimeContractResource {

    private final Logger log = LoggerFactory.getLogger(VesselTimeContractResource.class);

    private static final String ENTITY_NAME = "vesselTimeContract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VesselTimeContractService vesselTimeContractService;

    private final VesselTimeContractRepository vesselTimeContractRepository;

    public VesselTimeContractResource(
        VesselTimeContractService vesselTimeContractService,
        VesselTimeContractRepository vesselTimeContractRepository
    ) {
        this.vesselTimeContractService = vesselTimeContractService;
        this.vesselTimeContractRepository = vesselTimeContractRepository;
    }

    /**
     * {@code POST  /vessel-time-contracts} : Create a new vesselTimeContract.
     *
     * @param vesselTimeContractDTO the vesselTimeContractDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vesselTimeContractDTO, or with status {@code 400 (Bad Request)} if the vesselTimeContract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vessel-time-contracts")
    public ResponseEntity<VesselTimeContractDTO> createVesselTimeContract(@RequestBody VesselTimeContractDTO vesselTimeContractDTO)
        throws URISyntaxException {
        log.debug("REST request to save VesselTimeContract : {}", vesselTimeContractDTO);
        if (vesselTimeContractDTO.getId() != null) {
            throw new BadRequestAlertException("A new vesselTimeContract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VesselTimeContractDTO result = vesselTimeContractService.save(vesselTimeContractDTO);
        return ResponseEntity
            .created(new URI("/api/vessel-time-contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vessel-time-contracts/:id} : Updates an existing vesselTimeContract.
     *
     * @param id the id of the vesselTimeContractDTO to save.
     * @param vesselTimeContractDTO the vesselTimeContractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vesselTimeContractDTO,
     * or with status {@code 400 (Bad Request)} if the vesselTimeContractDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vesselTimeContractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vessel-time-contracts/{id}")
    public ResponseEntity<VesselTimeContractDTO> updateVesselTimeContract(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VesselTimeContractDTO vesselTimeContractDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VesselTimeContract : {}, {}", id, vesselTimeContractDTO);
        if (vesselTimeContractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vesselTimeContractDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vesselTimeContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VesselTimeContractDTO result = vesselTimeContractService.save(vesselTimeContractDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vesselTimeContractDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vessel-time-contracts/:id} : Partial updates given fields of an existing vesselTimeContract, field will ignore if it is null
     *
     * @param id the id of the vesselTimeContractDTO to save.
     * @param vesselTimeContractDTO the vesselTimeContractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vesselTimeContractDTO,
     * or with status {@code 400 (Bad Request)} if the vesselTimeContractDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vesselTimeContractDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vesselTimeContractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vessel-time-contracts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VesselTimeContractDTO> partialUpdateVesselTimeContract(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VesselTimeContractDTO vesselTimeContractDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VesselTimeContract partially : {}, {}", id, vesselTimeContractDTO);
        if (vesselTimeContractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vesselTimeContractDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vesselTimeContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VesselTimeContractDTO> result = vesselTimeContractService.partialUpdate(vesselTimeContractDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vesselTimeContractDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vessel-time-contracts} : get all the vesselTimeContracts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vesselTimeContracts in body.
     */
    @GetMapping("/vessel-time-contracts")
    public List<VesselTimeContractDTO> getAllVesselTimeContracts() {
        log.debug("REST request to get all VesselTimeContracts");
        return vesselTimeContractService.findAll();
    }

    /**
     * {@code GET  /vessel-time-contracts/:id} : get the "id" vesselTimeContract.
     *
     * @param id the id of the vesselTimeContractDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vesselTimeContractDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vessel-time-contracts/{id}")
    public ResponseEntity<VesselTimeContractDTO> getVesselTimeContract(@PathVariable Long id) {
        log.debug("REST request to get VesselTimeContract : {}", id);
        Optional<VesselTimeContractDTO> vesselTimeContractDTO = vesselTimeContractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vesselTimeContractDTO);
    }

    /**
     * {@code DELETE  /vessel-time-contracts/:id} : delete the "id" vesselTimeContract.
     *
     * @param id the id of the vesselTimeContractDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vessel-time-contracts/{id}")
    public ResponseEntity<Void> deleteVesselTimeContract(@PathVariable Long id) {
        log.debug("REST request to delete VesselTimeContract : {}", id);
        vesselTimeContractService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
