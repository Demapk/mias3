package ru.kpfu.web.rest;

import ru.kpfu.MiasApp;

import ru.kpfu.domain.DrugOrder;
import ru.kpfu.repository.DrugOrderRepository;
import ru.kpfu.service.DrugOrderService;
import ru.kpfu.service.dto.DrugOrderDTO;
import ru.kpfu.service.mapper.DrugOrderMapper;
import ru.kpfu.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static ru.kpfu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DrugOrderResource REST controller.
 *
 * @see DrugOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiasApp.class)
public class DrugOrderResourceIntTest {

    private static final Double DEFAULT_DOSE = 1D;
    private static final Double UPDATED_DOSE = 2D;

    private static final Double DEFAULT_DAILY_DOSE = 1D;
    private static final Double UPDATED_DAILY_DOSE = 2D;

    private static final String DEFAULT_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_UNITS = "BBBBBBBBBB";

    private static final String DEFAULT_FREQUENCEY = "AAAAAAAAAA";
    private static final String UPDATED_FREQUENCEY = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private DrugOrderRepository drugOrderRepository;

    @Autowired
    private DrugOrderMapper drugOrderMapper;

    @Autowired
    private DrugOrderService drugOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDrugOrderMockMvc;

    private DrugOrder drugOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DrugOrderResource drugOrderResource = new DrugOrderResource(drugOrderService);
        this.restDrugOrderMockMvc = MockMvcBuilders.standaloneSetup(drugOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DrugOrder createEntity(EntityManager em) {
        DrugOrder drugOrder = new DrugOrder()
            .dose(DEFAULT_DOSE)
            .dailyDose(DEFAULT_DAILY_DOSE)
            .units(DEFAULT_UNITS)
            .frequencey(DEFAULT_FREQUENCEY)
            .quantity(DEFAULT_QUANTITY);
        return drugOrder;
    }

    @Before
    public void initTest() {
        drugOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrugOrder() throws Exception {
        int databaseSizeBeforeCreate = drugOrderRepository.findAll().size();

        // Create the DrugOrder
        DrugOrderDTO drugOrderDTO = drugOrderMapper.toDto(drugOrder);
        restDrugOrderMockMvc.perform(post("/api/drug-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the DrugOrder in the database
        List<DrugOrder> drugOrderList = drugOrderRepository.findAll();
        assertThat(drugOrderList).hasSize(databaseSizeBeforeCreate + 1);
        DrugOrder testDrugOrder = drugOrderList.get(drugOrderList.size() - 1);
        assertThat(testDrugOrder.getDose()).isEqualTo(DEFAULT_DOSE);
        assertThat(testDrugOrder.getDailyDose()).isEqualTo(DEFAULT_DAILY_DOSE);
        assertThat(testDrugOrder.getUnits()).isEqualTo(DEFAULT_UNITS);
        assertThat(testDrugOrder.getFrequencey()).isEqualTo(DEFAULT_FREQUENCEY);
        assertThat(testDrugOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createDrugOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drugOrderRepository.findAll().size();

        // Create the DrugOrder with an existing ID
        drugOrder.setId(1L);
        DrugOrderDTO drugOrderDTO = drugOrderMapper.toDto(drugOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrugOrderMockMvc.perform(post("/api/drug-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DrugOrder in the database
        List<DrugOrder> drugOrderList = drugOrderRepository.findAll();
        assertThat(drugOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDrugOrders() throws Exception {
        // Initialize the database
        drugOrderRepository.saveAndFlush(drugOrder);

        // Get all the drugOrderList
        restDrugOrderMockMvc.perform(get("/api/drug-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drugOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].dose").value(hasItem(DEFAULT_DOSE.doubleValue())))
            .andExpect(jsonPath("$.[*].dailyDose").value(hasItem(DEFAULT_DAILY_DOSE.doubleValue())))
            .andExpect(jsonPath("$.[*].units").value(hasItem(DEFAULT_UNITS.toString())))
            .andExpect(jsonPath("$.[*].frequencey").value(hasItem(DEFAULT_FREQUENCEY.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    public void getDrugOrder() throws Exception {
        // Initialize the database
        drugOrderRepository.saveAndFlush(drugOrder);

        // Get the drugOrder
        restDrugOrderMockMvc.perform(get("/api/drug-orders/{id}", drugOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drugOrder.getId().intValue()))
            .andExpect(jsonPath("$.dose").value(DEFAULT_DOSE.doubleValue()))
            .andExpect(jsonPath("$.dailyDose").value(DEFAULT_DAILY_DOSE.doubleValue()))
            .andExpect(jsonPath("$.units").value(DEFAULT_UNITS.toString()))
            .andExpect(jsonPath("$.frequencey").value(DEFAULT_FREQUENCEY.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingDrugOrder() throws Exception {
        // Get the drugOrder
        restDrugOrderMockMvc.perform(get("/api/drug-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrugOrder() throws Exception {
        // Initialize the database
        drugOrderRepository.saveAndFlush(drugOrder);
        int databaseSizeBeforeUpdate = drugOrderRepository.findAll().size();

        // Update the drugOrder
        DrugOrder updatedDrugOrder = drugOrderRepository.findOne(drugOrder.getId());
        // Disconnect from session so that the updates on updatedDrugOrder are not directly saved in db
        em.detach(updatedDrugOrder);
        updatedDrugOrder
            .dose(UPDATED_DOSE)
            .dailyDose(UPDATED_DAILY_DOSE)
            .units(UPDATED_UNITS)
            .frequencey(UPDATED_FREQUENCEY)
            .quantity(UPDATED_QUANTITY);
        DrugOrderDTO drugOrderDTO = drugOrderMapper.toDto(updatedDrugOrder);

        restDrugOrderMockMvc.perform(put("/api/drug-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugOrderDTO)))
            .andExpect(status().isOk());

        // Validate the DrugOrder in the database
        List<DrugOrder> drugOrderList = drugOrderRepository.findAll();
        assertThat(drugOrderList).hasSize(databaseSizeBeforeUpdate);
        DrugOrder testDrugOrder = drugOrderList.get(drugOrderList.size() - 1);
        assertThat(testDrugOrder.getDose()).isEqualTo(UPDATED_DOSE);
        assertThat(testDrugOrder.getDailyDose()).isEqualTo(UPDATED_DAILY_DOSE);
        assertThat(testDrugOrder.getUnits()).isEqualTo(UPDATED_UNITS);
        assertThat(testDrugOrder.getFrequencey()).isEqualTo(UPDATED_FREQUENCEY);
        assertThat(testDrugOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingDrugOrder() throws Exception {
        int databaseSizeBeforeUpdate = drugOrderRepository.findAll().size();

        // Create the DrugOrder
        DrugOrderDTO drugOrderDTO = drugOrderMapper.toDto(drugOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDrugOrderMockMvc.perform(put("/api/drug-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the DrugOrder in the database
        List<DrugOrder> drugOrderList = drugOrderRepository.findAll();
        assertThat(drugOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDrugOrder() throws Exception {
        // Initialize the database
        drugOrderRepository.saveAndFlush(drugOrder);
        int databaseSizeBeforeDelete = drugOrderRepository.findAll().size();

        // Get the drugOrder
        restDrugOrderMockMvc.perform(delete("/api/drug-orders/{id}", drugOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DrugOrder> drugOrderList = drugOrderRepository.findAll();
        assertThat(drugOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrugOrder.class);
        DrugOrder drugOrder1 = new DrugOrder();
        drugOrder1.setId(1L);
        DrugOrder drugOrder2 = new DrugOrder();
        drugOrder2.setId(drugOrder1.getId());
        assertThat(drugOrder1).isEqualTo(drugOrder2);
        drugOrder2.setId(2L);
        assertThat(drugOrder1).isNotEqualTo(drugOrder2);
        drugOrder1.setId(null);
        assertThat(drugOrder1).isNotEqualTo(drugOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrugOrderDTO.class);
        DrugOrderDTO drugOrderDTO1 = new DrugOrderDTO();
        drugOrderDTO1.setId(1L);
        DrugOrderDTO drugOrderDTO2 = new DrugOrderDTO();
        assertThat(drugOrderDTO1).isNotEqualTo(drugOrderDTO2);
        drugOrderDTO2.setId(drugOrderDTO1.getId());
        assertThat(drugOrderDTO1).isEqualTo(drugOrderDTO2);
        drugOrderDTO2.setId(2L);
        assertThat(drugOrderDTO1).isNotEqualTo(drugOrderDTO2);
        drugOrderDTO1.setId(null);
        assertThat(drugOrderDTO1).isNotEqualTo(drugOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(drugOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(drugOrderMapper.fromId(null)).isNull();
    }
}
