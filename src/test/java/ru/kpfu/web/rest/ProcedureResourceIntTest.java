package ru.kpfu.web.rest;

import ru.kpfu.MiasApp;

import ru.kpfu.domain.Procedure;
import ru.kpfu.repository.ProcedureRepository;
import ru.kpfu.service.ProcedureService;
import ru.kpfu.service.dto.ProcedureDTO;
import ru.kpfu.service.mapper.ProcedureMapper;
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
 * Test class for the ProcedureResource REST controller.
 *
 * @see ProcedureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiasApp.class)
public class ProcedureResourceIntTest {

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
    private ProcedureRepository procedureRepository;

    @Autowired
    private ProcedureMapper procedureMapper;

    @Autowired
    private ProcedureService procedureService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProcedureMockMvc;

    private Procedure procedure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcedureResource procedureResource = new ProcedureResource(procedureService);
        this.restProcedureMockMvc = MockMvcBuilders.standaloneSetup(procedureResource)
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
    public static Procedure createEntity(EntityManager em) {
        Procedure procedure = new Procedure()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .parentCode(DEFAULT_PARENT_CODE)
            .nodeCount(DEFAULT_NODE_COUNT)
            .additionalInfo(DEFAULT_ADDITIONAL_INFO);
        return procedure;
    }

    @Before
    public void initTest() {
        procedure = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcedure() throws Exception {
        int databaseSizeBeforeCreate = procedureRepository.findAll().size();

        // Create the Procedure
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);
        restProcedureMockMvc.perform(post("/api/procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureDTO)))
            .andExpect(status().isCreated());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeCreate + 1);
        Procedure testProcedure = procedureList.get(procedureList.size() - 1);
        assertThat(testProcedure.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProcedure.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProcedure.getParentCode()).isEqualTo(DEFAULT_PARENT_CODE);
        assertThat(testProcedure.getNodeCount()).isEqualTo(DEFAULT_NODE_COUNT);
        assertThat(testProcedure.getAdditionalInfo()).isEqualTo(DEFAULT_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    public void createProcedureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procedureRepository.findAll().size();

        // Create the Procedure with an existing ID
        procedure.setId(1L);
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedureMockMvc.perform(post("/api/procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedureRepository.findAll().size();
        // set the field null
        procedure.setName(null);

        // Create the Procedure, which fails.
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);

        restProcedureMockMvc.perform(post("/api/procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureDTO)))
            .andExpect(status().isBadRequest());

        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedureRepository.findAll().size();
        // set the field null
        procedure.setCode(null);

        // Create the Procedure, which fails.
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);

        restProcedureMockMvc.perform(post("/api/procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureDTO)))
            .andExpect(status().isBadRequest());

        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNodeCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedureRepository.findAll().size();
        // set the field null
        procedure.setNodeCount(null);

        // Create the Procedure, which fails.
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);

        restProcedureMockMvc.perform(post("/api/procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureDTO)))
            .andExpect(status().isBadRequest());

        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProcedures() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList
        restProcedureMockMvc.perform(get("/api/procedures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].parentCode").value(hasItem(DEFAULT_PARENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].nodeCount").value(hasItem(DEFAULT_NODE_COUNT)))
            .andExpect(jsonPath("$.[*].additionalInfo").value(hasItem(DEFAULT_ADDITIONAL_INFO.toString())));
    }

    @Test
    @Transactional
    public void getProcedure() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get the procedure
        restProcedureMockMvc.perform(get("/api/procedures/{id}", procedure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(procedure.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.parentCode").value(DEFAULT_PARENT_CODE.toString()))
            .andExpect(jsonPath("$.nodeCount").value(DEFAULT_NODE_COUNT))
            .andExpect(jsonPath("$.additionalInfo").value(DEFAULT_ADDITIONAL_INFO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProcedure() throws Exception {
        // Get the procedure
        restProcedureMockMvc.perform(get("/api/procedures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcedure() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();

        // Update the procedure
        Procedure updatedProcedure = procedureRepository.findOne(procedure.getId());
        // Disconnect from session so that the updates on updatedProcedure are not directly saved in db
        em.detach(updatedProcedure);
        updatedProcedure
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .parentCode(UPDATED_PARENT_CODE)
            .nodeCount(UPDATED_NODE_COUNT)
            .additionalInfo(UPDATED_ADDITIONAL_INFO);
        ProcedureDTO procedureDTO = procedureMapper.toDto(updatedProcedure);

        restProcedureMockMvc.perform(put("/api/procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureDTO)))
            .andExpect(status().isOk());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
        Procedure testProcedure = procedureList.get(procedureList.size() - 1);
        assertThat(testProcedure.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProcedure.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProcedure.getParentCode()).isEqualTo(UPDATED_PARENT_CODE);
        assertThat(testProcedure.getNodeCount()).isEqualTo(UPDATED_NODE_COUNT);
        assertThat(testProcedure.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();

        // Create the Procedure
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProcedureMockMvc.perform(put("/api/procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureDTO)))
            .andExpect(status().isCreated());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProcedure() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);
        int databaseSizeBeforeDelete = procedureRepository.findAll().size();

        // Get the procedure
        restProcedureMockMvc.perform(delete("/api/procedures/{id}", procedure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procedure.class);
        Procedure procedure1 = new Procedure();
        procedure1.setId(1L);
        Procedure procedure2 = new Procedure();
        procedure2.setId(procedure1.getId());
        assertThat(procedure1).isEqualTo(procedure2);
        procedure2.setId(2L);
        assertThat(procedure1).isNotEqualTo(procedure2);
        procedure1.setId(null);
        assertThat(procedure1).isNotEqualTo(procedure2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcedureDTO.class);
        ProcedureDTO procedureDTO1 = new ProcedureDTO();
        procedureDTO1.setId(1L);
        ProcedureDTO procedureDTO2 = new ProcedureDTO();
        assertThat(procedureDTO1).isNotEqualTo(procedureDTO2);
        procedureDTO2.setId(procedureDTO1.getId());
        assertThat(procedureDTO1).isEqualTo(procedureDTO2);
        procedureDTO2.setId(2L);
        assertThat(procedureDTO1).isNotEqualTo(procedureDTO2);
        procedureDTO1.setId(null);
        assertThat(procedureDTO1).isNotEqualTo(procedureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(procedureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(procedureMapper.fromId(null)).isNull();
    }
}
