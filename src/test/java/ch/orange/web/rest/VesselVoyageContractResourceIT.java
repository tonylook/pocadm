package ch.orange.web.rest;

import static ch.orange.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.orange.IntegrationTest;
import ch.orange.domain.VesselVoyageContract;
import ch.orange.repository.VesselVoyageContractRepository;
import ch.orange.service.dto.VesselVoyageContractDTO;
import ch.orange.service.mapper.VesselVoyageContractMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VesselVoyageContractResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VesselVoyageContractResourceIT {

    private static final Integer DEFAULT_HOLDS = 1;
    private static final Integer UPDATED_HOLDS = 2;

    private static final Float DEFAULT_HOLD_CAPACITY = 1F;
    private static final Float UPDATED_HOLD_CAPACITY = 2F;

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final Long DEFAULT_PERIOD = 1L;
    private static final Long UPDATED_PERIOD = 2L;

    private static final BigDecimal DEFAULT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/vessel-voyage-contracts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VesselVoyageContractRepository vesselVoyageContractRepository;

    @Autowired
    private VesselVoyageContractMapper vesselVoyageContractMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVesselVoyageContractMockMvc;

    private VesselVoyageContract vesselVoyageContract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VesselVoyageContract createEntity(EntityManager em) {
        VesselVoyageContract vesselVoyageContract = new VesselVoyageContract()
            .holds(DEFAULT_HOLDS)
            .holdCapacity(DEFAULT_HOLD_CAPACITY)
            .source(DEFAULT_SOURCE)
            .destination(DEFAULT_DESTINATION)
            .period(DEFAULT_PERIOD)
            .cost(DEFAULT_COST);
        return vesselVoyageContract;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VesselVoyageContract createUpdatedEntity(EntityManager em) {
        VesselVoyageContract vesselVoyageContract = new VesselVoyageContract()
            .holds(UPDATED_HOLDS)
            .holdCapacity(UPDATED_HOLD_CAPACITY)
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .period(UPDATED_PERIOD)
            .cost(UPDATED_COST);
        return vesselVoyageContract;
    }

    @BeforeEach
    public void initTest() {
        vesselVoyageContract = createEntity(em);
    }

    @Test
    @Transactional
    void createVesselVoyageContract() throws Exception {
        int databaseSizeBeforeCreate = vesselVoyageContractRepository.findAll().size();
        // Create the VesselVoyageContract
        VesselVoyageContractDTO vesselVoyageContractDTO = vesselVoyageContractMapper.toDto(vesselVoyageContract);
        restVesselVoyageContractMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vesselVoyageContractDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VesselVoyageContract in the database
        List<VesselVoyageContract> vesselVoyageContractList = vesselVoyageContractRepository.findAll();
        assertThat(vesselVoyageContractList).hasSize(databaseSizeBeforeCreate + 1);
        VesselVoyageContract testVesselVoyageContract = vesselVoyageContractList.get(vesselVoyageContractList.size() - 1);
        assertThat(testVesselVoyageContract.getHolds()).isEqualTo(DEFAULT_HOLDS);
        assertThat(testVesselVoyageContract.getHoldCapacity()).isEqualTo(DEFAULT_HOLD_CAPACITY);
        assertThat(testVesselVoyageContract.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testVesselVoyageContract.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testVesselVoyageContract.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testVesselVoyageContract.getCost()).isEqualByComparingTo(DEFAULT_COST);
    }

    @Test
    @Transactional
    void createVesselVoyageContractWithExistingId() throws Exception {
        // Create the VesselVoyageContract with an existing ID
        vesselVoyageContract.setId(1L);
        VesselVoyageContractDTO vesselVoyageContractDTO = vesselVoyageContractMapper.toDto(vesselVoyageContract);

        int databaseSizeBeforeCreate = vesselVoyageContractRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVesselVoyageContractMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vesselVoyageContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VesselVoyageContract in the database
        List<VesselVoyageContract> vesselVoyageContractList = vesselVoyageContractRepository.findAll();
        assertThat(vesselVoyageContractList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVesselVoyageContracts() throws Exception {
        // Initialize the database
        vesselVoyageContractRepository.saveAndFlush(vesselVoyageContract);

        // Get all the vesselVoyageContractList
        restVesselVoyageContractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vesselVoyageContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].holds").value(hasItem(DEFAULT_HOLDS)))
            .andExpect(jsonPath("$.[*].holdCapacity").value(hasItem(DEFAULT_HOLD_CAPACITY.doubleValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION)))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(sameNumber(DEFAULT_COST))));
    }

    @Test
    @Transactional
    void getVesselVoyageContract() throws Exception {
        // Initialize the database
        vesselVoyageContractRepository.saveAndFlush(vesselVoyageContract);

        // Get the vesselVoyageContract
        restVesselVoyageContractMockMvc
            .perform(get(ENTITY_API_URL_ID, vesselVoyageContract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vesselVoyageContract.getId().intValue()))
            .andExpect(jsonPath("$.holds").value(DEFAULT_HOLDS))
            .andExpect(jsonPath("$.holdCapacity").value(DEFAULT_HOLD_CAPACITY.doubleValue()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD.intValue()))
            .andExpect(jsonPath("$.cost").value(sameNumber(DEFAULT_COST)));
    }

    @Test
    @Transactional
    void getNonExistingVesselVoyageContract() throws Exception {
        // Get the vesselVoyageContract
        restVesselVoyageContractMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVesselVoyageContract() throws Exception {
        // Initialize the database
        vesselVoyageContractRepository.saveAndFlush(vesselVoyageContract);

        int databaseSizeBeforeUpdate = vesselVoyageContractRepository.findAll().size();

        // Update the vesselVoyageContract
        VesselVoyageContract updatedVesselVoyageContract = vesselVoyageContractRepository.findById(vesselVoyageContract.getId()).get();
        // Disconnect from session so that the updates on updatedVesselVoyageContract are not directly saved in db
        em.detach(updatedVesselVoyageContract);
        updatedVesselVoyageContract
            .holds(UPDATED_HOLDS)
            .holdCapacity(UPDATED_HOLD_CAPACITY)
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .period(UPDATED_PERIOD)
            .cost(UPDATED_COST);
        VesselVoyageContractDTO vesselVoyageContractDTO = vesselVoyageContractMapper.toDto(updatedVesselVoyageContract);

        restVesselVoyageContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vesselVoyageContractDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vesselVoyageContractDTO))
            )
            .andExpect(status().isOk());

        // Validate the VesselVoyageContract in the database
        List<VesselVoyageContract> vesselVoyageContractList = vesselVoyageContractRepository.findAll();
        assertThat(vesselVoyageContractList).hasSize(databaseSizeBeforeUpdate);
        VesselVoyageContract testVesselVoyageContract = vesselVoyageContractList.get(vesselVoyageContractList.size() - 1);
        assertThat(testVesselVoyageContract.getHolds()).isEqualTo(UPDATED_HOLDS);
        assertThat(testVesselVoyageContract.getHoldCapacity()).isEqualTo(UPDATED_HOLD_CAPACITY);
        assertThat(testVesselVoyageContract.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testVesselVoyageContract.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testVesselVoyageContract.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testVesselVoyageContract.getCost()).isEqualByComparingTo(UPDATED_COST);
    }

    @Test
    @Transactional
    void putNonExistingVesselVoyageContract() throws Exception {
        int databaseSizeBeforeUpdate = vesselVoyageContractRepository.findAll().size();
        vesselVoyageContract.setId(count.incrementAndGet());

        // Create the VesselVoyageContract
        VesselVoyageContractDTO vesselVoyageContractDTO = vesselVoyageContractMapper.toDto(vesselVoyageContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVesselVoyageContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vesselVoyageContractDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vesselVoyageContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VesselVoyageContract in the database
        List<VesselVoyageContract> vesselVoyageContractList = vesselVoyageContractRepository.findAll();
        assertThat(vesselVoyageContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVesselVoyageContract() throws Exception {
        int databaseSizeBeforeUpdate = vesselVoyageContractRepository.findAll().size();
        vesselVoyageContract.setId(count.incrementAndGet());

        // Create the VesselVoyageContract
        VesselVoyageContractDTO vesselVoyageContractDTO = vesselVoyageContractMapper.toDto(vesselVoyageContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVesselVoyageContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vesselVoyageContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VesselVoyageContract in the database
        List<VesselVoyageContract> vesselVoyageContractList = vesselVoyageContractRepository.findAll();
        assertThat(vesselVoyageContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVesselVoyageContract() throws Exception {
        int databaseSizeBeforeUpdate = vesselVoyageContractRepository.findAll().size();
        vesselVoyageContract.setId(count.incrementAndGet());

        // Create the VesselVoyageContract
        VesselVoyageContractDTO vesselVoyageContractDTO = vesselVoyageContractMapper.toDto(vesselVoyageContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVesselVoyageContractMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vesselVoyageContractDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VesselVoyageContract in the database
        List<VesselVoyageContract> vesselVoyageContractList = vesselVoyageContractRepository.findAll();
        assertThat(vesselVoyageContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVesselVoyageContractWithPatch() throws Exception {
        // Initialize the database
        vesselVoyageContractRepository.saveAndFlush(vesselVoyageContract);

        int databaseSizeBeforeUpdate = vesselVoyageContractRepository.findAll().size();

        // Update the vesselVoyageContract using partial update
        VesselVoyageContract partialUpdatedVesselVoyageContract = new VesselVoyageContract();
        partialUpdatedVesselVoyageContract.setId(vesselVoyageContract.getId());

        partialUpdatedVesselVoyageContract.destination(UPDATED_DESTINATION).period(UPDATED_PERIOD);

        restVesselVoyageContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVesselVoyageContract.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVesselVoyageContract))
            )
            .andExpect(status().isOk());

        // Validate the VesselVoyageContract in the database
        List<VesselVoyageContract> vesselVoyageContractList = vesselVoyageContractRepository.findAll();
        assertThat(vesselVoyageContractList).hasSize(databaseSizeBeforeUpdate);
        VesselVoyageContract testVesselVoyageContract = vesselVoyageContractList.get(vesselVoyageContractList.size() - 1);
        assertThat(testVesselVoyageContract.getHolds()).isEqualTo(DEFAULT_HOLDS);
        assertThat(testVesselVoyageContract.getHoldCapacity()).isEqualTo(DEFAULT_HOLD_CAPACITY);
        assertThat(testVesselVoyageContract.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testVesselVoyageContract.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testVesselVoyageContract.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testVesselVoyageContract.getCost()).isEqualByComparingTo(DEFAULT_COST);
    }

    @Test
    @Transactional
    void fullUpdateVesselVoyageContractWithPatch() throws Exception {
        // Initialize the database
        vesselVoyageContractRepository.saveAndFlush(vesselVoyageContract);

        int databaseSizeBeforeUpdate = vesselVoyageContractRepository.findAll().size();

        // Update the vesselVoyageContract using partial update
        VesselVoyageContract partialUpdatedVesselVoyageContract = new VesselVoyageContract();
        partialUpdatedVesselVoyageContract.setId(vesselVoyageContract.getId());

        partialUpdatedVesselVoyageContract
            .holds(UPDATED_HOLDS)
            .holdCapacity(UPDATED_HOLD_CAPACITY)
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .period(UPDATED_PERIOD)
            .cost(UPDATED_COST);

        restVesselVoyageContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVesselVoyageContract.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVesselVoyageContract))
            )
            .andExpect(status().isOk());

        // Validate the VesselVoyageContract in the database
        List<VesselVoyageContract> vesselVoyageContractList = vesselVoyageContractRepository.findAll();
        assertThat(vesselVoyageContractList).hasSize(databaseSizeBeforeUpdate);
        VesselVoyageContract testVesselVoyageContract = vesselVoyageContractList.get(vesselVoyageContractList.size() - 1);
        assertThat(testVesselVoyageContract.getHolds()).isEqualTo(UPDATED_HOLDS);
        assertThat(testVesselVoyageContract.getHoldCapacity()).isEqualTo(UPDATED_HOLD_CAPACITY);
        assertThat(testVesselVoyageContract.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testVesselVoyageContract.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testVesselVoyageContract.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testVesselVoyageContract.getCost()).isEqualByComparingTo(UPDATED_COST);
    }

    @Test
    @Transactional
    void patchNonExistingVesselVoyageContract() throws Exception {
        int databaseSizeBeforeUpdate = vesselVoyageContractRepository.findAll().size();
        vesselVoyageContract.setId(count.incrementAndGet());

        // Create the VesselVoyageContract
        VesselVoyageContractDTO vesselVoyageContractDTO = vesselVoyageContractMapper.toDto(vesselVoyageContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVesselVoyageContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vesselVoyageContractDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vesselVoyageContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VesselVoyageContract in the database
        List<VesselVoyageContract> vesselVoyageContractList = vesselVoyageContractRepository.findAll();
        assertThat(vesselVoyageContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVesselVoyageContract() throws Exception {
        int databaseSizeBeforeUpdate = vesselVoyageContractRepository.findAll().size();
        vesselVoyageContract.setId(count.incrementAndGet());

        // Create the VesselVoyageContract
        VesselVoyageContractDTO vesselVoyageContractDTO = vesselVoyageContractMapper.toDto(vesselVoyageContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVesselVoyageContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vesselVoyageContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VesselVoyageContract in the database
        List<VesselVoyageContract> vesselVoyageContractList = vesselVoyageContractRepository.findAll();
        assertThat(vesselVoyageContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVesselVoyageContract() throws Exception {
        int databaseSizeBeforeUpdate = vesselVoyageContractRepository.findAll().size();
        vesselVoyageContract.setId(count.incrementAndGet());

        // Create the VesselVoyageContract
        VesselVoyageContractDTO vesselVoyageContractDTO = vesselVoyageContractMapper.toDto(vesselVoyageContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVesselVoyageContractMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vesselVoyageContractDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VesselVoyageContract in the database
        List<VesselVoyageContract> vesselVoyageContractList = vesselVoyageContractRepository.findAll();
        assertThat(vesselVoyageContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVesselVoyageContract() throws Exception {
        // Initialize the database
        vesselVoyageContractRepository.saveAndFlush(vesselVoyageContract);

        int databaseSizeBeforeDelete = vesselVoyageContractRepository.findAll().size();

        // Delete the vesselVoyageContract
        restVesselVoyageContractMockMvc
            .perform(delete(ENTITY_API_URL_ID, vesselVoyageContract.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VesselVoyageContract> vesselVoyageContractList = vesselVoyageContractRepository.findAll();
        assertThat(vesselVoyageContractList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
