package ru.kpfu.web.rest;

import ru.kpfu.MiasApp;

import ru.kpfu.domain.Patient;
import ru.kpfu.repository.PatientRepository;
import ru.kpfu.service.PatientService;
import ru.kpfu.service.dto.PatientDTO;
import ru.kpfu.service.mapper.PatientMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static ru.kpfu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.kpfu.domain.enumeration.Gender;
/**
 * Test class for the PatientResource REST controller.
 *
 * @see PatientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiasApp.class)
public class PatientResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATRONYMIC = "AAAAAAAAAA";
    private static final String UPDATED_PATRONYMIC = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DAY = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_RP = 1;
    private static final Integer UPDATED_RP = 2;

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PatientService patientService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPatientMockMvc;

    private Patient patient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientResource patientResource = new PatientResource(patientService);
        this.restPatientMockMvc = MockMvcBuilders.standaloneSetup(patientResource)
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
    public static Patient createEntity(EntityManager em) {
        Patient patient = new Patient()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .patronymic(DEFAULT_PATRONYMIC)
            .birthDay(DEFAULT_BIRTH_DAY)
            .rp(DEFAULT_RP)
            .phone(DEFAULT_PHONE)
            .gender(DEFAULT_GENDER);
        return patient;
    }

    @Before
    public void initTest() {
        patient = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPatient.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPatient.getPatronymic()).isEqualTo(DEFAULT_PATRONYMIC);
        assertThat(testPatient.getBirthDay()).isEqualTo(DEFAULT_BIRTH_DAY);
        assertThat(testPatient.getRp()).isEqualTo(DEFAULT_RP);
        assertThat(testPatient.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPatient.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    public void createPatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient with an existing ID
        patient.setId(1L);
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList
        restPatientMockMvc.perform(get("/api/patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].patronymic").value(hasItem(DEFAULT_PATRONYMIC.toString())))
            .andExpect(jsonPath("$.[*].birthDay").value(hasItem(DEFAULT_BIRTH_DAY.toString())))
            .andExpect(jsonPath("$.[*].rp").value(hasItem(DEFAULT_RP)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));
    }

    @Test
    @Transactional
    public void getPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patient.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.patronymic").value(DEFAULT_PATRONYMIC.toString()))
            .andExpect(jsonPath("$.birthDay").value(DEFAULT_BIRTH_DAY.toString()))
            .andExpect(jsonPath("$.rp").value(DEFAULT_RP))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        Patient updatedPatient = patientRepository.findOne(patient.getId());
        // Disconnect from session so that the updates on updatedPatient are not directly saved in db
        em.detach(updatedPatient);
        updatedPatient
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .patronymic(UPDATED_PATRONYMIC)
            .birthDay(UPDATED_BIRTH_DAY)
            .rp(UPDATED_RP)
            .phone(UPDATED_PHONE)
            .gender(UPDATED_GENDER);
        PatientDTO patientDTO = patientMapper.toDto(updatedPatient);

        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPatient.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPatient.getPatronymic()).isEqualTo(UPDATED_PATRONYMIC);
        assertThat(testPatient.getBirthDay()).isEqualTo(UPDATED_BIRTH_DAY);
        assertThat(testPatient.getRp()).isEqualTo(UPDATED_RP);
        assertThat(testPatient.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPatient.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void updateNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);
        int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Get the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}", patient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Patient.class);
        Patient patient1 = new Patient();
        patient1.setId(1L);
        Patient patient2 = new Patient();
        patient2.setId(patient1.getId());
        assertThat(patient1).isEqualTo(patient2);
        patient2.setId(2L);
        assertThat(patient1).isNotEqualTo(patient2);
        patient1.setId(null);
        assertThat(patient1).isNotEqualTo(patient2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientDTO.class);
        PatientDTO patientDTO1 = new PatientDTO();
        patientDTO1.setId(1L);
        PatientDTO patientDTO2 = new PatientDTO();
        assertThat(patientDTO1).isNotEqualTo(patientDTO2);
        patientDTO2.setId(patientDTO1.getId());
        assertThat(patientDTO1).isEqualTo(patientDTO2);
        patientDTO2.setId(2L);
        assertThat(patientDTO1).isNotEqualTo(patientDTO2);
        patientDTO1.setId(null);
        assertThat(patientDTO1).isNotEqualTo(patientDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(patientMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(patientMapper.fromId(null)).isNull();
    }
}