package ch.orange.web.rest;

import static ch.orange.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.orange.IntegrationTest;
import ch.orange.domain.Port;
import ch.orange.domain.PurchaseContract;
import ch.orange.domain.enumeration.Quality;
import ch.orange.repository.PurchaseContractRepository;
import ch.orange.service.dto.PurchaseContractDTO;
import ch.orange.service.mapper.PurchaseContractMapper;
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
 * Integration tests for the {@link PurchaseContractResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PurchaseContractResourceIT {

    private static final Long DEFAULT_PURCHASING_WINDOW = 1L;
    private static final Long UPDATED_PURCHASING_WINDOW = 2L;

    private static final Quality DEFAULT_SOYMEAL_QUALITY = Quality.BAD;
    private static final Quality UPDATED_SOYMEAL_QUALITY = Quality.GOOD;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Float DEFAULT_VOLUME = 1F;
    private static final Float UPDATED_VOLUME = 2F;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/purchase-contracts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PurchaseContractRepository purchaseContractRepository;

    @Autowired
    private PurchaseContractMapper purchaseContractMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchaseContractMockMvc;

    private PurchaseContract purchaseContract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseContract createEntity(EntityManager em) {
        PurchaseContract purchaseContract = new PurchaseContract()
            .purchasingWindow(DEFAULT_PURCHASING_WINDOW)
            .soymealQuality(DEFAULT_SOYMEAL_QUALITY)
            .price(DEFAULT_PRICE)
            .volume(DEFAULT_VOLUME)
            .status(DEFAULT_STATUS);
        // Add required entity
        Port port;
        if (TestUtil.findAll(em, Port.class).isEmpty()) {
            port = PortResourceIT.createEntity(em);
            em.persist(port);
            em.flush();
        } else {
            port = TestUtil.findAll(em, Port.class).get(0);
        }
        purchaseContract.setPort(port);
        return purchaseContract;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseContract createUpdatedEntity(EntityManager em) {
        PurchaseContract purchaseContract = new PurchaseContract()
            .purchasingWindow(UPDATED_PURCHASING_WINDOW)
            .soymealQuality(UPDATED_SOYMEAL_QUALITY)
            .price(UPDATED_PRICE)
            .volume(UPDATED_VOLUME)
            .status(UPDATED_STATUS);
        // Add required entity
        Port port;
        if (TestUtil.findAll(em, Port.class).isEmpty()) {
            port = PortResourceIT.createUpdatedEntity(em);
            em.persist(port);
            em.flush();
        } else {
            port = TestUtil.findAll(em, Port.class).get(0);
        }
        purchaseContract.setPort(port);
        return purchaseContract;
    }

    @BeforeEach
    public void initTest() {
        purchaseContract = createEntity(em);
    }

    @Test
    @Transactional
    void createPurchaseContract() throws Exception {
        int databaseSizeBeforeCreate = purchaseContractRepository.findAll().size();
        // Create the PurchaseContract
        PurchaseContractDTO purchaseContractDTO = purchaseContractMapper.toDto(purchaseContract);
        restPurchaseContractMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseContractDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PurchaseContract in the database
        List<PurchaseContract> purchaseContractList = purchaseContractRepository.findAll();
        assertThat(purchaseContractList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseContract testPurchaseContract = purchaseContractList.get(purchaseContractList.size() - 1);
        assertThat(testPurchaseContract.getPurchasingWindow()).isEqualTo(DEFAULT_PURCHASING_WINDOW);
        assertThat(testPurchaseContract.getSoymealQuality()).isEqualTo(DEFAULT_SOYMEAL_QUALITY);
        assertThat(testPurchaseContract.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testPurchaseContract.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testPurchaseContract.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createPurchaseContractWithExistingId() throws Exception {
        // Create the PurchaseContract with an existing ID
        purchaseContract.setId(1L);
        PurchaseContractDTO purchaseContractDTO = purchaseContractMapper.toDto(purchaseContract);

        int databaseSizeBeforeCreate = purchaseContractRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseContractMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseContract in the database
        List<PurchaseContract> purchaseContractList = purchaseContractRepository.findAll();
        assertThat(purchaseContractList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPurchaseContracts() throws Exception {
        // Initialize the database
        purchaseContractRepository.saveAndFlush(purchaseContract);

        // Get all the purchaseContractList
        restPurchaseContractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].purchasingWindow").value(hasItem(DEFAULT_PURCHASING_WINDOW.intValue())))
            .andExpect(jsonPath("$.[*].soymealQuality").value(hasItem(DEFAULT_SOYMEAL_QUALITY.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    void getPurchaseContract() throws Exception {
        // Initialize the database
        purchaseContractRepository.saveAndFlush(purchaseContract);

        // Get the purchaseContract
        restPurchaseContractMockMvc
            .perform(get(ENTITY_API_URL_ID, purchaseContract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseContract.getId().intValue()))
            .andExpect(jsonPath("$.purchasingWindow").value(DEFAULT_PURCHASING_WINDOW.intValue()))
            .andExpect(jsonPath("$.soymealQuality").value(DEFAULT_SOYMEAL_QUALITY.toString()))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPurchaseContract() throws Exception {
        // Get the purchaseContract
        restPurchaseContractMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPurchaseContract() throws Exception {
        // Initialize the database
        purchaseContractRepository.saveAndFlush(purchaseContract);

        int databaseSizeBeforeUpdate = purchaseContractRepository.findAll().size();

        // Update the purchaseContract
        PurchaseContract updatedPurchaseContract = purchaseContractRepository.findById(purchaseContract.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseContract are not directly saved in db
        em.detach(updatedPurchaseContract);
        updatedPurchaseContract
            .purchasingWindow(UPDATED_PURCHASING_WINDOW)
            .soymealQuality(UPDATED_SOYMEAL_QUALITY)
            .price(UPDATED_PRICE)
            .volume(UPDATED_VOLUME)
            .status(UPDATED_STATUS);
        PurchaseContractDTO purchaseContractDTO = purchaseContractMapper.toDto(updatedPurchaseContract);

        restPurchaseContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, purchaseContractDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseContractDTO))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseContract in the database
        List<PurchaseContract> purchaseContractList = purchaseContractRepository.findAll();
        assertThat(purchaseContractList).hasSize(databaseSizeBeforeUpdate);
        PurchaseContract testPurchaseContract = purchaseContractList.get(purchaseContractList.size() - 1);
        assertThat(testPurchaseContract.getPurchasingWindow()).isEqualTo(UPDATED_PURCHASING_WINDOW);
        assertThat(testPurchaseContract.getSoymealQuality()).isEqualTo(UPDATED_SOYMEAL_QUALITY);
        assertThat(testPurchaseContract.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testPurchaseContract.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testPurchaseContract.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPurchaseContract() throws Exception {
        int databaseSizeBeforeUpdate = purchaseContractRepository.findAll().size();
        purchaseContract.setId(count.incrementAndGet());

        // Create the PurchaseContract
        PurchaseContractDTO purchaseContractDTO = purchaseContractMapper.toDto(purchaseContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, purchaseContractDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseContract in the database
        List<PurchaseContract> purchaseContractList = purchaseContractRepository.findAll();
        assertThat(purchaseContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPurchaseContract() throws Exception {
        int databaseSizeBeforeUpdate = purchaseContractRepository.findAll().size();
        purchaseContract.setId(count.incrementAndGet());

        // Create the PurchaseContract
        PurchaseContractDTO purchaseContractDTO = purchaseContractMapper.toDto(purchaseContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseContract in the database
        List<PurchaseContract> purchaseContractList = purchaseContractRepository.findAll();
        assertThat(purchaseContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPurchaseContract() throws Exception {
        int databaseSizeBeforeUpdate = purchaseContractRepository.findAll().size();
        purchaseContract.setId(count.incrementAndGet());

        // Create the PurchaseContract
        PurchaseContractDTO purchaseContractDTO = purchaseContractMapper.toDto(purchaseContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseContractMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseContractDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurchaseContract in the database
        List<PurchaseContract> purchaseContractList = purchaseContractRepository.findAll();
        assertThat(purchaseContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePurchaseContractWithPatch() throws Exception {
        // Initialize the database
        purchaseContractRepository.saveAndFlush(purchaseContract);

        int databaseSizeBeforeUpdate = purchaseContractRepository.findAll().size();

        // Update the purchaseContract using partial update
        PurchaseContract partialUpdatedPurchaseContract = new PurchaseContract();
        partialUpdatedPurchaseContract.setId(purchaseContract.getId());

        partialUpdatedPurchaseContract.soymealQuality(UPDATED_SOYMEAL_QUALITY).volume(UPDATED_VOLUME);

        restPurchaseContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurchaseContract.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurchaseContract))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseContract in the database
        List<PurchaseContract> purchaseContractList = purchaseContractRepository.findAll();
        assertThat(purchaseContractList).hasSize(databaseSizeBeforeUpdate);
        PurchaseContract testPurchaseContract = purchaseContractList.get(purchaseContractList.size() - 1);
        assertThat(testPurchaseContract.getPurchasingWindow()).isEqualTo(DEFAULT_PURCHASING_WINDOW);
        assertThat(testPurchaseContract.getSoymealQuality()).isEqualTo(UPDATED_SOYMEAL_QUALITY);
        assertThat(testPurchaseContract.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testPurchaseContract.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testPurchaseContract.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePurchaseContractWithPatch() throws Exception {
        // Initialize the database
        purchaseContractRepository.saveAndFlush(purchaseContract);

        int databaseSizeBeforeUpdate = purchaseContractRepository.findAll().size();

        // Update the purchaseContract using partial update
        PurchaseContract partialUpdatedPurchaseContract = new PurchaseContract();
        partialUpdatedPurchaseContract.setId(purchaseContract.getId());

        partialUpdatedPurchaseContract
            .purchasingWindow(UPDATED_PURCHASING_WINDOW)
            .soymealQuality(UPDATED_SOYMEAL_QUALITY)
            .price(UPDATED_PRICE)
            .volume(UPDATED_VOLUME)
            .status(UPDATED_STATUS);

        restPurchaseContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurchaseContract.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurchaseContract))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseContract in the database
        List<PurchaseContract> purchaseContractList = purchaseContractRepository.findAll();
        assertThat(purchaseContractList).hasSize(databaseSizeBeforeUpdate);
        PurchaseContract testPurchaseContract = purchaseContractList.get(purchaseContractList.size() - 1);
        assertThat(testPurchaseContract.getPurchasingWindow()).isEqualTo(UPDATED_PURCHASING_WINDOW);
        assertThat(testPurchaseContract.getSoymealQuality()).isEqualTo(UPDATED_SOYMEAL_QUALITY);
        assertThat(testPurchaseContract.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testPurchaseContract.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testPurchaseContract.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPurchaseContract() throws Exception {
        int databaseSizeBeforeUpdate = purchaseContractRepository.findAll().size();
        purchaseContract.setId(count.incrementAndGet());

        // Create the PurchaseContract
        PurchaseContractDTO purchaseContractDTO = purchaseContractMapper.toDto(purchaseContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, purchaseContractDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseContract in the database
        List<PurchaseContract> purchaseContractList = purchaseContractRepository.findAll();
        assertThat(purchaseContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPurchaseContract() throws Exception {
        int databaseSizeBeforeUpdate = purchaseContractRepository.findAll().size();
        purchaseContract.setId(count.incrementAndGet());

        // Create the PurchaseContract
        PurchaseContractDTO purchaseContractDTO = purchaseContractMapper.toDto(purchaseContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseContract in the database
        List<PurchaseContract> purchaseContractList = purchaseContractRepository.findAll();
        assertThat(purchaseContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPurchaseContract() throws Exception {
        int databaseSizeBeforeUpdate = purchaseContractRepository.findAll().size();
        purchaseContract.setId(count.incrementAndGet());

        // Create the PurchaseContract
        PurchaseContractDTO purchaseContractDTO = purchaseContractMapper.toDto(purchaseContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseContractMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseContractDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurchaseContract in the database
        List<PurchaseContract> purchaseContractList = purchaseContractRepository.findAll();
        assertThat(purchaseContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePurchaseContract() throws Exception {
        // Initialize the database
        purchaseContractRepository.saveAndFlush(purchaseContract);

        int databaseSizeBeforeDelete = purchaseContractRepository.findAll().size();

        // Delete the purchaseContract
        restPurchaseContractMockMvc
            .perform(delete(ENTITY_API_URL_ID, purchaseContract.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseContract> purchaseContractList = purchaseContractRepository.findAll();
        assertThat(purchaseContractList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
