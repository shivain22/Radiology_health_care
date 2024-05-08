package com.radiology.health.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.care.IntegrationTest;
import com.radiology.health.care.domain.Equipment;
import com.radiology.health.care.domain.PatientTestTimings;
import com.radiology.health.care.domain.TestCategories;
import com.radiology.health.care.domain.TestCategories;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.repository.TestCategoriesRepository;
import com.radiology.health.care.service.dto.TestCategoriesDTO;
import com.radiology.health.care.service.mapper.TestCategoriesMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link TestCategoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TestCategoriesResourceIT {

    private static final String DEFAULT_TEST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEST_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TEST_DURATION = 1;
    private static final Integer UPDATED_TEST_DURATION = 2;
    private static final Integer SMALLER_TEST_DURATION = 1 - 1;

    private static final String DEFAULT_PATIENT_REPORT = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_REPORT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/test-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TestCategoriesRepository testCategoriesRepository;

    @Autowired
    private TestCategoriesMapper testCategoriesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTestCategoriesMockMvc;

    private TestCategories testCategories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestCategories createEntity(EntityManager em) {
        TestCategories testCategories = new TestCategories()
            .testName(DEFAULT_TEST_NAME)
            .testDuration(DEFAULT_TEST_DURATION)
            .patientReport(DEFAULT_PATIENT_REPORT);
        // Add required entity
        Equipment equipment;
        if (TestUtil.findAll(em, Equipment.class).isEmpty()) {
            equipment = EquipmentResourceIT.createEntity(em);
            em.persist(equipment);
            em.flush();
        } else {
            equipment = TestUtil.findAll(em, Equipment.class).get(0);
        }
        testCategories.setEquipment(equipment);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        testCategories.setUser(user);
        return testCategories;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestCategories createUpdatedEntity(EntityManager em) {
        TestCategories testCategories = new TestCategories()
            .testName(UPDATED_TEST_NAME)
            .testDuration(UPDATED_TEST_DURATION)
            .patientReport(UPDATED_PATIENT_REPORT);
        // Add required entity
        Equipment equipment;
        if (TestUtil.findAll(em, Equipment.class).isEmpty()) {
            equipment = EquipmentResourceIT.createUpdatedEntity(em);
            em.persist(equipment);
            em.flush();
        } else {
            equipment = TestUtil.findAll(em, Equipment.class).get(0);
        }
        testCategories.setEquipment(equipment);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        testCategories.setUser(user);
        return testCategories;
    }

    @BeforeEach
    public void initTest() {
        testCategories = createEntity(em);
    }

    @Test
    @Transactional
    void createTestCategories() throws Exception {
        int databaseSizeBeforeCreate = testCategoriesRepository.findAll().size();
        // Create the TestCategories
        TestCategoriesDTO testCategoriesDTO = testCategoriesMapper.toDto(testCategories);
        restTestCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testCategoriesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TestCategories in the database
        List<TestCategories> testCategoriesList = testCategoriesRepository.findAll();
        assertThat(testCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        TestCategories testTestCategories = testCategoriesList.get(testCategoriesList.size() - 1);
        assertThat(testTestCategories.getTestName()).isEqualTo(DEFAULT_TEST_NAME);
        assertThat(testTestCategories.getTestDuration()).isEqualTo(DEFAULT_TEST_DURATION);
        assertThat(testTestCategories.getPatientReport()).isEqualTo(DEFAULT_PATIENT_REPORT);
    }

    @Test
    @Transactional
    void createTestCategoriesWithExistingId() throws Exception {
        // Create the TestCategories with an existing ID
        testCategories.setId(1L);
        TestCategoriesDTO testCategoriesDTO = testCategoriesMapper.toDto(testCategories);

        int databaseSizeBeforeCreate = testCategoriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestCategories in the database
        List<TestCategories> testCategoriesList = testCategoriesRepository.findAll();
        assertThat(testCategoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTestNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = testCategoriesRepository.findAll().size();
        // set the field null
        testCategories.setTestName(null);

        // Create the TestCategories, which fails.
        TestCategoriesDTO testCategoriesDTO = testCategoriesMapper.toDto(testCategories);

        restTestCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        List<TestCategories> testCategoriesList = testCategoriesRepository.findAll();
        assertThat(testCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTestCategories() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList
        restTestCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].testName").value(hasItem(DEFAULT_TEST_NAME)))
            .andExpect(jsonPath("$.[*].testDuration").value(hasItem(DEFAULT_TEST_DURATION)))
            .andExpect(jsonPath("$.[*].patientReport").value(hasItem(DEFAULT_PATIENT_REPORT)));
    }

    @Test
    @Transactional
    void getTestCategories() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get the testCategories
        restTestCategoriesMockMvc
            .perform(get(ENTITY_API_URL_ID, testCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(testCategories.getId().intValue()))
            .andExpect(jsonPath("$.testName").value(DEFAULT_TEST_NAME))
            .andExpect(jsonPath("$.testDuration").value(DEFAULT_TEST_DURATION))
            .andExpect(jsonPath("$.patientReport").value(DEFAULT_PATIENT_REPORT));
    }

    @Test
    @Transactional
    void getTestCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        Long id = testCategories.getId();

        defaultTestCategoriesShouldBeFound("id.equals=" + id);
        defaultTestCategoriesShouldNotBeFound("id.notEquals=" + id);

        defaultTestCategoriesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTestCategoriesShouldNotBeFound("id.greaterThan=" + id);

        defaultTestCategoriesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTestCategoriesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByTestNameIsEqualToSomething() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where testName equals to DEFAULT_TEST_NAME
        defaultTestCategoriesShouldBeFound("testName.equals=" + DEFAULT_TEST_NAME);

        // Get all the testCategoriesList where testName equals to UPDATED_TEST_NAME
        defaultTestCategoriesShouldNotBeFound("testName.equals=" + UPDATED_TEST_NAME);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByTestNameIsInShouldWork() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where testName in DEFAULT_TEST_NAME or UPDATED_TEST_NAME
        defaultTestCategoriesShouldBeFound("testName.in=" + DEFAULT_TEST_NAME + "," + UPDATED_TEST_NAME);

        // Get all the testCategoriesList where testName equals to UPDATED_TEST_NAME
        defaultTestCategoriesShouldNotBeFound("testName.in=" + UPDATED_TEST_NAME);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByTestNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where testName is not null
        defaultTestCategoriesShouldBeFound("testName.specified=true");

        // Get all the testCategoriesList where testName is null
        defaultTestCategoriesShouldNotBeFound("testName.specified=false");
    }

    @Test
    @Transactional
    void getAllTestCategoriesByTestNameContainsSomething() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where testName contains DEFAULT_TEST_NAME
        defaultTestCategoriesShouldBeFound("testName.contains=" + DEFAULT_TEST_NAME);

        // Get all the testCategoriesList where testName contains UPDATED_TEST_NAME
        defaultTestCategoriesShouldNotBeFound("testName.contains=" + UPDATED_TEST_NAME);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByTestNameNotContainsSomething() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where testName does not contain DEFAULT_TEST_NAME
        defaultTestCategoriesShouldNotBeFound("testName.doesNotContain=" + DEFAULT_TEST_NAME);

        // Get all the testCategoriesList where testName does not contain UPDATED_TEST_NAME
        defaultTestCategoriesShouldBeFound("testName.doesNotContain=" + UPDATED_TEST_NAME);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByTestDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where testDuration equals to DEFAULT_TEST_DURATION
        defaultTestCategoriesShouldBeFound("testDuration.equals=" + DEFAULT_TEST_DURATION);

        // Get all the testCategoriesList where testDuration equals to UPDATED_TEST_DURATION
        defaultTestCategoriesShouldNotBeFound("testDuration.equals=" + UPDATED_TEST_DURATION);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByTestDurationIsInShouldWork() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where testDuration in DEFAULT_TEST_DURATION or UPDATED_TEST_DURATION
        defaultTestCategoriesShouldBeFound("testDuration.in=" + DEFAULT_TEST_DURATION + "," + UPDATED_TEST_DURATION);

        // Get all the testCategoriesList where testDuration equals to UPDATED_TEST_DURATION
        defaultTestCategoriesShouldNotBeFound("testDuration.in=" + UPDATED_TEST_DURATION);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByTestDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where testDuration is not null
        defaultTestCategoriesShouldBeFound("testDuration.specified=true");

        // Get all the testCategoriesList where testDuration is null
        defaultTestCategoriesShouldNotBeFound("testDuration.specified=false");
    }

    @Test
    @Transactional
    void getAllTestCategoriesByTestDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where testDuration is greater than or equal to DEFAULT_TEST_DURATION
        defaultTestCategoriesShouldBeFound("testDuration.greaterThanOrEqual=" + DEFAULT_TEST_DURATION);

        // Get all the testCategoriesList where testDuration is greater than or equal to UPDATED_TEST_DURATION
        defaultTestCategoriesShouldNotBeFound("testDuration.greaterThanOrEqual=" + UPDATED_TEST_DURATION);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByTestDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where testDuration is less than or equal to DEFAULT_TEST_DURATION
        defaultTestCategoriesShouldBeFound("testDuration.lessThanOrEqual=" + DEFAULT_TEST_DURATION);

        // Get all the testCategoriesList where testDuration is less than or equal to SMALLER_TEST_DURATION
        defaultTestCategoriesShouldNotBeFound("testDuration.lessThanOrEqual=" + SMALLER_TEST_DURATION);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByTestDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where testDuration is less than DEFAULT_TEST_DURATION
        defaultTestCategoriesShouldNotBeFound("testDuration.lessThan=" + DEFAULT_TEST_DURATION);

        // Get all the testCategoriesList where testDuration is less than UPDATED_TEST_DURATION
        defaultTestCategoriesShouldBeFound("testDuration.lessThan=" + UPDATED_TEST_DURATION);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByTestDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where testDuration is greater than DEFAULT_TEST_DURATION
        defaultTestCategoriesShouldNotBeFound("testDuration.greaterThan=" + DEFAULT_TEST_DURATION);

        // Get all the testCategoriesList where testDuration is greater than SMALLER_TEST_DURATION
        defaultTestCategoriesShouldBeFound("testDuration.greaterThan=" + SMALLER_TEST_DURATION);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByPatientReportIsEqualToSomething() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where patientReport equals to DEFAULT_PATIENT_REPORT
        defaultTestCategoriesShouldBeFound("patientReport.equals=" + DEFAULT_PATIENT_REPORT);

        // Get all the testCategoriesList where patientReport equals to UPDATED_PATIENT_REPORT
        defaultTestCategoriesShouldNotBeFound("patientReport.equals=" + UPDATED_PATIENT_REPORT);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByPatientReportIsInShouldWork() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where patientReport in DEFAULT_PATIENT_REPORT or UPDATED_PATIENT_REPORT
        defaultTestCategoriesShouldBeFound("patientReport.in=" + DEFAULT_PATIENT_REPORT + "," + UPDATED_PATIENT_REPORT);

        // Get all the testCategoriesList where patientReport equals to UPDATED_PATIENT_REPORT
        defaultTestCategoriesShouldNotBeFound("patientReport.in=" + UPDATED_PATIENT_REPORT);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByPatientReportIsNullOrNotNull() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where patientReport is not null
        defaultTestCategoriesShouldBeFound("patientReport.specified=true");

        // Get all the testCategoriesList where patientReport is null
        defaultTestCategoriesShouldNotBeFound("patientReport.specified=false");
    }

    @Test
    @Transactional
    void getAllTestCategoriesByPatientReportContainsSomething() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where patientReport contains DEFAULT_PATIENT_REPORT
        defaultTestCategoriesShouldBeFound("patientReport.contains=" + DEFAULT_PATIENT_REPORT);

        // Get all the testCategoriesList where patientReport contains UPDATED_PATIENT_REPORT
        defaultTestCategoriesShouldNotBeFound("patientReport.contains=" + UPDATED_PATIENT_REPORT);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByPatientReportNotContainsSomething() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        // Get all the testCategoriesList where patientReport does not contain DEFAULT_PATIENT_REPORT
        defaultTestCategoriesShouldNotBeFound("patientReport.doesNotContain=" + DEFAULT_PATIENT_REPORT);

        // Get all the testCategoriesList where patientReport does not contain UPDATED_PATIENT_REPORT
        defaultTestCategoriesShouldBeFound("patientReport.doesNotContain=" + UPDATED_PATIENT_REPORT);
    }

    @Test
    @Transactional
    void getAllTestCategoriesByEquipmentIsEqualToSomething() throws Exception {
        Equipment equipment;
        if (TestUtil.findAll(em, Equipment.class).isEmpty()) {
            testCategoriesRepository.saveAndFlush(testCategories);
            equipment = EquipmentResourceIT.createEntity(em);
        } else {
            equipment = TestUtil.findAll(em, Equipment.class).get(0);
        }
        em.persist(equipment);
        em.flush();
        testCategories.setEquipment(equipment);
        testCategoriesRepository.saveAndFlush(testCategories);
        Long equipmentId = equipment.getId();
        // Get all the testCategoriesList where equipment equals to equipmentId
        defaultTestCategoriesShouldBeFound("equipmentId.equals=" + equipmentId);

        // Get all the testCategoriesList where equipment equals to (equipmentId + 1)
        defaultTestCategoriesShouldNotBeFound("equipmentId.equals=" + (equipmentId + 1));
    }

    @Test
    @Transactional
    void getAllTestCategoriesByParentTestCategoryIsEqualToSomething() throws Exception {
        TestCategories parentTestCategory;
        if (TestUtil.findAll(em, TestCategories.class).isEmpty()) {
            testCategoriesRepository.saveAndFlush(testCategories);
            parentTestCategory = TestCategoriesResourceIT.createEntity(em);
        } else {
            parentTestCategory = TestUtil.findAll(em, TestCategories.class).get(0);
        }
        em.persist(parentTestCategory);
        em.flush();
        testCategories.setParentTestCategory(parentTestCategory);
        testCategoriesRepository.saveAndFlush(testCategories);
        Long parentTestCategoryId = parentTestCategory.getId();
        // Get all the testCategoriesList where parentTestCategory equals to parentTestCategoryId
        defaultTestCategoriesShouldBeFound("parentTestCategoryId.equals=" + parentTestCategoryId);

        // Get all the testCategoriesList where parentTestCategory equals to (parentTestCategoryId + 1)
        defaultTestCategoriesShouldNotBeFound("parentTestCategoryId.equals=" + (parentTestCategoryId + 1));
    }

    @Test
    @Transactional
    void getAllTestCategoriesByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            testCategoriesRepository.saveAndFlush(testCategories);
            user = UserResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        testCategories.setUser(user);
        testCategoriesRepository.saveAndFlush(testCategories);
        Long userId = user.getId();
        // Get all the testCategoriesList where user equals to userId
        defaultTestCategoriesShouldBeFound("userId.equals=" + userId);

        // Get all the testCategoriesList where user equals to (userId + 1)
        defaultTestCategoriesShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllTestCategoriesByPatientTestTimingsIsEqualToSomething() throws Exception {
        PatientTestTimings patientTestTimings;
        if (TestUtil.findAll(em, PatientTestTimings.class).isEmpty()) {
            testCategoriesRepository.saveAndFlush(testCategories);
            patientTestTimings = PatientTestTimingsResourceIT.createEntity(em);
        } else {
            patientTestTimings = TestUtil.findAll(em, PatientTestTimings.class).get(0);
        }
        em.persist(patientTestTimings);
        em.flush();
        testCategories.addPatientTestTimings(patientTestTimings);
        testCategoriesRepository.saveAndFlush(testCategories);
        Long patientTestTimingsId = patientTestTimings.getId();
        // Get all the testCategoriesList where patientTestTimings equals to patientTestTimingsId
        defaultTestCategoriesShouldBeFound("patientTestTimingsId.equals=" + patientTestTimingsId);

        // Get all the testCategoriesList where patientTestTimings equals to (patientTestTimingsId + 1)
        defaultTestCategoriesShouldNotBeFound("patientTestTimingsId.equals=" + (patientTestTimingsId + 1));
    }

    @Test
    @Transactional
    void getAllTestCategoriesByTestCategoryParentIsEqualToSomething() throws Exception {
        TestCategories testCategoryParent;
        if (TestUtil.findAll(em, TestCategories.class).isEmpty()) {
            testCategoriesRepository.saveAndFlush(testCategories);
            testCategoryParent = TestCategoriesResourceIT.createEntity(em);
        } else {
            testCategoryParent = TestUtil.findAll(em, TestCategories.class).get(0);
        }
        em.persist(testCategoryParent);
        em.flush();
        testCategories.addTestCategoryParent(testCategoryParent);
        testCategoriesRepository.saveAndFlush(testCategories);
        Long testCategoryParentId = testCategoryParent.getId();
        // Get all the testCategoriesList where testCategoryParent equals to testCategoryParentId
        defaultTestCategoriesShouldBeFound("testCategoryParentId.equals=" + testCategoryParentId);

        // Get all the testCategoriesList where testCategoryParent equals to (testCategoryParentId + 1)
        defaultTestCategoriesShouldNotBeFound("testCategoryParentId.equals=" + (testCategoryParentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTestCategoriesShouldBeFound(String filter) throws Exception {
        restTestCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].testName").value(hasItem(DEFAULT_TEST_NAME)))
            .andExpect(jsonPath("$.[*].testDuration").value(hasItem(DEFAULT_TEST_DURATION)))
            .andExpect(jsonPath("$.[*].patientReport").value(hasItem(DEFAULT_PATIENT_REPORT)));

        // Check, that the count call also returns 1
        restTestCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTestCategoriesShouldNotBeFound(String filter) throws Exception {
        restTestCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTestCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTestCategories() throws Exception {
        // Get the testCategories
        restTestCategoriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTestCategories() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        int databaseSizeBeforeUpdate = testCategoriesRepository.findAll().size();

        // Update the testCategories
        TestCategories updatedTestCategories = testCategoriesRepository.findById(testCategories.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTestCategories are not directly saved in db
        em.detach(updatedTestCategories);
        updatedTestCategories.testName(UPDATED_TEST_NAME).testDuration(UPDATED_TEST_DURATION).patientReport(UPDATED_PATIENT_REPORT);
        TestCategoriesDTO testCategoriesDTO = testCategoriesMapper.toDto(updatedTestCategories);

        restTestCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, testCategoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(testCategoriesDTO))
            )
            .andExpect(status().isOk());

        // Validate the TestCategories in the database
        List<TestCategories> testCategoriesList = testCategoriesRepository.findAll();
        assertThat(testCategoriesList).hasSize(databaseSizeBeforeUpdate);
        TestCategories testTestCategories = testCategoriesList.get(testCategoriesList.size() - 1);
        assertThat(testTestCategories.getTestName()).isEqualTo(UPDATED_TEST_NAME);
        assertThat(testTestCategories.getTestDuration()).isEqualTo(UPDATED_TEST_DURATION);
        assertThat(testTestCategories.getPatientReport()).isEqualTo(UPDATED_PATIENT_REPORT);
    }

    @Test
    @Transactional
    void putNonExistingTestCategories() throws Exception {
        int databaseSizeBeforeUpdate = testCategoriesRepository.findAll().size();
        testCategories.setId(longCount.incrementAndGet());

        // Create the TestCategories
        TestCategoriesDTO testCategoriesDTO = testCategoriesMapper.toDto(testCategories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, testCategoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(testCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestCategories in the database
        List<TestCategories> testCategoriesList = testCategoriesRepository.findAll();
        assertThat(testCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTestCategories() throws Exception {
        int databaseSizeBeforeUpdate = testCategoriesRepository.findAll().size();
        testCategories.setId(longCount.incrementAndGet());

        // Create the TestCategories
        TestCategoriesDTO testCategoriesDTO = testCategoriesMapper.toDto(testCategories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(testCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestCategories in the database
        List<TestCategories> testCategoriesList = testCategoriesRepository.findAll();
        assertThat(testCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTestCategories() throws Exception {
        int databaseSizeBeforeUpdate = testCategoriesRepository.findAll().size();
        testCategories.setId(longCount.incrementAndGet());

        // Create the TestCategories
        TestCategoriesDTO testCategoriesDTO = testCategoriesMapper.toDto(testCategories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testCategoriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TestCategories in the database
        List<TestCategories> testCategoriesList = testCategoriesRepository.findAll();
        assertThat(testCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTestCategoriesWithPatch() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        int databaseSizeBeforeUpdate = testCategoriesRepository.findAll().size();

        // Update the testCategories using partial update
        TestCategories partialUpdatedTestCategories = new TestCategories();
        partialUpdatedTestCategories.setId(testCategories.getId());

        partialUpdatedTestCategories.testName(UPDATED_TEST_NAME).testDuration(UPDATED_TEST_DURATION);

        restTestCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTestCategories.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTestCategories))
            )
            .andExpect(status().isOk());

        // Validate the TestCategories in the database
        List<TestCategories> testCategoriesList = testCategoriesRepository.findAll();
        assertThat(testCategoriesList).hasSize(databaseSizeBeforeUpdate);
        TestCategories testTestCategories = testCategoriesList.get(testCategoriesList.size() - 1);
        assertThat(testTestCategories.getTestName()).isEqualTo(UPDATED_TEST_NAME);
        assertThat(testTestCategories.getTestDuration()).isEqualTo(UPDATED_TEST_DURATION);
        assertThat(testTestCategories.getPatientReport()).isEqualTo(DEFAULT_PATIENT_REPORT);
    }

    @Test
    @Transactional
    void fullUpdateTestCategoriesWithPatch() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        int databaseSizeBeforeUpdate = testCategoriesRepository.findAll().size();

        // Update the testCategories using partial update
        TestCategories partialUpdatedTestCategories = new TestCategories();
        partialUpdatedTestCategories.setId(testCategories.getId());

        partialUpdatedTestCategories.testName(UPDATED_TEST_NAME).testDuration(UPDATED_TEST_DURATION).patientReport(UPDATED_PATIENT_REPORT);

        restTestCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTestCategories.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTestCategories))
            )
            .andExpect(status().isOk());

        // Validate the TestCategories in the database
        List<TestCategories> testCategoriesList = testCategoriesRepository.findAll();
        assertThat(testCategoriesList).hasSize(databaseSizeBeforeUpdate);
        TestCategories testTestCategories = testCategoriesList.get(testCategoriesList.size() - 1);
        assertThat(testTestCategories.getTestName()).isEqualTo(UPDATED_TEST_NAME);
        assertThat(testTestCategories.getTestDuration()).isEqualTo(UPDATED_TEST_DURATION);
        assertThat(testTestCategories.getPatientReport()).isEqualTo(UPDATED_PATIENT_REPORT);
    }

    @Test
    @Transactional
    void patchNonExistingTestCategories() throws Exception {
        int databaseSizeBeforeUpdate = testCategoriesRepository.findAll().size();
        testCategories.setId(longCount.incrementAndGet());

        // Create the TestCategories
        TestCategoriesDTO testCategoriesDTO = testCategoriesMapper.toDto(testCategories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, testCategoriesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(testCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestCategories in the database
        List<TestCategories> testCategoriesList = testCategoriesRepository.findAll();
        assertThat(testCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTestCategories() throws Exception {
        int databaseSizeBeforeUpdate = testCategoriesRepository.findAll().size();
        testCategories.setId(longCount.incrementAndGet());

        // Create the TestCategories
        TestCategoriesDTO testCategoriesDTO = testCategoriesMapper.toDto(testCategories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(testCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestCategories in the database
        List<TestCategories> testCategoriesList = testCategoriesRepository.findAll();
        assertThat(testCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTestCategories() throws Exception {
        int databaseSizeBeforeUpdate = testCategoriesRepository.findAll().size();
        testCategories.setId(longCount.incrementAndGet());

        // Create the TestCategories
        TestCategoriesDTO testCategoriesDTO = testCategoriesMapper.toDto(testCategories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(testCategoriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TestCategories in the database
        List<TestCategories> testCategoriesList = testCategoriesRepository.findAll();
        assertThat(testCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTestCategories() throws Exception {
        // Initialize the database
        testCategoriesRepository.saveAndFlush(testCategories);

        int databaseSizeBeforeDelete = testCategoriesRepository.findAll().size();

        // Delete the testCategories
        restTestCategoriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, testCategories.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TestCategories> testCategoriesList = testCategoriesRepository.findAll();
        assertThat(testCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
