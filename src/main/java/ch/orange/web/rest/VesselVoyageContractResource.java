package ch.orange.web.rest;

import ch.orange.repository.VesselVoyageContractRepository;
import ch.orange.service.VesselVoyageContractService;
import ch.orange.service.dto.VesselVoyageContractDTO;
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
 * REST controller for managing {@link ch.orange.domain.VesselVoyageContract}.
 */
@RestController
@RequestMapping("/api")
public class VesselVoyageContractResource {

    private final Logger log = LoggerFactory.getLogger(VesselVoyageContractResource.class);

    private static final String ENTITY_NAME = "vesselVoyageContract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VesselVoyageContractService vesselVoyageContractService;

    private final VesselVoyageContractRepository vesselVoyageContractRepository;

    public VesselVoyageContractResource(
        VesselVoyageContractService vesselVoyageContractService,
        VesselVoyageContractRepository vesselVoyageContractRepository
    ) {
        this.vesselVoyageContractService = vesselVoyageContractService;
        this.vesselVoyageContractRepository = vesselVoyageContractRepository;
    }

    /**
     * {@code POST  /vessel-voyage-contracts} : Create a new vesselVoyageContract.
     *
     * @param vesselVoyageContractDTO the vesselVoyageContractDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vesselVoyageContractDTO, or with status {@code 400 (Bad Request)} if the vesselVoyageContract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vessel-voyage-contracts")
    public ResponseEntity<VesselVoyageContractDTO> createVesselVoyageContract(@RequestBody VesselVoyageContractDTO vesselVoyageContractDTO)
        throws URISyntaxException {
        log.debug("REST request to save VesselVoyageContract : {}", vesselVoyageContractDTO);
        if (vesselVoyageContractDTO.getId() != null) {
            throw new BadRequestAlertException("A new vesselVoyageContract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VesselVoyageContractDTO result = vesselVoyageContractService.save(vesselVoyageContractDTO);
        return ResponseEntity
            .created(new URI("/api/vessel-voyage-contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vessel-voyage-contracts/:id} : Updates an existing vesselVoyageContract.
     *
     * @param id the id of the vesselVoyageContractDTO to save.
     * @param vesselVoyageContractDTO the vesselVoyageContractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vesselVoyageContractDTO,
     * or with status {@code 400 (Bad Request)} if the vesselVoyageContractDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vesselVoyageContractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vessel-voyage-contracts/{id}")
    public ResponseEntity<VesselVoyageContractDTO> updateVesselVoyageContract(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VesselVoyageContractDTO vesselVoyageContractDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VesselVoyageContract : {}, {}", id, vesselVoyageContractDTO);
        if (vesselVoyageContractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vesselVoyageContractDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vesselVoyageContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VesselVoyageContractDTO result = vesselVoyageContractService.save(vesselVoyageContractDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vesselVoyageContractDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vessel-voyage-contracts/:id} : Partial updates given fields of an existing vesselVoyageContract, field will ignore if it is null
     *
     * @param id the id of the vesselVoyageContractDTO to save.
     * @param vesselVoyageContractDTO the vesselVoyageContractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vesselVoyageContractDTO,
     * or with status {@code 400 (Bad Request)} if the vesselVoyageContractDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vesselVoyageContractDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vesselVoyageContractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vessel-voyage-contracts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VesselVoyageContractDTO> partialUpdateVesselVoyageContract(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VesselVoyageContractDTO vesselVoyageContractDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VesselVoyageContract partially : {}, {}", id, vesselVoyageContractDTO);
        if (vesselVoyageContractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vesselVoyageContractDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vesselVoyageContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VesselVoyageContractDTO> result = vesselVoyageContractService.partialUpdate(vesselVoyageContractDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vesselVoyageContractDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vessel-voyage-contracts} : get all the vesselVoyageContracts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vesselVoyageContracts in body.
     */
    @GetMapping("/vessel-voyage-contracts")
    public List<VesselVoyageContractDTO> getAllVesselVoyageContracts() {
        log.debug("REST request to get all VesselVoyageContracts");
        return vesselVoyageContractService.findAll();
    }

    /**
     * {@code GET  /vessel-voyage-contracts/:id} : get the "id" vesselVoyageContract.
     *
     * @param id the id of the vesselVoyageContractDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vesselVoyageContractDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vessel-voyage-contracts/{id}")
    public ResponseEntity<VesselVoyageContractDTO> getVesselVoyageContract(@PathVariable Long id) {
        log.debug("REST request to get VesselVoyageContract : {}", id);
        Optional<VesselVoyageContractDTO> vesselVoyageContractDTO = vesselVoyageContractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vesselVoyageContractDTO);
    }

    /**
     * {@code DELETE  /vessel-voyage-contracts/:id} : delete the "id" vesselVoyageContract.
     *
     * @param id the id of the vesselVoyageContractDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vessel-voyage-contracts/{id}")
    public ResponseEntity<Void> deleteVesselVoyageContract(@PathVariable Long id) {
        log.debug("REST request to delete VesselVoyageContract : {}", id);
        vesselVoyageContractService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
