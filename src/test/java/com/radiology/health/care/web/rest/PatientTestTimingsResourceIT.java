package com.radiology.health.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.care.IntegrationTest;
import com.radiology.health.care.domain.PatientInfo;
import com.radiology.health.care.domain.PatientTestTimings;
import com.radiology.health.care.domain.Room;
import com.radiology.health.care.domain.TestCategories;
import com.radiology.health.care.repository.PatientInfoRepository;
import com.radiology.health.care.repository.PatientTestTimingsRepository;
import com.radiology.health.care.repository.TestCategoriesRepository;
import com.radiology.health.care.service.dto.PatientTestTimingsDTO;
import com.radiology.health.care.service.mapper.PatientTestTimingsMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PatientTestTimingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PatientTestTimingsResourceIT {

    private static final LocalDate DEFAULT_TEST_TIMINGS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TEST_TIMINGS = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TEST_TIMINGS = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITY = "BBBBBBBBBB";

    private static final String DEFAULT_CLINICAL_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_CLINICAL_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_SPCL_INSTRUCTION = "AAAAAAAAAA";
    private static final String UPDATED_SPCL_INSTRUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";
    private static final String ENTITY_API_URL = "/api/patient-test-timings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PatientTestTimingsRepository patientTestTimingsRepository;

    @Autowired
    private PatientTestTimingsMapper patientTestTimingsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientTestTimingsMockMvc;

    private PatientTestTimings patientTestTimings;

    @Autowired
    private PatientInfoRepository patientInfoRepository;

    @Autowired
    private TestCategoriesRepository testCategoriesRepository;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientTestTimings createEntity(EntityManager em) {
        PatientTestTimings patientTestTimings = new PatientTestTimings()
            .testTimings(DEFAULT_TEST_TIMINGS)
            .priority(DEFAULT_PRIORITY)
            .clinicalNote(DEFAULT_CLINICAL_NOTE)
            .spclInstruction(DEFAULT_SPCL_INSTRUCTION)
            .status(DEFAULT_STATUS);
        // Add required entity
        PatientInfo patientInfo;
        if (TestUtil.findAll(em, PatientInfo.class).isEmpty()) {
            patientInfo = PatientInfoResourceIT.createEntity(em);
            em.persist(patientInfo);
            em.flush();
        } else {
            patientInfo = TestUtil.findAll(em, PatientInfo.class).get(0);
        }
        // Add required entity
        TestCategories testCategories;
        if (TestUtil.findAll(em, TestCategories.class).isEmpty()) {
            testCategories = TestCategoriesResourceIT.createEntity(em);
            em.persist(testCategories);
            em.flush();
        } else {
            testCategories = TestUtil.findAll(em, TestCategories.class).get(0);
        }
        patientTestTimings.setPatientInfo(patientInfo);
        patientTestTimings.setTestCategories(testCategories);
        return patientTestTimings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientTestTimings createUpdatedEntity(EntityManager em) {
        PatientTestTimings patientTestTimings = new PatientTestTimings()
            .testTimings(UPDATED_TEST_TIMINGS)
            .priority(UPDATED_PRIORITY)
            .clinicalNote(UPDATED_CLINICAL_NOTE)
            .spclInstruction(UPDATED_SPCL_INSTRUCTION)
            .status(UPDATED_STATUS);
        // Add required entity
        PatientInfo patientInfo;
        if (TestUtil.findAll(em, PatientInfo.class).isEmpty()) {
            patientInfo = PatientInfoResourceIT.createUpdatedEntity(em);
            em.persist(patientInfo);
            em.flush();
        } else {
            patientInfo = TestUtil.findAll(em, PatientInfo.class).get(0);
        }
        // Add required entity
        TestCategories testCategories;
        if (TestUtil.findAll(em, TestCategories.class).isEmpty()) {
            testCategories = TestCategoriesResourceIT.createUpdatedEntity(em);
            em.persist(testCategories);
            em.flush();
        } else {
            testCategories = TestUtil.findAll(em, TestCategories.class).get(0);
        }
        patientTestTimings.setPatientInfo(patientInfo);
        patientTestTimings.setTestCategories(testCategories);
        return patientTestTimings;
    }

    @BeforeEach
    public void initTest() {
        patientTestTimings = createEntity(em);
    }

    @Test
    @Transactional
    void createPatientTestTimings() throws Exception {
        // Create a TestCategories entity and save it
        PatientInfo patientInfo = PatientInfoResourceIT.createEntity(em);
        patientInfo = patientInfoRepository.save(patientInfo);
        TestCategories testCategories = TestCategoriesResourceIT.createEntity(em);
        testCategories = testCategoriesRepository.save(testCategories);

        // Create a PatientTestTimings entity and set its attributes
        PatientTestTimings patientTestTimings = new PatientTestTimings();
        patientTestTimings.setTestCategories(testCategories); // Set the TestCategories
        patientTestTimings.setPatientInfo(patientInfo);
        patientTestTimings.setTestTimings(DEFAULT_TEST_TIMINGS);
        patientTestTimings.setPriority(DEFAULT_PRIORITY);
        patientTestTimings.setClinicalNote(DEFAULT_CLINICAL_NOTE);
        patientTestTimings.setSpclInstruction(DEFAULT_SPCL_INSTRUCTION);
        patientTestTimings.setStatus(DEFAULT_STATUS);
        // Set other attributes as needed

        int databaseSizeBeforeCreate = patientTestTimingsRepository.findAll().size();

        // Save the PatientTestTimings entity
        patientTestTimings = patientTestTimingsRepository.save(patientTestTimings);

        // Validate the creation
        assertThat(patientTestTimings.getId()).isNotNull();

        // Validate the size of the database after creation
        List<PatientTestTimings> patientTestTimingsList = patientTestTimingsRepository.findAll();
        assertThat(patientTestTimingsList).hasSize(databaseSizeBeforeCreate + 1);
    }

    @Test
    @Transactional
    void createPatientTestTimingsWithExistingId() throws Exception {
        // Create the PatientTestTimings with an existing ID
        patientTestTimings.setId(1L);
        PatientTestTimingsDTO patientTestTimingsDTO = patientTestTimingsMapper.toDto(patientTestTimings);

        int databaseSizeBeforeCreate = patientTestTimingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientTestTimingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientTestTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientTestTimings in the database
        List<PatientTestTimings> patientTestTimingsList = patientTestTimingsRepository.findAll();
        assertThat(patientTestTimingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPatientTestTimings() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList
        restPatientTestTimingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientTestTimings.getId().intValue())))
            .andExpect(jsonPath("$.[*].testTimings").value(hasItem(DEFAULT_TEST_TIMINGS.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].clinicalNote").value(hasItem(DEFAULT_CLINICAL_NOTE)))
            .andExpect(jsonPath("$.[*].spclInstruction").value(hasItem(DEFAULT_SPCL_INSTRUCTION)));
    }

    @Test
    @Transactional
    void getPatientTestTimings() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get the patientTestTimings
        restPatientTestTimingsMockMvc
            .perform(get(ENTITY_API_URL_ID, patientTestTimings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patientTestTimings.getId().intValue()))
            .andExpect(jsonPath("$.testTimings").value(DEFAULT_TEST_TIMINGS.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.clinicalNote").value(DEFAULT_CLINICAL_NOTE))
            .andExpect(jsonPath("$.spclInstruction").value(DEFAULT_SPCL_INSTRUCTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getPatientTestTimingsByIdFiltering() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        Long id = patientTestTimings.getId();

        defaultPatientTestTimingsShouldBeFound("id.equals=" + id);
        defaultPatientTestTimingsShouldNotBeFound("id.notEquals=" + id);

        defaultPatientTestTimingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPatientTestTimingsShouldNotBeFound("id.greaterThan=" + id);

        defaultPatientTestTimingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPatientTestTimingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByTestTimingsIsEqualToSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where testTimings equals to DEFAULT_TEST_TIMINGS
        defaultPatientTestTimingsShouldBeFound("testTimings.equals=" + DEFAULT_TEST_TIMINGS);

        // Get all the patientTestTimingsList where testTimings equals to UPDATED_TEST_TIMINGS
        defaultPatientTestTimingsShouldNotBeFound("testTimings.equals=" + UPDATED_TEST_TIMINGS);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByTestTimingsIsInShouldWork() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where testTimings in DEFAULT_TEST_TIMINGS or UPDATED_TEST_TIMINGS
        defaultPatientTestTimingsShouldBeFound("testTimings.in=" + DEFAULT_TEST_TIMINGS + "," + UPDATED_TEST_TIMINGS);

        // Get all the patientTestTimingsList where testTimings equals to UPDATED_TEST_TIMINGS
        defaultPatientTestTimingsShouldNotBeFound("testTimings.in=" + UPDATED_TEST_TIMINGS);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByTestTimingsIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where testTimings is not null
        defaultPatientTestTimingsShouldBeFound("testTimings.specified=true");

        // Get all the patientTestTimingsList where testTimings is null
        defaultPatientTestTimingsShouldNotBeFound("testTimings.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByTestTimingsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where testTimings is greater than or equal to DEFAULT_TEST_TIMINGS
        defaultPatientTestTimingsShouldBeFound("testTimings.greaterThanOrEqual=" + DEFAULT_TEST_TIMINGS);

        // Get all the patientTestTimingsList where testTimings is greater than or equal to UPDATED_TEST_TIMINGS
        defaultPatientTestTimingsShouldNotBeFound("testTimings.greaterThanOrEqual=" + UPDATED_TEST_TIMINGS);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByTestTimingsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where testTimings is less than or equal to DEFAULT_TEST_TIMINGS
        defaultPatientTestTimingsShouldBeFound("testTimings.lessThanOrEqual=" + DEFAULT_TEST_TIMINGS);

        // Get all the patientTestTimingsList where testTimings is less than or equal to SMALLER_TEST_TIMINGS
        defaultPatientTestTimingsShouldNotBeFound("testTimings.lessThanOrEqual=" + SMALLER_TEST_TIMINGS);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByTestTimingsIsLessThanSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where testTimings is less than DEFAULT_TEST_TIMINGS
        defaultPatientTestTimingsShouldNotBeFound("testTimings.lessThan=" + DEFAULT_TEST_TIMINGS);

        // Get all the patientTestTimingsList where testTimings is less than UPDATED_TEST_TIMINGS
        defaultPatientTestTimingsShouldBeFound("testTimings.lessThan=" + UPDATED_TEST_TIMINGS);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByTestTimingsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where testTimings is greater than DEFAULT_TEST_TIMINGS
        defaultPatientTestTimingsShouldNotBeFound("testTimings.greaterThan=" + DEFAULT_TEST_TIMINGS);

        // Get all the patientTestTimingsList where testTimings is greater than SMALLER_TEST_TIMINGS
        defaultPatientTestTimingsShouldBeFound("testTimings.greaterThan=" + SMALLER_TEST_TIMINGS);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where priority equals to DEFAULT_PRIORITY
        defaultPatientTestTimingsShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the patientTestTimingsList where priority equals to UPDATED_PRIORITY
        defaultPatientTestTimingsShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultPatientTestTimingsShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the patientTestTimingsList where priority equals to UPDATED_PRIORITY
        defaultPatientTestTimingsShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where priority is not null
        defaultPatientTestTimingsShouldBeFound("priority.specified=true");

        // Get all the patientTestTimingsList where priority is null
        defaultPatientTestTimingsShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByPriorityContainsSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where priority contains DEFAULT_PRIORITY
        defaultPatientTestTimingsShouldBeFound("priority.contains=" + DEFAULT_PRIORITY);

        // Get all the patientTestTimingsList where priority contains UPDATED_PRIORITY
        defaultPatientTestTimingsShouldNotBeFound("priority.contains=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByPriorityNotContainsSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where priority does not contain DEFAULT_PRIORITY
        defaultPatientTestTimingsShouldNotBeFound("priority.doesNotContain=" + DEFAULT_PRIORITY);

        // Get all the patientTestTimingsList where priority does not contain UPDATED_PRIORITY
        defaultPatientTestTimingsShouldBeFound("priority.doesNotContain=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByClinicalNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where clinicalNote equals to DEFAULT_CLINICAL_NOTE
        defaultPatientTestTimingsShouldBeFound("clinicalNote.equals=" + DEFAULT_CLINICAL_NOTE);

        // Get all the patientTestTimingsList where clinicalNote equals to UPDATED_CLINICAL_NOTE
        defaultPatientTestTimingsShouldNotBeFound("clinicalNote.equals=" + UPDATED_CLINICAL_NOTE);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByClinicalNoteIsInShouldWork() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where clinicalNote in DEFAULT_CLINICAL_NOTE or UPDATED_CLINICAL_NOTE
        defaultPatientTestTimingsShouldBeFound("clinicalNote.in=" + DEFAULT_CLINICAL_NOTE + "," + UPDATED_CLINICAL_NOTE);

        // Get all the patientTestTimingsList where clinicalNote equals to UPDATED_CLINICAL_NOTE
        defaultPatientTestTimingsShouldNotBeFound("clinicalNote.in=" + UPDATED_CLINICAL_NOTE);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByClinicalNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where clinicalNote is not null
        defaultPatientTestTimingsShouldBeFound("clinicalNote.specified=true");

        // Get all the patientTestTimingsList where clinicalNote is null
        defaultPatientTestTimingsShouldNotBeFound("clinicalNote.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByClinicalNoteContainsSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where clinicalNote contains DEFAULT_CLINICAL_NOTE
        defaultPatientTestTimingsShouldBeFound("clinicalNote.contains=" + DEFAULT_CLINICAL_NOTE);

        // Get all the patientTestTimingsList where clinicalNote contains UPDATED_CLINICAL_NOTE
        defaultPatientTestTimingsShouldNotBeFound("clinicalNote.contains=" + UPDATED_CLINICAL_NOTE);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByClinicalNoteNotContainsSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where clinicalNote does not contain DEFAULT_CLINICAL_NOTE
        defaultPatientTestTimingsShouldNotBeFound("clinicalNote.doesNotContain=" + DEFAULT_CLINICAL_NOTE);

        // Get all the patientTestTimingsList where clinicalNote does not contain UPDATED_CLINICAL_NOTE
        defaultPatientTestTimingsShouldBeFound("clinicalNote.doesNotContain=" + UPDATED_CLINICAL_NOTE);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsBySpclInstructionIsEqualToSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where spclInstruction equals to DEFAULT_SPCL_INSTRUCTION
        defaultPatientTestTimingsShouldBeFound("spclInstruction.equals=" + DEFAULT_SPCL_INSTRUCTION);

        // Get all the patientTestTimingsList where spclInstruction equals to UPDATED_SPCL_INSTRUCTION
        defaultPatientTestTimingsShouldNotBeFound("spclInstruction.equals=" + UPDATED_SPCL_INSTRUCTION);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsBySpclInstructionIsInShouldWork() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where spclInstruction in DEFAULT_SPCL_INSTRUCTION or UPDATED_SPCL_INSTRUCTION
        defaultPatientTestTimingsShouldBeFound("spclInstruction.in=" + DEFAULT_SPCL_INSTRUCTION + "," + UPDATED_SPCL_INSTRUCTION);

        // Get all the patientTestTimingsList where spclInstruction equals to UPDATED_SPCL_INSTRUCTION
        defaultPatientTestTimingsShouldNotBeFound("spclInstruction.in=" + UPDATED_SPCL_INSTRUCTION);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsBySpclInstructionIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where spclInstruction is not null
        defaultPatientTestTimingsShouldBeFound("spclInstruction.specified=true");

        // Get all the patientTestTimingsList where spclInstruction is null
        defaultPatientTestTimingsShouldNotBeFound("spclInstruction.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsBySpclInstructionContainsSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where spclInstruction contains DEFAULT_SPCL_INSTRUCTION
        defaultPatientTestTimingsShouldBeFound("spclInstruction.contains=" + DEFAULT_SPCL_INSTRUCTION);

        // Get all the patientTestTimingsList where spclInstruction contains UPDATED_SPCL_INSTRUCTION
        defaultPatientTestTimingsShouldNotBeFound("spclInstruction.contains=" + UPDATED_SPCL_INSTRUCTION);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsBySpclInstructionNotContainsSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where spclInstruction does not contain DEFAULT_SPCL_INSTRUCTION
        defaultPatientTestTimingsShouldNotBeFound("spclInstruction.doesNotContain=" + DEFAULT_SPCL_INSTRUCTION);

        // Get all the patientTestTimingsList where spclInstruction does not contain UPDATED_SPCL_INSTRUCTION
        defaultPatientTestTimingsShouldBeFound("spclInstruction.doesNotContain=" + UPDATED_SPCL_INSTRUCTION);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where status equals to DEFAULT_STATUS
        defaultPatientTestTimingsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the patientTestTimingsList where status equals to UPDATED_STATUS
        defaultPatientTestTimingsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPatientTestTimingsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the patientTestTimingsList where status equals to UPDATED_STATUS
        defaultPatientTestTimingsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsBySStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where status is not null
        defaultPatientTestTimingsShouldBeFound("status.specified=true");

        // Get all the patientTestTimingsList where status is null
        defaultPatientTestTimingsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByStatusContainsSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where status contains DEFAULT_STATUS
        defaultPatientTestTimingsShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the patientTestTimingsList where status contains UPDATED_STATUS
        defaultPatientTestTimingsShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        // Get all the patientTestTimingsList where status does not contain DEFAULT_STATUS
        defaultPatientTestTimingsShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the patientTestTimingsList where status does not contain UPDATED_STATUS
        defaultPatientTestTimingsShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByPatientInfoIsEqualToSomething() throws Exception {
        PatientInfo patientInfo;
        if (TestUtil.findAll(em, PatientInfo.class).isEmpty()) {
            patientTestTimingsRepository.saveAndFlush(patientTestTimings);
            patientInfo = PatientInfoResourceIT.createEntity(em);
        } else {
            patientInfo = TestUtil.findAll(em, PatientInfo.class).get(0);
        }
        em.persist(patientInfo);
        em.flush();
        patientTestTimings.setPatientInfo(patientInfo);
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);
        Long patientInfoId = patientInfo.getId();
        // Get all the patientTestTimingsList where patientInfo equals to patientInfoId
        defaultPatientTestTimingsShouldBeFound("patientInfoId.equals=" + patientInfoId);

        // Get all the patientTestTimingsList where patientInfo equals to (patientInfoId + 1)
        defaultPatientTestTimingsShouldNotBeFound("patientInfoId.equals=" + (patientInfoId + 1));
    }

    @Test
    @Transactional
    void getAllPatientTestTimingsByTestCategoriesIsEqualToSomething() throws Exception {
        TestCategories testCategories;
        if (TestUtil.findAll(em, TestCategories.class).isEmpty()) {
            patientTestTimingsRepository.saveAndFlush(patientTestTimings);
            testCategories = TestCategoriesResourceIT.createEntity(em);
        } else {
            testCategories = TestUtil.findAll(em, TestCategories.class).get(0);
        }
        em.persist(testCategories);
        em.flush();
        patientTestTimings.setTestCategories(testCategories);
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);
        Long testCategoriesId = testCategories.getId();
        // Get all the patientTestTimingsList where testCategories equals to testCategoriesId
        defaultPatientTestTimingsShouldBeFound("testCategoriesId.equals=" + testCategoriesId);

        // Get all the patientTestTimingsList where testCategories equals to (testCategoriesId + 1)
        defaultPatientTestTimingsShouldNotBeFound("testCategoriesId.equals=" + (testCategoriesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPatientTestTimingsShouldBeFound(String filter) throws Exception {
        restPatientTestTimingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientTestTimings.getId().intValue())))
            .andExpect(jsonPath("$.[*].testTimings").value(hasItem(DEFAULT_TEST_TIMINGS.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].clinicalNote").value(hasItem(DEFAULT_CLINICAL_NOTE)))
            .andExpect(jsonPath("$.[*].spclInstruction").value(hasItem(DEFAULT_SPCL_INSTRUCTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restPatientTestTimingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPatientTestTimingsShouldNotBeFound(String filter) throws Exception {
        restPatientTestTimingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPatientTestTimingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPatientTestTimings() throws Exception {
        // Get the patientTestTimings
        restPatientTestTimingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPatientTestTimings() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        int databaseSizeBeforeUpdate = patientTestTimingsRepository.findAll().size();

        // Update the patientTestTimings
        PatientTestTimings updatedPatientTestTimings = patientTestTimingsRepository.findById(patientTestTimings.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPatientTestTimings are not directly saved in db
        em.detach(updatedPatientTestTimings);
        updatedPatientTestTimings
            .testTimings(UPDATED_TEST_TIMINGS)
            .priority(UPDATED_PRIORITY)
            .clinicalNote(UPDATED_CLINICAL_NOTE)
            .spclInstruction(UPDATED_SPCL_INSTRUCTION)
            .status(UPDATED_STATUS);

        PatientTestTimingsDTO patientTestTimingsDTO = patientTestTimingsMapper.toDto(updatedPatientTestTimings);

        restPatientTestTimingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientTestTimingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientTestTimingsDTO))
            )
            .andExpect(status().isOk());

        // Validate the PatientTestTimings in the database
        List<PatientTestTimings> patientTestTimingsList = patientTestTimingsRepository.findAll();
        assertThat(patientTestTimingsList).hasSize(databaseSizeBeforeUpdate);
        PatientTestTimings testPatientTestTimings = patientTestTimingsList.get(patientTestTimingsList.size() - 1);
        assertThat(testPatientTestTimings.getTestTimings()).isEqualTo(UPDATED_TEST_TIMINGS);
        assertThat(testPatientTestTimings.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPatientTestTimings.getClinicalNote()).isEqualTo(UPDATED_CLINICAL_NOTE);
        assertThat(testPatientTestTimings.getSpclInstruction()).isEqualTo(UPDATED_SPCL_INSTRUCTION);
        assertThat(testPatientTestTimings.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPatientTestTimings() throws Exception {
        int databaseSizeBeforeUpdate = patientTestTimingsRepository.findAll().size();
        patientTestTimings.setId(longCount.incrementAndGet());

        // Create the PatientTestTimings
        PatientTestTimingsDTO patientTestTimingsDTO = patientTestTimingsMapper.toDto(patientTestTimings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientTestTimingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientTestTimingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientTestTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientTestTimings in the database
        List<PatientTestTimings> patientTestTimingsList = patientTestTimingsRepository.findAll();
        assertThat(patientTestTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPatientTestTimings() throws Exception {
        int databaseSizeBeforeUpdate = patientTestTimingsRepository.findAll().size();
        patientTestTimings.setId(longCount.incrementAndGet());

        // Create the PatientTestTimings
        PatientTestTimingsDTO patientTestTimingsDTO = patientTestTimingsMapper.toDto(patientTestTimings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientTestTimingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientTestTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientTestTimings in the database
        List<PatientTestTimings> patientTestTimingsList = patientTestTimingsRepository.findAll();
        assertThat(patientTestTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPatientTestTimings() throws Exception {
        int databaseSizeBeforeUpdate = patientTestTimingsRepository.findAll().size();
        patientTestTimings.setId(longCount.incrementAndGet());

        // Create the PatientTestTimings
        PatientTestTimingsDTO patientTestTimingsDTO = patientTestTimingsMapper.toDto(patientTestTimings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientTestTimingsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientTestTimingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientTestTimings in the database
        List<PatientTestTimings> patientTestTimingsList = patientTestTimingsRepository.findAll();
        assertThat(patientTestTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePatientTestTimingsWithPatch() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        int databaseSizeBeforeUpdate = patientTestTimingsRepository.findAll().size();

        // Update the patientTestTimings using partial update
        PatientTestTimings partialUpdatedPatientTestTimings = new PatientTestTimings();
        partialUpdatedPatientTestTimings.setId(patientTestTimings.getId());

        partialUpdatedPatientTestTimings.spclInstruction(UPDATED_SPCL_INSTRUCTION);

        restPatientTestTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientTestTimings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatientTestTimings))
            )
            .andExpect(status().isOk());

        // Validate the PatientTestTimings in the database
        List<PatientTestTimings> patientTestTimingsList = patientTestTimingsRepository.findAll();
        assertThat(patientTestTimingsList).hasSize(databaseSizeBeforeUpdate);
        PatientTestTimings testPatientTestTimings = patientTestTimingsList.get(patientTestTimingsList.size() - 1);
        assertThat(testPatientTestTimings.getTestTimings()).isEqualTo(DEFAULT_TEST_TIMINGS);
        assertThat(testPatientTestTimings.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testPatientTestTimings.getClinicalNote()).isEqualTo(DEFAULT_CLINICAL_NOTE);
        assertThat(testPatientTestTimings.getSpclInstruction()).isEqualTo(UPDATED_SPCL_INSTRUCTION);
        assertThat(testPatientTestTimings.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePatientTestTimingsWithPatch() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        int databaseSizeBeforeUpdate = patientTestTimingsRepository.findAll().size();

        // Update the patientTestTimings using partial update
        PatientTestTimings partialUpdatedPatientTestTimings = new PatientTestTimings();
        partialUpdatedPatientTestTimings.setId(patientTestTimings.getId());

        partialUpdatedPatientTestTimings
            .testTimings(UPDATED_TEST_TIMINGS)
            .priority(UPDATED_PRIORITY)
            .clinicalNote(UPDATED_CLINICAL_NOTE)
            .spclInstruction(UPDATED_SPCL_INSTRUCTION)
            .status(UPDATED_STATUS);

        restPatientTestTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientTestTimings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatientTestTimings))
            )
            .andExpect(status().isOk());

        // Validate the PatientTestTimings in the database
        List<PatientTestTimings> patientTestTimingsList = patientTestTimingsRepository.findAll();
        assertThat(patientTestTimingsList).hasSize(databaseSizeBeforeUpdate);
        PatientTestTimings testPatientTestTimings = patientTestTimingsList.get(patientTestTimingsList.size() - 1);
        assertThat(testPatientTestTimings.getTestTimings()).isEqualTo(UPDATED_TEST_TIMINGS);
        assertThat(testPatientTestTimings.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPatientTestTimings.getClinicalNote()).isEqualTo(UPDATED_CLINICAL_NOTE);
        assertThat(testPatientTestTimings.getSpclInstruction()).isEqualTo(UPDATED_SPCL_INSTRUCTION);
        assertThat(testPatientTestTimings.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPatientTestTimings() throws Exception {
        int databaseSizeBeforeUpdate = patientTestTimingsRepository.findAll().size();
        patientTestTimings.setId(longCount.incrementAndGet());

        // Create the PatientTestTimings
        PatientTestTimingsDTO patientTestTimingsDTO = patientTestTimingsMapper.toDto(patientTestTimings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientTestTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patientTestTimingsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientTestTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientTestTimings in the database
        List<PatientTestTimings> patientTestTimingsList = patientTestTimingsRepository.findAll();
        assertThat(patientTestTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPatientTestTimings() throws Exception {
        int databaseSizeBeforeUpdate = patientTestTimingsRepository.findAll().size();
        patientTestTimings.setId(longCount.incrementAndGet());

        // Create the PatientTestTimings
        PatientTestTimingsDTO patientTestTimingsDTO = patientTestTimingsMapper.toDto(patientTestTimings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientTestTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientTestTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientTestTimings in the database
        List<PatientTestTimings> patientTestTimingsList = patientTestTimingsRepository.findAll();
        assertThat(patientTestTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPatientTestTimings() throws Exception {
        int databaseSizeBeforeUpdate = patientTestTimingsRepository.findAll().size();
        patientTestTimings.setId(longCount.incrementAndGet());

        // Create the PatientTestTimings
        PatientTestTimingsDTO patientTestTimingsDTO = patientTestTimingsMapper.toDto(patientTestTimings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientTestTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientTestTimingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientTestTimings in the database
        List<PatientTestTimings> patientTestTimingsList = patientTestTimingsRepository.findAll();
        assertThat(patientTestTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePatientTestTimings() throws Exception {
        // Initialize the database
        patientTestTimingsRepository.saveAndFlush(patientTestTimings);

        int databaseSizeBeforeDelete = patientTestTimingsRepository.findAll().size();

        // Delete the patientTestTimings
        restPatientTestTimingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, patientTestTimings.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientTestTimings> patientTestTimingsList = patientTestTimingsRepository.findAll();
        assertThat(patientTestTimingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
