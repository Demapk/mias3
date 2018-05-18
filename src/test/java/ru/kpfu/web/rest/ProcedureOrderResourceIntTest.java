package ru.kpfu.web.rest;

import ru.kpfu.MiasApp;

import ru.kpfu.domain.ProcedureOrder;
import ru.kpfu.repository.ProcedureOrderRepository;
import ru.kpfu.service.ProcedureOrderService;
import ru.kpfu.service.dto.ProcedureOrderDTO;
import ru.kpfu.service.mapper.ProcedureOrderMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.kpfu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProcedureOrderResource REST controller.
 *
 * @see ProcedureOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiasApp.class)
public class ProcedureOrderResourceIntTest {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProcedureOrderRepository procedureOrderRepository;

    @Autowired
    private ProcedureOrderMapper procedureOrderMapper;

    @Autowired
    private ProcedureOrderService procedureOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProcedureOrderMockMvc;

    private ProcedureOrder procedureOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcedureOrderResource procedureOrderResource = new ProcedureOrderResource(procedureOrderService);
        this.restProcedureOrderMockMvc = MockMvcBuilders.standaloneSetup(procedureOrderResource)
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
    public static ProcedureOrder createEntity(EntityManager em) {
        ProcedureOrder procedureOrder = new ProcedureOrder()
            .date(DEFAULT_DATE);
        return procedureOrder;
    }

    @Before
    public void initTest() {
        procedureOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcedureOrder() throws Exception {
        int databaseSizeBeforeCreate = procedureOrderRepository.findAll().size();

        // Create the ProcedureOrder
        ProcedureOrderDTO procedureOrderDTO = procedureOrderMapper.toDto(procedureOrder);
        restProcedureOrderMockMvc.perform(post("/api/procedure-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the ProcedureOrder in the database
        List<ProcedureOrder> procedureOrderList = procedureOrderRepository.findAll();
        assertThat(procedureOrderList).hasSize(databaseSizeBeforeCreate + 1);
        ProcedureOrder testProcedureOrder = procedureOrderList.get(procedureOrderList.size() - 1);
        assertThat(testProcedureOrder.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createProcedureOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procedureOrderRepository.findAll().size();

        // Create the ProcedureOrder with an existing ID
        procedureOrder.setId(1L);
        ProcedureOrderDTO procedureOrderDTO = procedureOrderMapper.toDto(procedureOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedureOrderMockMvc.perform(post("/api/procedure-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProcedureOrder in the database
        List<ProcedureOrder> procedureOrderList = procedureOrderRepository.findAll();
        assertThat(procedureOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProcedureOrders() throws Exception {
        // Initialize the database
        procedureOrderRepository.saveAndFlush(procedureOrder);

        // Get all the procedureOrderList
        restProcedureOrderMockMvc.perform(get("/api/procedure-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedureOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getProcedureOrder() throws Exception {
        // Initialize the database
        procedureOrderRepository.saveAndFlush(procedureOrder);

        // Get the procedureOrder
        restProcedureOrderMockMvc.perform(get("/api/procedure-orders/{id}", procedureOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(procedureOrder.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProcedureOrder() throws Exception {
        // Get the procedureOrder
        restProcedureOrderMockMvc.perform(get("/api/procedure-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcedureOrder() throws Exception {
        // Initialize the database
        procedureOrderRepository.saveAndFlush(procedureOrder);
        int databaseSizeBeforeUpdate = procedureOrderRepository.findAll().size();

        // Update the procedureOrder
        ProcedureOrder updatedProcedureOrder = procedureOrderRepository.findOne(procedureOrder.getId());
        // Disconnect from session so that the updates on updatedProcedureOrder are not directly saved in db
        em.detach(updatedProcedureOrder);
        updatedProcedureOrder
            .date(UPDATED_DATE);
        ProcedureOrderDTO procedureOrderDTO = procedureOrderMapper.toDto(updatedProcedureOrder);

        restProcedureOrderMockMvc.perform(put("/api/procedure-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureOrderDTO)))
            .andExpect(status().isOk());

        // Validate the ProcedureOrder in the database
        List<ProcedureOrder> procedureOrderList = procedureOrderRepository.findAll();
        assertThat(procedureOrderList).hasSize(databaseSizeBeforeUpdate);
        ProcedureOrder testProcedureOrder = procedureOrderList.get(procedureOrderList.size() - 1);
        assertThat(testProcedureOrder.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProcedureOrder() throws Exception {
        int databaseSizeBeforeUpdate = procedureOrderRepository.findAll().size();

        // Create the ProcedureOrder
        ProcedureOrderDTO procedureOrderDTO = procedureOrderMapper.toDto(procedureOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProcedureOrderMockMvc.perform(put("/api/procedure-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the ProcedureOrder in the database
        List<ProcedureOrder> procedureOrderList = procedureOrderRepository.findAll();
        assertThat(procedureOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProcedureOrder() throws Exception {
        // Initialize the database
        procedureOrderRepository.saveAndFlush(procedureOrder);
        int databaseSizeBeforeDelete = procedureOrderRepository.findAll().size();

        // Get the procedureOrder
        restProcedureOrderMockMvc.perform(delete("/api/procedure-orders/{id}", procedureOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProcedureOrder> procedureOrderList = procedureOrderRepository.findAll();
        assertThat(procedureOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcedureOrder.class);
        ProcedureOrder procedureOrder1 = new ProcedureOrder();
        procedureOrder1.setId(1L);
        ProcedureOrder procedureOrder2 = new ProcedureOrder();
        procedureOrder2.setId(procedureOrder1.getId());
        assertThat(procedureOrder1).isEqualTo(procedureOrder2);
        procedureOrder2.setId(2L);
        assertThat(procedureOrder1).isNotEqualTo(procedureOrder2);
        procedureOrder1.setId(null);
        assertThat(procedureOrder1).isNotEqualTo(procedureOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcedureOrderDTO.class);
        ProcedureOrderDTO procedureOrderDTO1 = new ProcedureOrderDTO();
        procedureOrderDTO1.setId(1L);
        ProcedureOrderDTO procedureOrderDTO2 = new ProcedureOrderDTO();
        assertThat(procedureOrderDTO1).isNotEqualTo(procedureOrderDTO2);
        procedureOrderDTO2.setId(procedureOrderDTO1.getId());
        assertThat(procedureOrderDTO1).isEqualTo(procedureOrderDTO2);
        procedureOrderDTO2.setId(2L);
        assertThat(procedureOrderDTO1).isNotEqualTo(procedureOrderDTO2);
        procedureOrderDTO1.setId(null);
        assertThat(procedureOrderDTO1).isNotEqualTo(procedureOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(procedureOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(procedureOrderMapper.fromId(null)).isNull();
    }
}
