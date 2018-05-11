package ru.kpfu.web.rest;

import ru.kpfu.MiasApp;

import ru.kpfu.domain.HealthFacility;
import ru.kpfu.repository.HealthFacilityRepository;
import ru.kpfu.service.HealthFacilityService;
import ru.kpfu.service.dto.HealthFacilityDTO;
import ru.kpfu.service.mapper.HealthFacilityMapper;
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
 * Test class for the HealthFacilityResource REST controller.
 *
 * @see HealthFacilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiasApp.class)
public class HealthFacilityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private HealthFacilityRepository healthFacilityRepository;

    @Autowired
    private HealthFacilityMapper healthFacilityMapper;

    @Autowired
    private HealthFacilityService healthFacilityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHealthFacilityMockMvc;

    private HealthFacility healthFacility;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HealthFacilityResource healthFacilityResource = new HealthFacilityResource(healthFacilityService);
        this.restHealthFacilityMockMvc = MockMvcBuilders.standaloneSetup(healthFacilityResource)
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
    public static HealthFacility createEntity(EntityManager em) {
        HealthFacility healthFacility = new HealthFacility()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return healthFacility;
    }

    @Before
    public void initTest() {
        healthFacility = createEntity(em);
    }

    @Test
    @Transactional
    public void createHealthFacility() throws Exception {
        int databaseSizeBeforeCreate = healthFacilityRepository.findAll().size();

        // Create the HealthFacility
        HealthFacilityDTO healthFacilityDTO = healthFacilityMapper.toDto(healthFacility);
        restHealthFacilityMockMvc.perform(post("/api/health-facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthFacilityDTO)))
            .andExpect(status().isCreated());

        // Validate the HealthFacility in the database
        List<HealthFacility> healthFacilityList = healthFacilityRepository.findAll();
        assertThat(healthFacilityList).hasSize(databaseSizeBeforeCreate + 1);
        HealthFacility testHealthFacility = healthFacilityList.get(healthFacilityList.size() - 1);
        assertThat(testHealthFacility.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHealthFacility.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testHealthFacility.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createHealthFacilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = healthFacilityRepository.findAll().size();

        // Create the HealthFacility with an existing ID
        healthFacility.setId(1L);
        HealthFacilityDTO healthFacilityDTO = healthFacilityMapper.toDto(healthFacility);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHealthFacilityMockMvc.perform(post("/api/health-facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthFacilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HealthFacility in the database
        List<HealthFacility> healthFacilityList = healthFacilityRepository.findAll();
        assertThat(healthFacilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHealthFacilities() throws Exception {
        // Initialize the database
        healthFacilityRepository.saveAndFlush(healthFacility);

        // Get all the healthFacilityList
        restHealthFacilityMockMvc.perform(get("/api/health-facilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(healthFacility.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getHealthFacility() throws Exception {
        // Initialize the database
        healthFacilityRepository.saveAndFlush(healthFacility);

        // Get the healthFacility
        restHealthFacilityMockMvc.perform(get("/api/health-facilities/{id}", healthFacility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(healthFacility.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHealthFacility() throws Exception {
        // Get the healthFacility
        restHealthFacilityMockMvc.perform(get("/api/health-facilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHealthFacility() throws Exception {
        // Initialize the database
        healthFacilityRepository.saveAndFlush(healthFacility);
        int databaseSizeBeforeUpdate = healthFacilityRepository.findAll().size();

        // Update the healthFacility
        HealthFacility updatedHealthFacility = healthFacilityRepository.findOne(healthFacility.getId());
        // Disconnect from session so that the updates on updatedHealthFacility are not directly saved in db
        em.detach(updatedHealthFacility);
        updatedHealthFacility
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        HealthFacilityDTO healthFacilityDTO = healthFacilityMapper.toDto(updatedHealthFacility);

        restHealthFacilityMockMvc.perform(put("/api/health-facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthFacilityDTO)))
            .andExpect(status().isOk());

        // Validate the HealthFacility in the database
        List<HealthFacility> healthFacilityList = healthFacilityRepository.findAll();
        assertThat(healthFacilityList).hasSize(databaseSizeBeforeUpdate);
        HealthFacility testHealthFacility = healthFacilityList.get(healthFacilityList.size() - 1);
        assertThat(testHealthFacility.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHealthFacility.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testHealthFacility.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingHealthFacility() throws Exception {
        int databaseSizeBeforeUpdate = healthFacilityRepository.findAll().size();

        // Create the HealthFacility
        HealthFacilityDTO healthFacilityDTO = healthFacilityMapper.toDto(healthFacility);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHealthFacilityMockMvc.perform(put("/api/health-facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthFacilityDTO)))
            .andExpect(status().isCreated());

        // Validate the HealthFacility in the database
        List<HealthFacility> healthFacilityList = healthFacilityRepository.findAll();
        assertThat(healthFacilityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHealthFacility() throws Exception {
        // Initialize the database
        healthFacilityRepository.saveAndFlush(healthFacility);
        int databaseSizeBeforeDelete = healthFacilityRepository.findAll().size();

        // Get the healthFacility
        restHealthFacilityMockMvc.perform(delete("/api/health-facilities/{id}", healthFacility.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HealthFacility> healthFacilityList = healthFacilityRepository.findAll();
        assertThat(healthFacilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthFacility.class);
        HealthFacility healthFacility1 = new HealthFacility();
        healthFacility1.setId(1L);
        HealthFacility healthFacility2 = new HealthFacility();
        healthFacility2.setId(healthFacility1.getId());
        assertThat(healthFacility1).isEqualTo(healthFacility2);
        healthFacility2.setId(2L);
        assertThat(healthFacility1).isNotEqualTo(healthFacility2);
        healthFacility1.setId(null);
        assertThat(healthFacility1).isNotEqualTo(healthFacility2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthFacilityDTO.class);
        HealthFacilityDTO healthFacilityDTO1 = new HealthFacilityDTO();
        healthFacilityDTO1.setId(1L);
        HealthFacilityDTO healthFacilityDTO2 = new HealthFacilityDTO();
        assertThat(healthFacilityDTO1).isNotEqualTo(healthFacilityDTO2);
        healthFacilityDTO2.setId(healthFacilityDTO1.getId());
        assertThat(healthFacilityDTO1).isEqualTo(healthFacilityDTO2);
        healthFacilityDTO2.setId(2L);
        assertThat(healthFacilityDTO1).isNotEqualTo(healthFacilityDTO2);
        healthFacilityDTO1.setId(null);
        assertThat(healthFacilityDTO1).isNotEqualTo(healthFacilityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(healthFacilityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(healthFacilityMapper.fromId(null)).isNull();
    }
}
