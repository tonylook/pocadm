package ch.orange.web.rest;

import static ch.orange.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.orange.IntegrationTest;
import ch.orange.domain.Port;
import ch.orange.domain.SaleContract;
import ch.orange.domain.enumeration.Quality;
import ch.orange.repository.SaleContractRepository;
import ch.orange.service.dto.SaleContractDTO;
import ch.orange.service.mapper.SaleContractMapper;
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
 * Integration tests for the {@link SaleContractResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaleContractResourceIT {

    private static final Long DEFAULT_DELIVERY_WINDOW = 1L;
    private static final Long UPDATED_DELIVERY_WINDOW = 2L;

    private static final Quality DEFAULT_SOYMEAL_QUALITY = Quality.BAD;
    private static final Quality UPDATED_SOYMEAL_QUALITY = Quality.GOOD;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Float DEFAULT_VOLUME = 1F;
    private static final Float UPDATED_VOLUME = 2F;

    private static final Float DEFAULT_ALLOWANCES = 1F;
    private static final Float UPDATED_ALLOWANCES = 2F;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/sale-contracts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SaleContractRepository saleContractRepository;

    @Autowired
    private SaleContractMapper saleContractMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaleContractMockMvc;

    private SaleContract saleContract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleContract createEntity(EntityManager em) {
        SaleContract saleContract = new SaleContract()
            .deliveryWindow(DEFAULT_DELIVERY_WINDOW)
            .soymealQuality(DEFAULT_SOYMEAL_QUALITY)
            .price(DEFAULT_PRICE)
            .volume(DEFAULT_VOLUME)
            .allowances(DEFAULT_ALLOWANCES)
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
        saleContract.setPort(port);
        return saleContract;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleContract createUpdatedEntity(EntityManager em) {
        SaleContract saleContract = new SaleContract()
            .deliveryWindow(UPDATED_DELIVERY_WINDOW)
            .soymealQuality(UPDATED_SOYMEAL_QUALITY)
            .price(UPDATED_PRICE)
            .volume(UPDATED_VOLUME)
            .allowances(UPDATED_ALLOWANCES)
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
        saleContract.setPort(port);
        return saleContract;
    }

    @BeforeEach
    public void initTest() {
        saleContract = createEntity(em);
    }

    @Test
    @Transactional
    void createSaleContract() throws Exception {
        int databaseSizeBeforeCreate = saleContractRepository.findAll().size();
        // Create the SaleContract
        SaleContractDTO saleContractDTO = saleContractMapper.toDto(saleContract);
        restSaleContractMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleContractDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeCreate + 1);
        SaleContract testSaleContract = saleContractList.get(saleContractList.size() - 1);
        assertThat(testSaleContract.getDeliveryWindow()).isEqualTo(DEFAULT_DELIVERY_WINDOW);
        assertThat(testSaleContract.getSoymealQuality()).isEqualTo(DEFAULT_SOYMEAL_QUALITY);
        assertThat(testSaleContract.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testSaleContract.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testSaleContract.getAllowances()).isEqualTo(DEFAULT_ALLOWANCES);
        assertThat(testSaleContract.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createSaleContractWithExistingId() throws Exception {
        // Create the SaleContract with an existing ID
        saleContract.setId(1L);
        SaleContractDTO saleContractDTO = saleContractMapper.toDto(saleContract);

        int databaseSizeBeforeCreate = saleContractRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleContractMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSaleContracts() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList
        restSaleContractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliveryWindow").value(hasItem(DEFAULT_DELIVERY_WINDOW.intValue())))
            .andExpect(jsonPath("$.[*].soymealQuality").value(hasItem(DEFAULT_SOYMEAL_QUALITY.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].allowances").value(hasItem(DEFAULT_ALLOWANCES.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    void getSaleContract() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get the saleContract
        restSaleContractMockMvc
            .perform(get(ENTITY_API_URL_ID, saleContract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(saleContract.getId().intValue()))
            .andExpect(jsonPath("$.deliveryWindow").value(DEFAULT_DELIVERY_WINDOW.intValue()))
            .andExpect(jsonPath("$.soymealQuality").value(DEFAULT_SOYMEAL_QUALITY.toString()))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.allowances").value(DEFAULT_ALLOWANCES.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSaleContract() throws Exception {
        // Get the saleContract
        restSaleContractMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSaleContract() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();

        // Update the saleContract
        SaleContract updatedSaleContract = saleContractRepository.findById(saleContract.getId()).get();
        // Disconnect from session so that the updates on updatedSaleContract are not directly saved in db
        em.detach(updatedSaleContract);
        updatedSaleContract
            .deliveryWindow(UPDATED_DELIVERY_WINDOW)
            .soymealQuality(UPDATED_SOYMEAL_QUALITY)
            .price(UPDATED_PRICE)
            .volume(UPDATED_VOLUME)
            .allowances(UPDATED_ALLOWANCES)
            .status(UPDATED_STATUS);
        SaleContractDTO saleContractDTO = saleContractMapper.toDto(updatedSaleContract);

        restSaleContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saleContractDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleContractDTO))
            )
            .andExpect(status().isOk());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
        SaleContract testSaleContract = saleContractList.get(saleContractList.size() - 1);
        assertThat(testSaleContract.getDeliveryWindow()).isEqualTo(UPDATED_DELIVERY_WINDOW);
        assertThat(testSaleContract.getSoymealQuality()).isEqualTo(UPDATED_SOYMEAL_QUALITY);
        assertThat(testSaleContract.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testSaleContract.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testSaleContract.getAllowances()).isEqualTo(UPDATED_ALLOWANCES);
        assertThat(testSaleContract.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingSaleContract() throws Exception {
        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();
        saleContract.setId(count.incrementAndGet());

        // Create the SaleContract
        SaleContractDTO saleContractDTO = saleContractMapper.toDto(saleContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saleContractDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSaleContract() throws Exception {
        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();
        saleContract.setId(count.incrementAndGet());

        // Create the SaleContract
        SaleContractDTO saleContractDTO = saleContractMapper.toDto(saleContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSaleContract() throws Exception {
        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();
        saleContract.setId(count.incrementAndGet());

        // Create the SaleContract
        SaleContractDTO saleContractDTO = saleContractMapper.toDto(saleContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleContractMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleContractDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSaleContractWithPatch() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();

        // Update the saleContract using partial update
        SaleContract partialUpdatedSaleContract = new SaleContract();
        partialUpdatedSaleContract.setId(saleContract.getId());

        partialUpdatedSaleContract.allowances(UPDATED_ALLOWANCES);

        restSaleContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleContract.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaleContract))
            )
            .andExpect(status().isOk());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
        SaleContract testSaleContract = saleContractList.get(saleContractList.size() - 1);
        assertThat(testSaleContract.getDeliveryWindow()).isEqualTo(DEFAULT_DELIVERY_WINDOW);
        assertThat(testSaleContract.getSoymealQuality()).isEqualTo(DEFAULT_SOYMEAL_QUALITY);
        assertThat(testSaleContract.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testSaleContract.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testSaleContract.getAllowances()).isEqualTo(UPDATED_ALLOWANCES);
        assertThat(testSaleContract.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateSaleContractWithPatch() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();

        // Update the saleContract using partial update
        SaleContract partialUpdatedSaleContract = new SaleContract();
        partialUpdatedSaleContract.setId(saleContract.getId());

        partialUpdatedSaleContract
            .deliveryWindow(UPDATED_DELIVERY_WINDOW)
            .soymealQuality(UPDATED_SOYMEAL_QUALITY)
            .price(UPDATED_PRICE)
            .volume(UPDATED_VOLUME)
            .allowances(UPDATED_ALLOWANCES)
            .status(UPDATED_STATUS);

        restSaleContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleContract.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaleContract))
            )
            .andExpect(status().isOk());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
        SaleContract testSaleContract = saleContractList.get(saleContractList.size() - 1);
        assertThat(testSaleContract.getDeliveryWindow()).isEqualTo(UPDATED_DELIVERY_WINDOW);
        assertThat(testSaleContract.getSoymealQuality()).isEqualTo(UPDATED_SOYMEAL_QUALITY);
        assertThat(testSaleContract.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testSaleContract.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testSaleContract.getAllowances()).isEqualTo(UPDATED_ALLOWANCES);
        assertThat(testSaleContract.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingSaleContract() throws Exception {
        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();
        saleContract.setId(count.incrementAndGet());

        // Create the SaleContract
        SaleContractDTO saleContractDTO = saleContractMapper.toDto(saleContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saleContractDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saleContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSaleContract() throws Exception {
        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();
        saleContract.setId(count.incrementAndGet());

        // Create the SaleContract
        SaleContractDTO saleContractDTO = saleContractMapper.toDto(saleContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saleContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSaleContract() throws Exception {
        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();
        saleContract.setId(count.incrementAndGet());

        // Create the SaleContract
        SaleContractDTO saleContractDTO = saleContractMapper.toDto(saleContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleContractMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saleContractDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSaleContract() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        int databaseSizeBeforeDelete = saleContractRepository.findAll().size();

        // Delete the saleContract
        restSaleContractMockMvc
            .perform(delete(ENTITY_API_URL_ID, saleContract.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
