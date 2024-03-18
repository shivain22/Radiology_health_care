package com.radiology.health.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.app.IntegrationTest;
import com.radiology.health.app.domain.Equipments;
import com.radiology.health.app.domain.TestCatogories;
import com.radiology.health.app.domain.TestCatogories;
import com.radiology.health.app.domain.TestTimings;
import com.radiology.health.app.repository.TestCatogoriesRepository;
import com.radiology.health.app.service.dto.TestCatogoriesDTO;
import com.radiology.health.app.service.mapper.TestCatogoriesMapper;
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
 * Integration tests for the {@link TestCatogoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TestCatogoriesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/test-catogories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TestCatogoriesRepository testCatogoriesRepository;

    @Autowired
    private TestCatogoriesMapper testCatogoriesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTestCatogoriesMockMvc;

    private TestCatogories testCatogories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestCatogories createEntity(EntityManager em) {
        TestCatogories testCatogories = new TestCatogories().name(DEFAULT_NAME);
        // Add required entity
        Equipments equipments;
        if (TestUtil.findAll(em, Equipments.class).isEmpty()) {
            equipments = EquipmentsResourceIT.createEntity(em);
            em.persist(equipments);
            em.flush();
        } else {
            equipments = TestUtil.findAll(em, Equipments.class).get(0);
        }
        testCatogories.setEquipments(equipments);
        return testCatogories;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestCatogories createUpdatedEntity(EntityManager em) {
        TestCatogories testCatogories = new TestCatogories().name(UPDATED_NAME);
        // Add required entity
        Equipments equipments;
        if (TestUtil.findAll(em, Equipments.class).isEmpty()) {
            equipments = EquipmentsResourceIT.createUpdatedEntity(em);
            em.persist(equipments);
            em.flush();
        } else {
            equipments = TestUtil.findAll(em, Equipments.class).get(0);
        }
        testCatogories.setEquipments(equipments);
        return testCatogories;
    }

    @BeforeEach
    public void initTest() {
        testCatogories = createEntity(em);
    }

    @Test
    @Transactional
    void createTestCatogories() throws Exception {
        int databaseSizeBeforeCreate = testCatogoriesRepository.findAll().size();
        // Create the TestCatogories
        TestCatogoriesDTO testCatogoriesDTO = testCatogoriesMapper.toDto(testCatogories);
        restTestCatogoriesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testCatogoriesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TestCatogories in the database
        List<TestCatogories> testCatogoriesList = testCatogoriesRepository.findAll();
        assertThat(testCatogoriesList).hasSize(databaseSizeBeforeCreate + 1);
        TestCatogories testTestCatogories = testCatogoriesList.get(testCatogoriesList.size() - 1);
        assertThat(testTestCatogories.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createTestCatogoriesWithExistingId() throws Exception {
        // Create the TestCatogories with an existing ID
        testCatogories.setId(1L);
        TestCatogoriesDTO testCatogoriesDTO = testCatogoriesMapper.toDto(testCatogories);

        int databaseSizeBeforeCreate = testCatogoriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestCatogoriesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testCatogoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestCatogories in the database
        List<TestCatogories> testCatogoriesList = testCatogoriesRepository.findAll();
        assertThat(testCatogoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = testCatogoriesRepository.findAll().size();
        // set the field null
        testCatogories.setName(null);

        // Create the TestCatogories, which fails.
        TestCatogoriesDTO testCatogoriesDTO = testCatogoriesMapper.toDto(testCatogories);

        restTestCatogoriesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testCatogoriesDTO))
            )
            .andExpect(status().isBadRequest());

        List<TestCatogories> testCatogoriesList = testCatogoriesRepository.findAll();
        assertThat(testCatogoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTestCatogories() throws Exception {
        // Initialize the database
        testCatogoriesRepository.saveAndFlush(testCatogories);

        // Get all the testCatogoriesList
        restTestCatogoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testCatogories.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getTestCatogories() throws Exception {
        // Initialize the database
        testCatogoriesRepository.saveAndFlush(testCatogories);

        // Get the testCatogories
        restTestCatogoriesMockMvc
            .perform(get(ENTITY_API_URL_ID, testCatogories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(testCatogories.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getTestCatogoriesByIdFiltering() throws Exception {
        // Initialize the database
        testCatogoriesRepository.saveAndFlush(testCatogories);

        Long id = testCatogories.getId();

        defaultTestCatogoriesShouldBeFound("id.equals=" + id);
        defaultTestCatogoriesShouldNotBeFound("id.notEquals=" + id);

        defaultTestCatogoriesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTestCatogoriesShouldNotBeFound("id.greaterThan=" + id);

        defaultTestCatogoriesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTestCatogoriesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTestCatogoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        testCatogoriesRepository.saveAndFlush(testCatogories);

        // Get all the testCatogoriesList where name equals to DEFAULT_NAME
        defaultTestCatogoriesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the testCatogoriesList where name equals to UPDATED_NAME
        defaultTestCatogoriesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTestCatogoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        testCatogoriesRepository.saveAndFlush(testCatogories);

        // Get all the testCatogoriesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTestCatogoriesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the testCatogoriesList where name equals to UPDATED_NAME
        defaultTestCatogoriesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTestCatogoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        testCatogoriesRepository.saveAndFlush(testCatogories);

        // Get all the testCatogoriesList where name is not null
        defaultTestCatogoriesShouldBeFound("name.specified=true");

        // Get all the testCatogoriesList where name is null
        defaultTestCatogoriesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllTestCatogoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        testCatogoriesRepository.saveAndFlush(testCatogories);

        // Get all the testCatogoriesList where name contains DEFAULT_NAME
        defaultTestCatogoriesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the testCatogoriesList where name contains UPDATED_NAME
        defaultTestCatogoriesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTestCatogoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        testCatogoriesRepository.saveAndFlush(testCatogories);

        // Get all the testCatogoriesList where name does not contain DEFAULT_NAME
        defaultTestCatogoriesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the testCatogoriesList where name does not contain UPDATED_NAME
        defaultTestCatogoriesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTestCatogoriesByEquipmentsIsEqualToSomething() throws Exception {
        Equipments equipments;
        if (TestUtil.findAll(em, Equipments.class).isEmpty()) {
            testCatogoriesRepository.saveAndFlush(testCatogories);
            equipments = EquipmentsResourceIT.createEntity(em);
        } else {
            equipments = TestUtil.findAll(em, Equipments.class).get(0);
        }
        em.persist(equipments);
        em.flush();
        testCatogories.setEquipments(equipments);
        testCatogoriesRepository.saveAndFlush(testCatogories);
        Long equipmentsId = equipments.getId();
        // Get all the testCatogoriesList where equipments equals to equipmentsId
        defaultTestCatogoriesShouldBeFound("equipmentsId.equals=" + equipmentsId);

        // Get all the testCatogoriesList where equipments equals to (equipmentsId + 1)
        defaultTestCatogoriesShouldNotBeFound("equipmentsId.equals=" + (equipmentsId + 1));
    }

    @Test
    @Transactional
    void getAllTestCatogoriesByTestCatogories_parentIsEqualToSomething() throws Exception {
        TestCatogories testCatogories_parent;
        if (TestUtil.findAll(em, TestCatogories.class).isEmpty()) {
            testCatogoriesRepository.saveAndFlush(testCatogories);
            testCatogories_parent = TestCatogoriesResourceIT.createEntity(em);
        } else {
            testCatogories_parent = TestUtil.findAll(em, TestCatogories.class).get(0);
        }
        em.persist(testCatogories_parent);
        em.flush();
        testCatogories.setTestCatogories_parent(testCatogories_parent);
        testCatogoriesRepository.saveAndFlush(testCatogories);
        Long testCatogories_parentId = testCatogories_parent.getId();
        // Get all the testCatogoriesList where testCatogories_parent equals to testCatogories_parentId
        defaultTestCatogoriesShouldBeFound("testCatogories_parentId.equals=" + testCatogories_parentId);

        // Get all the testCatogoriesList where testCatogories_parent equals to (testCatogories_parentId + 1)
        defaultTestCatogoriesShouldNotBeFound("testCatogories_parentId.equals=" + (testCatogories_parentId + 1));
    }

    @Test
    @Transactional
    void getAllTestCatogoriesByTest_catogories_parent_catogoryIsEqualToSomething() throws Exception {
        TestCatogories test_catogories_parent_catogory;
        if (TestUtil.findAll(em, TestCatogories.class).isEmpty()) {
            testCatogoriesRepository.saveAndFlush(testCatogories);
            test_catogories_parent_catogory = TestCatogoriesResourceIT.createEntity(em);
        } else {
            test_catogories_parent_catogory = TestUtil.findAll(em, TestCatogories.class).get(0);
        }
        em.persist(test_catogories_parent_catogory);
        em.flush();
        testCatogories.addTest_catogories_parent_catogory(test_catogories_parent_catogory);
        testCatogoriesRepository.saveAndFlush(testCatogories);
        Long test_catogories_parent_catogoryId = test_catogories_parent_catogory.getId();
        // Get all the testCatogoriesList where test_catogories_parent_catogory equals to test_catogories_parent_catogoryId
        defaultTestCatogoriesShouldBeFound("test_catogories_parent_catogoryId.equals=" + test_catogories_parent_catogoryId);

        // Get all the testCatogoriesList where test_catogories_parent_catogory equals to (test_catogories_parent_catogoryId + 1)
        defaultTestCatogoriesShouldNotBeFound("test_catogories_parent_catogoryId.equals=" + (test_catogories_parent_catogoryId + 1));
    }

    @Test
    @Transactional
    void getAllTestCatogoriesByTestTimingsIsEqualToSomething() throws Exception {
        TestTimings testTimings;
        if (TestUtil.findAll(em, TestTimings.class).isEmpty()) {
            testCatogoriesRepository.saveAndFlush(testCatogories);
            testTimings = TestTimingsResourceIT.createEntity(em);
        } else {
            testTimings = TestUtil.findAll(em, TestTimings.class).get(0);
        }
        em.persist(testTimings);
        em.flush();
        testCatogories.addTestTimings(testTimings);
        testCatogoriesRepository.saveAndFlush(testCatogories);
        Long testTimingsId = testTimings.getId();
        // Get all the testCatogoriesList where testTimings equals to testTimingsId
        defaultTestCatogoriesShouldBeFound("testTimingsId.equals=" + testTimingsId);

        // Get all the testCatogoriesList where testTimings equals to (testTimingsId + 1)
        defaultTestCatogoriesShouldNotBeFound("testTimingsId.equals=" + (testTimingsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTestCatogoriesShouldBeFound(String filter) throws Exception {
        restTestCatogoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testCatogories.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTestCatogoriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTestCatogoriesShouldNotBeFound(String filter) throws Exception {
        restTestCatogoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTestCatogoriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTestCatogories() throws Exception {
        // Get the testCatogories
        restTestCatogoriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTestCatogories() throws Exception {
        // Initialize the database
        testCatogoriesRepository.saveAndFlush(testCatogories);

        int databaseSizeBeforeUpdate = testCatogoriesRepository.findAll().size();

        // Update the testCatogories
        TestCatogories updatedTestCatogories = testCatogoriesRepository.findById(testCatogories.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTestCatogories are not directly saved in db
        em.detach(updatedTestCatogories);
        updatedTestCatogories.name(UPDATED_NAME);
        TestCatogoriesDTO testCatogoriesDTO = testCatogoriesMapper.toDto(updatedTestCatogories);

        restTestCatogoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, testCatogoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(testCatogoriesDTO))
            )
            .andExpect(status().isOk());

        // Validate the TestCatogories in the database
        List<TestCatogories> testCatogoriesList = testCatogoriesRepository.findAll();
        assertThat(testCatogoriesList).hasSize(databaseSizeBeforeUpdate);
        TestCatogories testTestCatogories = testCatogoriesList.get(testCatogoriesList.size() - 1);
        assertThat(testTestCatogories.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingTestCatogories() throws Exception {
        int databaseSizeBeforeUpdate = testCatogoriesRepository.findAll().size();
        testCatogories.setId(longCount.incrementAndGet());

        // Create the TestCatogories
        TestCatogoriesDTO testCatogoriesDTO = testCatogoriesMapper.toDto(testCatogories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestCatogoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, testCatogoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(testCatogoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestCatogories in the database
        List<TestCatogories> testCatogoriesList = testCatogoriesRepository.findAll();
        assertThat(testCatogoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTestCatogories() throws Exception {
        int databaseSizeBeforeUpdate = testCatogoriesRepository.findAll().size();
        testCatogories.setId(longCount.incrementAndGet());

        // Create the TestCatogories
        TestCatogoriesDTO testCatogoriesDTO = testCatogoriesMapper.toDto(testCatogories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestCatogoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(testCatogoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestCatogories in the database
        List<TestCatogories> testCatogoriesList = testCatogoriesRepository.findAll();
        assertThat(testCatogoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTestCatogories() throws Exception {
        int databaseSizeBeforeUpdate = testCatogoriesRepository.findAll().size();
        testCatogories.setId(longCount.incrementAndGet());

        // Create the TestCatogories
        TestCatogoriesDTO testCatogoriesDTO = testCatogoriesMapper.toDto(testCatogories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestCatogoriesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testCatogoriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TestCatogories in the database
        List<TestCatogories> testCatogoriesList = testCatogoriesRepository.findAll();
        assertThat(testCatogoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTestCatogoriesWithPatch() throws Exception {
        // Initialize the database
        testCatogoriesRepository.saveAndFlush(testCatogories);

        int databaseSizeBeforeUpdate = testCatogoriesRepository.findAll().size();

        // Update the testCatogories using partial update
        TestCatogories partialUpdatedTestCatogories = new TestCatogories();
        partialUpdatedTestCatogories.setId(testCatogories.getId());

        restTestCatogoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTestCatogories.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTestCatogories))
            )
            .andExpect(status().isOk());

        // Validate the TestCatogories in the database
        List<TestCatogories> testCatogoriesList = testCatogoriesRepository.findAll();
        assertThat(testCatogoriesList).hasSize(databaseSizeBeforeUpdate);
        TestCatogories testTestCatogories = testCatogoriesList.get(testCatogoriesList.size() - 1);
        assertThat(testTestCatogories.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateTestCatogoriesWithPatch() throws Exception {
        // Initialize the database
        testCatogoriesRepository.saveAndFlush(testCatogories);

        int databaseSizeBeforeUpdate = testCatogoriesRepository.findAll().size();

        // Update the testCatogories using partial update
        TestCatogories partialUpdatedTestCatogories = new TestCatogories();
        partialUpdatedTestCatogories.setId(testCatogories.getId());

        partialUpdatedTestCatogories.name(UPDATED_NAME);

        restTestCatogoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTestCatogories.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTestCatogories))
            )
            .andExpect(status().isOk());

        // Validate the TestCatogories in the database
        List<TestCatogories> testCatogoriesList = testCatogoriesRepository.findAll();
        assertThat(testCatogoriesList).hasSize(databaseSizeBeforeUpdate);
        TestCatogories testTestCatogories = testCatogoriesList.get(testCatogoriesList.size() - 1);
        assertThat(testTestCatogories.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingTestCatogories() throws Exception {
        int databaseSizeBeforeUpdate = testCatogoriesRepository.findAll().size();
        testCatogories.setId(longCount.incrementAndGet());

        // Create the TestCatogories
        TestCatogoriesDTO testCatogoriesDTO = testCatogoriesMapper.toDto(testCatogories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestCatogoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, testCatogoriesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(testCatogoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestCatogories in the database
        List<TestCatogories> testCatogoriesList = testCatogoriesRepository.findAll();
        assertThat(testCatogoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTestCatogories() throws Exception {
        int databaseSizeBeforeUpdate = testCatogoriesRepository.findAll().size();
        testCatogories.setId(longCount.incrementAndGet());

        // Create the TestCatogories
        TestCatogoriesDTO testCatogoriesDTO = testCatogoriesMapper.toDto(testCatogories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestCatogoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(testCatogoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestCatogories in the database
        List<TestCatogories> testCatogoriesList = testCatogoriesRepository.findAll();
        assertThat(testCatogoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTestCatogories() throws Exception {
        int databaseSizeBeforeUpdate = testCatogoriesRepository.findAll().size();
        testCatogories.setId(longCount.incrementAndGet());

        // Create the TestCatogories
        TestCatogoriesDTO testCatogoriesDTO = testCatogoriesMapper.toDto(testCatogories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestCatogoriesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(testCatogoriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TestCatogories in the database
        List<TestCatogories> testCatogoriesList = testCatogoriesRepository.findAll();
        assertThat(testCatogoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTestCatogories() throws Exception {
        // Initialize the database
        testCatogoriesRepository.saveAndFlush(testCatogories);

        int databaseSizeBeforeDelete = testCatogoriesRepository.findAll().size();

        // Delete the testCatogories
        restTestCatogoriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, testCatogories.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TestCatogories> testCatogoriesList = testCatogoriesRepository.findAll();
        assertThat(testCatogoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
