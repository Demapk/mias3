package ru.kpfu.web.rest;

import ru.kpfu.MiasApp;

import ru.kpfu.domain.Treatment;
import ru.kpfu.repository.TreatmentRepository;
import ru.kpfu.service.TreatmentService;
import ru.kpfu.service.dto.TreatmentDTO;
import ru.kpfu.service.mapper.TreatmentMapper;
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
 * Test class for the TreatmentResource REST controller.
 *
 * @see TreatmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiasApp.class)
public class TreatmentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NODE_COUNT = 1;
    private static final Integer UPDATED_NODE_COUNT = 2;

    private static final String DEFAULT_ADDITIONAL_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFO = "BBBBBBBBBB";

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private TreatmentMapper treatmentMapper;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTreatmentMockMvc;

    private Treatment treatment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TreatmentResource treatmentResource = new TreatmentResource(treatmentService);
        this.restTreatmentMockMvc = MockMvcBuilders.standaloneSetup(treatmentResource)
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
    public static Treatment createEntity(EntityManager em) {
        Treatment treatment = new Treatment()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .parentCode(DEFAULT_PARENT_CODE)
            .nodeCount(DEFAULT_NODE_COUNT)
            .additionalInfo(DEFAULT_ADDITIONAL_INFO);
        return treatment;
    }

    @Before
    public void initTest() {
        treatment = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreatment() throws Exception {
        int databaseSizeBeforeCreate = treatmentRepository.findAll().size();

        // Create the Treatment
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);
        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeCreate + 1);
        Treatment testTreatment = treatmentList.get(treatmentList.size() - 1);
        assertThat(testTreatment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTreatment.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTreatment.getParentCode()).isEqualTo(DEFAULT_PARENT_CODE);
        assertThat(testTreatment.getNodeCount()).isEqualTo(DEFAULT_NODE_COUNT);
        assertThat(testTreatment.getAdditionalInfo()).isEqualTo(DEFAULT_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    public void createTreatmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treatmentRepository.findAll().size();

        // Create the Treatment with an existing ID
        treatment.setId(1L);
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = treatmentRepository.findAll().size();
        // set the field null
        treatment.setName(null);

        // Create the Treatment, which fails.
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);

        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isBadRequest());

        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = treatmentRepository.findAll().size();
        // set the field null
        treatment.setCode(null);

        // Create the Treatment, which fails.
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);

        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isBadRequest());

        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNodeCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = treatmentRepository.findAll().size();
        // set the field null
        treatment.setNodeCount(null);

        // Create the Treatment, which fails.
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);

        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isBadRequest());

        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTreatments() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList
        restTreatmentMockMvc.perform(get("/api/treatments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].parentCode").value(hasItem(DEFAULT_PARENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].nodeCount").value(hasItem(DEFAULT_NODE_COUNT)))
            .andExpect(jsonPath("$.[*].additionalInfo").value(hasItem(DEFAULT_ADDITIONAL_INFO.toString())));
    }

    @Test
    @Transactional
    public void getTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", treatment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(treatment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.parentCode").value(DEFAULT_PARENT_CODE.toString()))
            .andExpect(jsonPath("$.nodeCount").value(DEFAULT_NODE_COUNT))
            .andExpect(jsonPath("$.additionalInfo").value(DEFAULT_ADDITIONAL_INFO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTreatment() throws Exception {
        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);
        int databaseSizeBeforeUpdate = treatmentRepository.findAll().size();

        // Update the treatment
        Treatment updatedTreatment = treatmentRepository.findOne(treatment.getId());
        // Disconnect from session so that the updates on updatedTreatment are not directly saved in db
        em.detach(updatedTreatment);
        updatedTreatment
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .parentCode(UPDATED_PARENT_CODE)
            .nodeCount(UPDATED_NODE_COUNT)
            .additionalInfo(UPDATED_ADDITIONAL_INFO);
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(updatedTreatment);

        restTreatmentMockMvc.perform(put("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isOk());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeUpdate);
        Treatment testTreatment = treatmentList.get(treatmentList.size() - 1);
        assertThat(testTreatment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTreatment.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTreatment.getParentCode()).isEqualTo(UPDATED_PARENT_CODE);
        assertThat(testTreatment.getNodeCount()).isEqualTo(UPDATED_NODE_COUNT);
        assertThat(testTreatment.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingTreatment() throws Exception {
        int databaseSizeBeforeUpdate = treatmentRepository.findAll().size();

        // Create the Treatment
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTreatmentMockMvc.perform(put("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);
        int databaseSizeBeforeDelete = treatmentRepository.findAll().size();

        // Get the treatment
        restTreatmentMockMvc.perform(delete("/api/treatments/{id}", treatment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Treatment.class);
        Treatment treatment1 = new Treatment();
        treatment1.setId(1L);
        Treatment treatment2 = new Treatment();
        treatment2.setId(treatment1.getId());
        assertThat(treatment1).isEqualTo(treatment2);
        treatment2.setId(2L);
        assertThat(treatment1).isNotEqualTo(treatment2);
        treatment1.setId(null);
        assertThat(treatment1).isNotEqualTo(treatment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TreatmentDTO.class);
        TreatmentDTO treatmentDTO1 = new TreatmentDTO();
        treatmentDTO1.setId(1L);
        TreatmentDTO treatmentDTO2 = new TreatmentDTO();
        assertThat(treatmentDTO1).isNotEqualTo(treatmentDTO2);
        treatmentDTO2.setId(treatmentDTO1.getId());
        assertThat(treatmentDTO1).isEqualTo(treatmentDTO2);
        treatmentDTO2.setId(2L);
        assertThat(treatmentDTO1).isNotEqualTo(treatmentDTO2);
        treatmentDTO1.setId(null);
        assertThat(treatmentDTO1).isNotEqualTo(treatmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(treatmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(treatmentMapper.fromId(null)).isNull();
    }
}
