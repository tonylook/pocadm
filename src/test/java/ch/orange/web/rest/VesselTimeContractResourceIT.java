package ch.orange.web.rest;

import static ch.orange.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.orange.IntegrationTest;
import ch.orange.domain.VesselTimeContract;
import ch.orange.repository.VesselTimeContractRepository;
import ch.orange.service.dto.VesselTimeContractDTO;
import ch.orange.service.mapper.VesselTimeContractMapper;
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
 * Integration tests for the {@link VesselTimeContractResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VesselTimeContractResourceIT {

    private static final Integer DEFAULT_HOLDS = 1;
    private static final Integer UPDATED_HOLDS = 2;

    private static final Float DEFAULT_HOLD_CAPACITY = 1F;
    private static final Float UPDATED_HOLD_CAPACITY = 2F;

    private static final Long DEFAULT_PERIOD = 1L;
    private static final Long UPDATED_PERIOD = 2L;

    private static final BigDecimal DEFAULT_COST_PER_DAY = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST_PER_DAY = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/vessel-time-contracts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VesselTimeContractRepository vesselTimeContractRepository;

    @Autowired
    private VesselTimeContractMapper vesselTimeContractMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVesselTimeContractMockMvc;

    private VesselTimeContract vesselTimeContract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VesselTimeContract createEntity(EntityManager em) {
        VesselTimeContract vesselTimeContract = new VesselTimeContract()
            .holds(DEFAULT_HOLDS)
            .holdCapacity(DEFAULT_HOLD_CAPACITY)
            .period(DEFAULT_PERIOD)
            .costPerDay(DEFAULT_COST_PER_DAY);
        return vesselTimeContract;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VesselTimeContract createUpdatedEntity(EntityManager em) {
        VesselTimeContract vesselTimeContract = new VesselTimeContract()
            .holds(UPDATED_HOLDS)
            .holdCapacity(UPDATED_HOLD_CAPACITY)
            .period(UPDATED_PERIOD)
            .costPerDay(UPDATED_COST_PER_DAY);
        return vesselTimeContract;
    }

    @BeforeEach
    public void initTest() {
        vesselTimeContract = createEntity(em);
    }

    @Test
    @Transactional
    void createVesselTimeContract() throws Exception {
        int databaseSizeBeforeCreate = vesselTimeContractRepository.findAll().size();
        // Create the VesselTimeContract
        VesselTimeContractDTO vesselTimeContractDTO = vesselTimeContractMapper.toDto(vesselTimeContract);
        restVesselTimeContractMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vesselTimeContractDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VesselTimeContract in the database
        List<VesselTimeContract> vesselTimeContractList = vesselTimeContractRepository.findAll();
        assertThat(vesselTimeContractList).hasSize(databaseSizeBeforeCreate + 1);
        VesselTimeContract testVesselTimeContract = vesselTimeContractList.get(vesselTimeContractList.size() - 1);
        assertThat(testVesselTimeContract.getHolds()).isEqualTo(DEFAULT_HOLDS);
        assertThat(testVesselTimeContract.getHoldCapacity()).isEqualTo(DEFAULT_HOLD_CAPACITY);
        assertThat(testVesselTimeContract.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testVesselTimeContract.getCostPerDay()).isEqualByComparingTo(DEFAULT_COST_PER_DAY);
    }

    @Test
    @Transactional
    void createVesselTimeContractWithExistingId() throws Exception {
        // Create the VesselTimeContract with an existing ID
        vesselTimeContract.setId(1L);
        VesselTimeContractDTO vesselTimeContractDTO = vesselTimeContractMapper.toDto(vesselTimeContract);

        int databaseSizeBeforeCreate = vesselTimeContractRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVesselTimeContractMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vesselTimeContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VesselTimeContract in the database
        List<VesselTimeContract> vesselTimeContractList = vesselTimeContractRepository.findAll();
        assertThat(vesselTimeContractList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVesselTimeContracts() throws Exception {
        // Initialize the database
        vesselTimeContractRepository.saveAndFlush(vesselTimeContract);

        // Get all the vesselTimeContractList
        restVesselTimeContractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vesselTimeContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].holds").value(hasItem(DEFAULT_HOLDS)))
            .andExpect(jsonPath("$.[*].holdCapacity").value(hasItem(DEFAULT_HOLD_CAPACITY.doubleValue())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].costPerDay").value(hasItem(sameNumber(DEFAULT_COST_PER_DAY))));
    }

    @Test
    @Transactional
    void getVesselTimeContract() throws Exception {
        // Initialize the database
        vesselTimeContractRepository.saveAndFlush(vesselTimeContract);

        // Get the vesselTimeContract
        restVesselTimeContractMockMvc
            .perform(get(ENTITY_API_URL_ID, vesselTimeContract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vesselTimeContract.getId().intValue()))
            .andExpect(jsonPath("$.holds").value(DEFAULT_HOLDS))
            .andExpect(jsonPath("$.holdCapacity").value(DEFAULT_HOLD_CAPACITY.doubleValue()))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD.intValue()))
            .andExpect(jsonPath("$.costPerDay").value(sameNumber(DEFAULT_COST_PER_DAY)));
    }

    @Test
    @Transactional
    void getNonExistingVesselTimeContract() throws Exception {
        // Get the vesselTimeContract
        restVesselTimeContractMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVesselTimeContract() throws Exception {
        // Initialize the database
        vesselTimeContractRepository.saveAndFlush(vesselTimeContract);

        int databaseSizeBeforeUpdate = vesselTimeContractRepository.findAll().size();

        // Update the vesselTimeContract
        VesselTimeContract updatedVesselTimeContract = vesselTimeContractRepository.findById(vesselTimeContract.getId()).get();
        // Disconnect from session so that the updates on updatedVesselTimeContract are not directly saved in db
        em.detach(updatedVesselTimeContract);
        updatedVesselTimeContract
            .holds(UPDATED_HOLDS)
            .holdCapacity(UPDATED_HOLD_CAPACITY)
            .period(UPDATED_PERIOD)
            .costPerDay(UPDATED_COST_PER_DAY);
        VesselTimeContractDTO vesselTimeContractDTO = vesselTimeContractMapper.toDto(updatedVesselTimeContract);

        restVesselTimeContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vesselTimeContractDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vesselTimeContractDTO))
            )
            .andExpect(status().isOk());

        // Validate the VesselTimeContract in the database
        List<VesselTimeContract> vesselTimeContractList = vesselTimeContractRepository.findAll();
        assertThat(vesselTimeContractList).hasSize(databaseSizeBeforeUpdate);
        VesselTimeContract testVesselTimeContract = vesselTimeContractList.get(vesselTimeContractList.size() - 1);
        assertThat(testVesselTimeContract.getHolds()).isEqualTo(UPDATED_HOLDS);
        assertThat(testVesselTimeContract.getHoldCapacity()).isEqualTo(UPDATED_HOLD_CAPACITY);
        assertThat(testVesselTimeContract.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testVesselTimeContract.getCostPerDay()).isEqualByComparingTo(UPDATED_COST_PER_DAY);
    }

    @Test
    @Transactional
    void putNonExistingVesselTimeContract() throws Exception {
        int databaseSizeBeforeUpdate = vesselTimeContractRepository.findAll().size();
        vesselTimeContract.setId(count.incrementAndGet());

        // Create the VesselTimeContract
        VesselTimeContractDTO vesselTimeContractDTO = vesselTimeContractMapper.toDto(vesselTimeContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVesselTimeContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vesselTimeContractDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vesselTimeContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VesselTimeContract in the database
        List<VesselTimeContract> vesselTimeContractList = vesselTimeContractRepository.findAll();
        assertThat(vesselTimeContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVesselTimeContract() throws Exception {
        int databaseSizeBeforeUpdate = vesselTimeContractRepository.findAll().size();
        vesselTimeContract.setId(count.incrementAndGet());

        // Create the VesselTimeContract
        VesselTimeContractDTO vesselTimeContractDTO = vesselTimeContractMapper.toDto(vesselTimeContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVesselTimeContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vesselTimeContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VesselTimeContract in the database
        List<VesselTimeContract> vesselTimeContractList = vesselTimeContractRepository.findAll();
        assertThat(vesselTimeContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVesselTimeContract() throws Exception {
        int databaseSizeBeforeUpdate = vesselTimeContractRepository.findAll().size();
        vesselTimeContract.setId(count.incrementAndGet());

        // Create the VesselTimeContract
        VesselTimeContractDTO vesselTimeContractDTO = vesselTimeContractMapper.toDto(vesselTimeContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVesselTimeContractMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vesselTimeContractDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VesselTimeContract in the database
        List<VesselTimeContract> vesselTimeContractList = vesselTimeContractRepository.findAll();
        assertThat(vesselTimeContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVesselTimeContractWithPatch() throws Exception {
        // Initialize the database
        vesselTimeContractRepository.saveAndFlush(vesselTimeContract);

        int databaseSizeBeforeUpdate = vesselTimeContractRepository.findAll().size();

        // Update the vesselTimeContract using partial update
        VesselTimeContract partialUpdatedVesselTimeContract = new VesselTimeContract();
        partialUpdatedVesselTimeContract.setId(vesselTimeContract.getId());

        partialUpdatedVesselTimeContract.holdCapacity(UPDATED_HOLD_CAPACITY).period(UPDATED_PERIOD);

        restVesselTimeContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVesselTimeContract.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVesselTimeContract))
            )
            .andExpect(status().isOk());

        // Validate the VesselTimeContract in the database
        List<VesselTimeContract> vesselTimeContractList = vesselTimeContractRepository.findAll();
        assertThat(vesselTimeContractList).hasSize(databaseSizeBeforeUpdate);
        VesselTimeContract testVesselTimeContract = vesselTimeContractList.get(vesselTimeContractList.size() - 1);
        assertThat(testVesselTimeContract.getHolds()).isEqualTo(DEFAULT_HOLDS);
        assertThat(testVesselTimeContract.getHoldCapacity()).isEqualTo(UPDATED_HOLD_CAPACITY);
        assertThat(testVesselTimeContract.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testVesselTimeContract.getCostPerDay()).isEqualByComparingTo(DEFAULT_COST_PER_DAY);
    }

    @Test
    @Transactional
    void fullUpdateVesselTimeContractWithPatch() throws Exception {
        // Initialize the database
        vesselTimeContractRepository.saveAndFlush(vesselTimeContract);

        int databaseSizeBeforeUpdate = vesselTimeContractRepository.findAll().size();

        // Update the vesselTimeContract using partial update
        VesselTimeContract partialUpdatedVesselTimeContract = new VesselTimeContract();
        partialUpdatedVesselTimeContract.setId(vesselTimeContract.getId());

        partialUpdatedVesselTimeContract
            .holds(UPDATED_HOLDS)
            .holdCapacity(UPDATED_HOLD_CAPACITY)
            .period(UPDATED_PERIOD)
            .costPerDay(UPDATED_COST_PER_DAY);

        restVesselTimeContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVesselTimeContract.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVesselTimeContract))
            )
            .andExpect(status().isOk());

        // Validate the VesselTimeContract in the database
        List<VesselTimeContract> vesselTimeContractList = vesselTimeContractRepository.findAll();
        assertThat(vesselTimeContractList).hasSize(databaseSizeBeforeUpdate);
        VesselTimeContract testVesselTimeContract = vesselTimeContractList.get(vesselTimeContractList.size() - 1);
        assertThat(testVesselTimeContract.getHolds()).isEqualTo(UPDATED_HOLDS);
        assertThat(testVesselTimeContract.getHoldCapacity()).isEqualTo(UPDATED_HOLD_CAPACITY);
        assertThat(testVesselTimeContract.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testVesselTimeContract.getCostPerDay()).isEqualByComparingTo(UPDATED_COST_PER_DAY);
    }

    @Test
    @Transactional
    void patchNonExistingVesselTimeContract() throws Exception {
        int databaseSizeBeforeUpdate = vesselTimeContractRepository.findAll().size();
        vesselTimeContract.setId(count.incrementAndGet());

        // Create the VesselTimeContract
        VesselTimeContractDTO vesselTimeContractDTO = vesselTimeContractMapper.toDto(vesselTimeContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVesselTimeContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vesselTimeContractDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vesselTimeContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VesselTimeContract in the database
        List<VesselTimeContract> vesselTimeContractList = vesselTimeContractRepository.findAll();
        assertThat(vesselTimeContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVesselTimeContract() throws Exception {
        int databaseSizeBeforeUpdate = vesselTimeContractRepository.findAll().size();
        vesselTimeContract.setId(count.incrementAndGet());

        // Create the VesselTimeContract
        VesselTimeContractDTO vesselTimeContractDTO = vesselTimeContractMapper.toDto(vesselTimeContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVesselTimeContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vesselTimeContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VesselTimeContract in the database
        List<VesselTimeContract> vesselTimeContractList = vesselTimeContractRepository.findAll();
        assertThat(vesselTimeContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVesselTimeContract() throws Exception {
        int databaseSizeBeforeUpdate = vesselTimeContractRepository.findAll().size();
        vesselTimeContract.setId(count.incrementAndGet());

        // Create the VesselTimeContract
        VesselTimeContractDTO vesselTimeContractDTO = vesselTimeContractMapper.toDto(vesselTimeContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVesselTimeContractMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vesselTimeContractDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VesselTimeContract in the database
        List<VesselTimeContract> vesselTimeContractList = vesselTimeContractRepository.findAll();
        assertThat(vesselTimeContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVesselTimeContract() throws Exception {
        // Initialize the database
        vesselTimeContractRepository.saveAndFlush(vesselTimeContract);

        int databaseSizeBeforeDelete = vesselTimeContractRepository.findAll().size();

        // Delete the vesselTimeContract
        restVesselTimeContractMockMvc
            .perform(delete(ENTITY_API_URL_ID, vesselTimeContract.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VesselTimeContract> vesselTimeContractList = vesselTimeContractRepository.findAll();
        assertThat(vesselTimeContractList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
