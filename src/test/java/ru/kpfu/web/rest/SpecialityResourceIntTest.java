package ru.kpfu.web.rest;

import ru.kpfu.MiasApp;

import ru.kpfu.domain.Speciality;
import ru.kpfu.repository.SpecialityRepository;
import ru.kpfu.service.SpecialityService;
import ru.kpfu.service.dto.SpecialityDTO;
import ru.kpfu.service.mapper.SpecialityMapper;
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
 * Test class for the SpecialityResource REST controller.
 *
 * @see SpecialityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiasApp.class)
public class SpecialityResourceIntTest {

    private static final String DEFAULT_SPECIALITY = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALITY = "BBBBBBBBBB";

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private SpecialityMapper specialityMapper;

    @Autowired
    private SpecialityService specialityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSpecialityMockMvc;

    private Speciality speciality;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpecialityResource specialityResource = new SpecialityResource(specialityService);
        this.restSpecialityMockMvc = MockMvcBuilders.standaloneSetup(specialityResource)
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
    public static Speciality createEntity(EntityManager em) {
        Speciality speciality = new Speciality()
            .speciality(DEFAULT_SPECIALITY);
        return speciality;
    }

    @Before
    public void initTest() {
        speciality = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpeciality() throws Exception {
        int databaseSizeBeforeCreate = specialityRepository.findAll().size();

        // Create the Speciality
        SpecialityDTO specialityDTO = specialityMapper.toDto(speciality);
        restSpecialityMockMvc.perform(post("/api/specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialityDTO)))
            .andExpect(status().isCreated());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeCreate + 1);
        Speciality testSpeciality = specialityList.get(specialityList.size() - 1);
        assertThat(testSpeciality.getSpeciality()).isEqualTo(DEFAULT_SPECIALITY);
    }

    @Test
    @Transactional
    public void createSpecialityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialityRepository.findAll().size();

        // Create the Speciality with an existing ID
        speciality.setId(1L);
        SpecialityDTO specialityDTO = specialityMapper.toDto(speciality);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialityMockMvc.perform(post("/api/specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSpecialities() throws Exception {
        // Initialize the database
        specialityRepository.saveAndFlush(speciality);

        // Get all the specialityList
        restSpecialityMockMvc.perform(get("/api/specialities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speciality.getId().intValue())))
            .andExpect(jsonPath("$.[*].speciality").value(hasItem(DEFAULT_SPECIALITY.toString())));
    }

    @Test
    @Transactional
    public void getSpeciality() throws Exception {
        // Initialize the database
        specialityRepository.saveAndFlush(speciality);

        // Get the speciality
        restSpecialityMockMvc.perform(get("/api/specialities/{id}", speciality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(speciality.getId().intValue()))
            .andExpect(jsonPath("$.speciality").value(DEFAULT_SPECIALITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSpeciality() throws Exception {
        // Get the speciality
        restSpecialityMockMvc.perform(get("/api/specialities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpeciality() throws Exception {
        // Initialize the database
        specialityRepository.saveAndFlush(speciality);
        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();

        // Update the speciality
        Speciality updatedSpeciality = specialityRepository.findOne(speciality.getId());
        // Disconnect from session so that the updates on updatedSpeciality are not directly saved in db
        em.detach(updatedSpeciality);
        updatedSpeciality
            .speciality(UPDATED_SPECIALITY);
        SpecialityDTO specialityDTO = specialityMapper.toDto(updatedSpeciality);

        restSpecialityMockMvc.perform(put("/api/specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialityDTO)))
            .andExpect(status().isOk());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);
        Speciality testSpeciality = specialityList.get(specialityList.size() - 1);
        assertThat(testSpeciality.getSpeciality()).isEqualTo(UPDATED_SPECIALITY);
    }

    @Test
    @Transactional
    public void updateNonExistingSpeciality() throws Exception {
        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();

        // Create the Speciality
        SpecialityDTO specialityDTO = specialityMapper.toDto(speciality);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSpecialityMockMvc.perform(put("/api/specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialityDTO)))
            .andExpect(status().isCreated());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSpeciality() throws Exception {
        // Initialize the database
        specialityRepository.saveAndFlush(speciality);
        int databaseSizeBeforeDelete = specialityRepository.findAll().size();

        // Get the speciality
        restSpecialityMockMvc.perform(delete("/api/specialities/{id}", speciality.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Speciality.class);
        Speciality speciality1 = new Speciality();
        speciality1.setId(1L);
        Speciality speciality2 = new Speciality();
        speciality2.setId(speciality1.getId());
        assertThat(speciality1).isEqualTo(speciality2);
        speciality2.setId(2L);
        assertThat(speciality1).isNotEqualTo(speciality2);
        speciality1.setId(null);
        assertThat(speciality1).isNotEqualTo(speciality2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialityDTO.class);
        SpecialityDTO specialityDTO1 = new SpecialityDTO();
        specialityDTO1.setId(1L);
        SpecialityDTO specialityDTO2 = new SpecialityDTO();
        assertThat(specialityDTO1).isNotEqualTo(specialityDTO2);
        specialityDTO2.setId(specialityDTO1.getId());
        assertThat(specialityDTO1).isEqualTo(specialityDTO2);
        specialityDTO2.setId(2L);
        assertThat(specialityDTO1).isNotEqualTo(specialityDTO2);
        specialityDTO1.setId(null);
        assertThat(specialityDTO1).isNotEqualTo(specialityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(specialityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(specialityMapper.fromId(null)).isNull();
    }
}
