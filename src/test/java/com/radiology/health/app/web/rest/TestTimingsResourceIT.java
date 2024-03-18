package com.radiology.health.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.app.IntegrationTest;
import com.radiology.health.app.domain.PatientTestInfo;
import com.radiology.health.app.domain.TestCatogories;
import com.radiology.health.app.domain.TestTimings;
import com.radiology.health.app.repository.TestTimingsRepository;
import com.radiology.health.app.service.dto.TestTimingsDTO;
import com.radiology.health.app.service.mapper.TestTimingsMapper;
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
 * Integration tests for the {@link TestTimingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TestTimingsResourceIT {

    private static final String DEFAULT_TIMINGS = "AAAAAAAAAA";
    private static final String UPDATED_TIMINGS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/test-timings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TestTimingsRepository testTimingsRepository;

    @Autowired
    private TestTimingsMapper testTimingsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTestTimingsMockMvc;

    private TestTimings testTimings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestTimings createEntity(EntityManager em) {
        TestTimings testTimings = new TestTimings().timings(DEFAULT_TIMINGS);
        // Add required entity
        TestCatogories testCatogories;
        if (TestUtil.findAll(em, TestCatogories.class).isEmpty()) {
            testCatogories = TestCatogoriesResourceIT.createEntity(em);
            em.persist(testCatogories);
            em.flush();
        } else {
            testCatogories = TestUtil.findAll(em, TestCatogories.class).get(0);
        }
        testTimings.setTestCatogories(testCatogories);
        return testTimings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestTimings createUpdatedEntity(EntityManager em) {
        TestTimings testTimings = new TestTimings().timings(UPDATED_TIMINGS);
        // Add required entity
        TestCatogories testCatogories;
        if (TestUtil.findAll(em, TestCatogories.class).isEmpty()) {
            testCatogories = TestCatogoriesResourceIT.createUpdatedEntity(em);
            em.persist(testCatogories);
            em.flush();
        } else {
            testCatogories = TestUtil.findAll(em, TestCatogories.class).get(0);
        }
        testTimings.setTestCatogories(testCatogories);
        return testTimings;
    }

    @BeforeEach
    public void initTest() {
        testTimings = createEntity(em);
    }

    @Test
    @Transactional
    void createTestTimings() throws Exception {
        int databaseSizeBeforeCreate = testTimingsRepository.findAll().size();
        // Create the TestTimings
        TestTimingsDTO testTimingsDTO = testTimingsMapper.toDto(testTimings);
        restTestTimingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testTimingsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TestTimings in the database
        List<TestTimings> testTimingsList = testTimingsRepository.findAll();
        assertThat(testTimingsList).hasSize(databaseSizeBeforeCreate + 1);
        TestTimings testTestTimings = testTimingsList.get(testTimingsList.size() - 1);
        assertThat(testTestTimings.getTimings()).isEqualTo(DEFAULT_TIMINGS);
    }

    @Test
    @Transactional
    void createTestTimingsWithExistingId() throws Exception {
        // Create the TestTimings with an existing ID
        testTimings.setId(1L);
        TestTimingsDTO testTimingsDTO = testTimingsMapper.toDto(testTimings);

        int databaseSizeBeforeCreate = testTimingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestTimingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestTimings in the database
        List<TestTimings> testTimingsList = testTimingsRepository.findAll();
        assertThat(testTimingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTimingsIsRequired() throws Exception {
        int databaseSizeBeforeTest = testTimingsRepository.findAll().size();
        // set the field null
        testTimings.setTimings(null);

        // Create the TestTimings, which fails.
        TestTimingsDTO testTimingsDTO = testTimingsMapper.toDto(testTimings);

        restTestTimingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        List<TestTimings> testTimingsList = testTimingsRepository.findAll();
        assertThat(testTimingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTestTimings() throws Exception {
        // Initialize the database
        testTimingsRepository.saveAndFlush(testTimings);

        // Get all the testTimingsList
        restTestTimingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testTimings.getId().intValue())))
            .andExpect(jsonPath("$.[*].timings").value(hasItem(DEFAULT_TIMINGS)));
    }

    @Test
    @Transactional
    void getTestTimings() throws Exception {
        // Initialize the database
        testTimingsRepository.saveAndFlush(testTimings);

        // Get the testTimings
        restTestTimingsMockMvc
            .perform(get(ENTITY_API_URL_ID, testTimings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(testTimings.getId().intValue()))
            .andExpect(jsonPath("$.timings").value(DEFAULT_TIMINGS));
    }

    @Test
    @Transactional
    void getTestTimingsByIdFiltering() throws Exception {
        // Initialize the database
        testTimingsRepository.saveAndFlush(testTimings);

        Long id = testTimings.getId();

        defaultTestTimingsShouldBeFound("id.equals=" + id);
        defaultTestTimingsShouldNotBeFound("id.notEquals=" + id);

        defaultTestTimingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTestTimingsShouldNotBeFound("id.greaterThan=" + id);

        defaultTestTimingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTestTimingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTestTimingsByTimingsIsEqualToSomething() throws Exception {
        // Initialize the database
        testTimingsRepository.saveAndFlush(testTimings);

        // Get all the testTimingsList where timings equals to DEFAULT_TIMINGS
        defaultTestTimingsShouldBeFound("timings.equals=" + DEFAULT_TIMINGS);

        // Get all the testTimingsList where timings equals to UPDATED_TIMINGS
        defaultTestTimingsShouldNotBeFound("timings.equals=" + UPDATED_TIMINGS);
    }

    @Test
    @Transactional
    void getAllTestTimingsByTimingsIsInShouldWork() throws Exception {
        // Initialize the database
        testTimingsRepository.saveAndFlush(testTimings);

        // Get all the testTimingsList where timings in DEFAULT_TIMINGS or UPDATED_TIMINGS
        defaultTestTimingsShouldBeFound("timings.in=" + DEFAULT_TIMINGS + "," + UPDATED_TIMINGS);

        // Get all the testTimingsList where timings equals to UPDATED_TIMINGS
        defaultTestTimingsShouldNotBeFound("timings.in=" + UPDATED_TIMINGS);
    }

    @Test
    @Transactional
    void getAllTestTimingsByTimingsIsNullOrNotNull() throws Exception {
        // Initialize the database
        testTimingsRepository.saveAndFlush(testTimings);

        // Get all the testTimingsList where timings is not null
        defaultTestTimingsShouldBeFound("timings.specified=true");

        // Get all the testTimingsList where timings is null
        defaultTestTimingsShouldNotBeFound("timings.specified=false");
    }

    @Test
    @Transactional
    void getAllTestTimingsByTimingsContainsSomething() throws Exception {
        // Initialize the database
        testTimingsRepository.saveAndFlush(testTimings);

        // Get all the testTimingsList where timings contains DEFAULT_TIMINGS
        defaultTestTimingsShouldBeFound("timings.contains=" + DEFAULT_TIMINGS);

        // Get all the testTimingsList where timings contains UPDATED_TIMINGS
        defaultTestTimingsShouldNotBeFound("timings.contains=" + UPDATED_TIMINGS);
    }

    @Test
    @Transactional
    void getAllTestTimingsByTimingsNotContainsSomething() throws Exception {
        // Initialize the database
        testTimingsRepository.saveAndFlush(testTimings);

        // Get all the testTimingsList where timings does not contain DEFAULT_TIMINGS
        defaultTestTimingsShouldNotBeFound("timings.doesNotContain=" + DEFAULT_TIMINGS);

        // Get all the testTimingsList where timings does not contain UPDATED_TIMINGS
        defaultTestTimingsShouldBeFound("timings.doesNotContain=" + UPDATED_TIMINGS);
    }

    @Test
    @Transactional
    void getAllTestTimingsByTestCatogoriesIsEqualToSomething() throws Exception {
        TestCatogories testCatogories;
        if (TestUtil.findAll(em, TestCatogories.class).isEmpty()) {
            testTimingsRepository.saveAndFlush(testTimings);
            testCatogories = TestCatogoriesResourceIT.createEntity(em);
        } else {
            testCatogories = TestUtil.findAll(em, TestCatogories.class).get(0);
        }
        em.persist(testCatogories);
        em.flush();
        testTimings.setTestCatogories(testCatogories);
        testTimingsRepository.saveAndFlush(testTimings);
        Long testCatogoriesId = testCatogories.getId();
        // Get all the testTimingsList where testCatogories equals to testCatogoriesId
        defaultTestTimingsShouldBeFound("testCatogoriesId.equals=" + testCatogoriesId);

        // Get all the testTimingsList where testCatogories equals to (testCatogoriesId + 1)
        defaultTestTimingsShouldNotBeFound("testCatogoriesId.equals=" + (testCatogoriesId + 1));
    }

    @Test
    @Transactional
    void getAllTestTimingsByPatientTestInfoIsEqualToSomething() throws Exception {
        PatientTestInfo patientTestInfo;
        if (TestUtil.findAll(em, PatientTestInfo.class).isEmpty()) {
            testTimingsRepository.saveAndFlush(testTimings);
            patientTestInfo = PatientTestInfoResourceIT.createEntity(em);
        } else {
            patientTestInfo = TestUtil.findAll(em, PatientTestInfo.class).get(0);
        }
        em.persist(patientTestInfo);
        em.flush();
        testTimings.addPatientTestInfo(patientTestInfo);
        testTimingsRepository.saveAndFlush(testTimings);
        Long patientTestInfoId = patientTestInfo.getId();
        // Get all the testTimingsList where patientTestInfo equals to patientTestInfoId
        defaultTestTimingsShouldBeFound("patientTestInfoId.equals=" + patientTestInfoId);

        // Get all the testTimingsList where patientTestInfo equals to (patientTestInfoId + 1)
        defaultTestTimingsShouldNotBeFound("patientTestInfoId.equals=" + (patientTestInfoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTestTimingsShouldBeFound(String filter) throws Exception {
        restTestTimingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testTimings.getId().intValue())))
            .andExpect(jsonPath("$.[*].timings").value(hasItem(DEFAULT_TIMINGS)));

        // Check, that the count call also returns 1
        restTestTimingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTestTimingsShouldNotBeFound(String filter) throws Exception {
        restTestTimingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTestTimingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTestTimings() throws Exception {
        // Get the testTimings
        restTestTimingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTestTimings() throws Exception {
        // Initialize the database
        testTimingsRepository.saveAndFlush(testTimings);

        int databaseSizeBeforeUpdate = testTimingsRepository.findAll().size();

        // Update the testTimings
        TestTimings updatedTestTimings = testTimingsRepository.findById(testTimings.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTestTimings are not directly saved in db
        em.detach(updatedTestTimings);
        updatedTestTimings.timings(UPDATED_TIMINGS);
        TestTimingsDTO testTimingsDTO = testTimingsMapper.toDto(updatedTestTimings);

        restTestTimingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, testTimingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(testTimingsDTO))
            )
            .andExpect(status().isOk());

        // Validate the TestTimings in the database
        List<TestTimings> testTimingsList = testTimingsRepository.findAll();
        assertThat(testTimingsList).hasSize(databaseSizeBeforeUpdate);
        TestTimings testTestTimings = testTimingsList.get(testTimingsList.size() - 1);
        assertThat(testTestTimings.getTimings()).isEqualTo(UPDATED_TIMINGS);
    }

    @Test
    @Transactional
    void putNonExistingTestTimings() throws Exception {
        int databaseSizeBeforeUpdate = testTimingsRepository.findAll().size();
        testTimings.setId(longCount.incrementAndGet());

        // Create the TestTimings
        TestTimingsDTO testTimingsDTO = testTimingsMapper.toDto(testTimings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestTimingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, testTimingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(testTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestTimings in the database
        List<TestTimings> testTimingsList = testTimingsRepository.findAll();
        assertThat(testTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTestTimings() throws Exception {
        int databaseSizeBeforeUpdate = testTimingsRepository.findAll().size();
        testTimings.setId(longCount.incrementAndGet());

        // Create the TestTimings
        TestTimingsDTO testTimingsDTO = testTimingsMapper.toDto(testTimings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestTimingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(testTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestTimings in the database
        List<TestTimings> testTimingsList = testTimingsRepository.findAll();
        assertThat(testTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTestTimings() throws Exception {
        int databaseSizeBeforeUpdate = testTimingsRepository.findAll().size();
        testTimings.setId(longCount.incrementAndGet());

        // Create the TestTimings
        TestTimingsDTO testTimingsDTO = testTimingsMapper.toDto(testTimings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestTimingsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testTimingsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TestTimings in the database
        List<TestTimings> testTimingsList = testTimingsRepository.findAll();
        assertThat(testTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTestTimingsWithPatch() throws Exception {
        // Initialize the database
        testTimingsRepository.saveAndFlush(testTimings);

        int databaseSizeBeforeUpdate = testTimingsRepository.findAll().size();

        // Update the testTimings using partial update
        TestTimings partialUpdatedTestTimings = new TestTimings();
        partialUpdatedTestTimings.setId(testTimings.getId());

        restTestTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTestTimings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTestTimings))
            )
            .andExpect(status().isOk());

        // Validate the TestTimings in the database
        List<TestTimings> testTimingsList = testTimingsRepository.findAll();
        assertThat(testTimingsList).hasSize(databaseSizeBeforeUpdate);
        TestTimings testTestTimings = testTimingsList.get(testTimingsList.size() - 1);
        assertThat(testTestTimings.getTimings()).isEqualTo(DEFAULT_TIMINGS);
    }

    @Test
    @Transactional
    void fullUpdateTestTimingsWithPatch() throws Exception {
        // Initialize the database
        testTimingsRepository.saveAndFlush(testTimings);

        int databaseSizeBeforeUpdate = testTimingsRepository.findAll().size();

        // Update the testTimings using partial update
        TestTimings partialUpdatedTestTimings = new TestTimings();
        partialUpdatedTestTimings.setId(testTimings.getId());

        partialUpdatedTestTimings.timings(UPDATED_TIMINGS);

        restTestTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTestTimings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTestTimings))
            )
            .andExpect(status().isOk());

        // Validate the TestTimings in the database
        List<TestTimings> testTimingsList = testTimingsRepository.findAll();
        assertThat(testTimingsList).hasSize(databaseSizeBeforeUpdate);
        TestTimings testTestTimings = testTimingsList.get(testTimingsList.size() - 1);
        assertThat(testTestTimings.getTimings()).isEqualTo(UPDATED_TIMINGS);
    }

    @Test
    @Transactional
    void patchNonExistingTestTimings() throws Exception {
        int databaseSizeBeforeUpdate = testTimingsRepository.findAll().size();
        testTimings.setId(longCount.incrementAndGet());

        // Create the TestTimings
        TestTimingsDTO testTimingsDTO = testTimingsMapper.toDto(testTimings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, testTimingsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(testTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestTimings in the database
        List<TestTimings> testTimingsList = testTimingsRepository.findAll();
        assertThat(testTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTestTimings() throws Exception {
        int databaseSizeBeforeUpdate = testTimingsRepository.findAll().size();
        testTimings.setId(longCount.incrementAndGet());

        // Create the TestTimings
        TestTimingsDTO testTimingsDTO = testTimingsMapper.toDto(testTimings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(testTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestTimings in the database
        List<TestTimings> testTimingsList = testTimingsRepository.findAll();
        assertThat(testTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTestTimings() throws Exception {
        int databaseSizeBeforeUpdate = testTimingsRepository.findAll().size();
        testTimings.setId(longCount.incrementAndGet());

        // Create the TestTimings
        TestTimingsDTO testTimingsDTO = testTimingsMapper.toDto(testTimings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(testTimingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TestTimings in the database
        List<TestTimings> testTimingsList = testTimingsRepository.findAll();
        assertThat(testTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTestTimings() throws Exception {
        // Initialize the database
        testTimingsRepository.saveAndFlush(testTimings);

        int databaseSizeBeforeDelete = testTimingsRepository.findAll().size();

        // Delete the testTimings
        restTestTimingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, testTimings.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TestTimings> testTimingsList = testTimingsRepository.findAll();
        assertThat(testTimingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
