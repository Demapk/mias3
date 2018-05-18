package ru.kpfu.web.rest;

import ru.kpfu.MiasApp;

import ru.kpfu.domain.Diseas;
import ru.kpfu.repository.DiseasRepository;
import ru.kpfu.service.DiseasService;
import ru.kpfu.service.dto.DiseasDTO;
import ru.kpfu.service.mapper.DiseasMapper;
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
 * Test class for the DiseasResource REST controller.
 *
 * @see DiseasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiasApp.class)
public class DiseasResourceIntTest {

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
    private DiseasRepository diseasRepository;

    @Autowired
    private DiseasMapper diseasMapper;

    @Autowired
    private DiseasService diseasService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDiseasMockMvc;

    private Diseas diseas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiseasResource diseasResource = new DiseasResource(diseasService);
        this.restDiseasMockMvc = MockMvcBuilders.standaloneSetup(diseasResource)
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
    public static Diseas createEntity(EntityManager em) {
        Diseas diseas = new Diseas()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .parentCode(DEFAULT_PARENT_CODE)
            .nodeCount(DEFAULT_NODE_COUNT)
            .additionalInfo(DEFAULT_ADDITIONAL_INFO);
        return diseas;
    }

    @Before
    public void initTest() {
        diseas = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiseas() throws Exception {
        int databaseSizeBeforeCreate = diseasRepository.findAll().size();

        // Create the Diseas
        DiseasDTO diseasDTO = diseasMapper.toDto(diseas);
        restDiseasMockMvc.perform(post("/api/diseas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diseasDTO)))
            .andExpect(status().isCreated());

        // Validate the Diseas in the database
        List<Diseas> diseasList = diseasRepository.findAll();
        assertThat(diseasList).hasSize(databaseSizeBeforeCreate + 1);
        Diseas testDiseas = diseasList.get(diseasList.size() - 1);
        assertThat(testDiseas.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDiseas.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDiseas.getParentCode()).isEqualTo(DEFAULT_PARENT_CODE);
        assertThat(testDiseas.getNodeCount()).isEqualTo(DEFAULT_NODE_COUNT);
        assertThat(testDiseas.getAdditionalInfo()).isEqualTo(DEFAULT_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    public void createDiseasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diseasRepository.findAll().size();

        // Create the Diseas with an existing ID
        diseas.setId(1L);
        DiseasDTO diseasDTO = diseasMapper.toDto(diseas);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiseasMockMvc.perform(post("/api/diseas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diseasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Diseas in the database
        List<Diseas> diseasList = diseasRepository.findAll();
        assertThat(diseasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = diseasRepository.findAll().size();
        // set the field null
        diseas.setName(null);

        // Create the Diseas, which fails.
        DiseasDTO diseasDTO = diseasMapper.toDto(diseas);

        restDiseasMockMvc.perform(post("/api/diseas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diseasDTO)))
            .andExpect(status().isBadRequest());

        List<Diseas> diseasList = diseasRepository.findAll();
        assertThat(diseasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = diseasRepository.findAll().size();
        // set the field null
        diseas.setCode(null);

        // Create the Diseas, which fails.
        DiseasDTO diseasDTO = diseasMapper.toDto(diseas);

        restDiseasMockMvc.perform(post("/api/diseas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diseasDTO)))
            .andExpect(status().isBadRequest());

        List<Diseas> diseasList = diseasRepository.findAll();
        assertThat(diseasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNodeCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = diseasRepository.findAll().size();
        // set the field null
        diseas.setNodeCount(null);

        // Create the Diseas, which fails.
        DiseasDTO diseasDTO = diseasMapper.toDto(diseas);

        restDiseasMockMvc.perform(post("/api/diseas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diseasDTO)))
            .andExpect(status().isBadRequest());

        List<Diseas> diseasList = diseasRepository.findAll();
        assertThat(diseasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiseas() throws Exception {
        // Initialize the database
        diseasRepository.saveAndFlush(diseas);

        // Get all the diseasList
        restDiseasMockMvc.perform(get("/api/diseas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diseas.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].parentCode").value(hasItem(DEFAULT_PARENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].nodeCount").value(hasItem(DEFAULT_NODE_COUNT)))
            .andExpect(jsonPath("$.[*].additionalInfo").value(hasItem(DEFAULT_ADDITIONAL_INFO.toString())));
    }

    @Test
    @Transactional
    public void getDiseas() throws Exception {
        // Initialize the database
        diseasRepository.saveAndFlush(diseas);

        // Get the diseas
        restDiseasMockMvc.perform(get("/api/diseas/{id}", diseas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diseas.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.parentCode").value(DEFAULT_PARENT_CODE.toString()))
            .andExpect(jsonPath("$.nodeCount").value(DEFAULT_NODE_COUNT))
            .andExpect(jsonPath("$.additionalInfo").value(DEFAULT_ADDITIONAL_INFO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiseas() throws Exception {
        // Get the diseas
        restDiseasMockMvc.perform(get("/api/diseas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiseas() throws Exception {
        // Initialize the database
        diseasRepository.saveAndFlush(diseas);
        int databaseSizeBeforeUpdate = diseasRepository.findAll().size();

        // Update the diseas
        Diseas updatedDiseas = diseasRepository.findOne(diseas.getId());
        // Disconnect from session so that the updates on updatedDiseas are not directly saved in db
        em.detach(updatedDiseas);
        updatedDiseas
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .parentCode(UPDATED_PARENT_CODE)
            .nodeCount(UPDATED_NODE_COUNT)
            .additionalInfo(UPDATED_ADDITIONAL_INFO);
        DiseasDTO diseasDTO = diseasMapper.toDto(updatedDiseas);

        restDiseasMockMvc.perform(put("/api/diseas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diseasDTO)))
            .andExpect(status().isOk());

        // Validate the Diseas in the database
        List<Diseas> diseasList = diseasRepository.findAll();
        assertThat(diseasList).hasSize(databaseSizeBeforeUpdate);
        Diseas testDiseas = diseasList.get(diseasList.size() - 1);
        assertThat(testDiseas.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDiseas.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDiseas.getParentCode()).isEqualTo(UPDATED_PARENT_CODE);
        assertThat(testDiseas.getNodeCount()).isEqualTo(UPDATED_NODE_COUNT);
        assertThat(testDiseas.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingDiseas() throws Exception {
        int databaseSizeBeforeUpdate = diseasRepository.findAll().size();

        // Create the Diseas
        DiseasDTO diseasDTO = diseasMapper.toDto(diseas);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDiseasMockMvc.perform(put("/api/diseas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diseasDTO)))
            .andExpect(status().isCreated());

        // Validate the Diseas in the database
        List<Diseas> diseasList = diseasRepository.findAll();
        assertThat(diseasList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDiseas() throws Exception {
        // Initialize the database
        diseasRepository.saveAndFlush(diseas);
        int databaseSizeBeforeDelete = diseasRepository.findAll().size();

        // Get the diseas
        restDiseasMockMvc.perform(delete("/api/diseas/{id}", diseas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Diseas> diseasList = diseasRepository.findAll();
        assertThat(diseasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diseas.class);
        Diseas diseas1 = new Diseas();
        diseas1.setId(1L);
        Diseas diseas2 = new Diseas();
        diseas2.setId(diseas1.getId());
        assertThat(diseas1).isEqualTo(diseas2);
        diseas2.setId(2L);
        assertThat(diseas1).isNotEqualTo(diseas2);
        diseas1.setId(null);
        assertThat(diseas1).isNotEqualTo(diseas2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiseasDTO.class);
        DiseasDTO diseasDTO1 = new DiseasDTO();
        diseasDTO1.setId(1L);
        DiseasDTO diseasDTO2 = new DiseasDTO();
        assertThat(diseasDTO1).isNotEqualTo(diseasDTO2);
        diseasDTO2.setId(diseasDTO1.getId());
        assertThat(diseasDTO1).isEqualTo(diseasDTO2);
        diseasDTO2.setId(2L);
        assertThat(diseasDTO1).isNotEqualTo(diseasDTO2);
        diseasDTO1.setId(null);
        assertThat(diseasDTO1).isNotEqualTo(diseasDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(diseasMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(diseasMapper.fromId(null)).isNull();
    }
}
