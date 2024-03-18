package com.radiology.health.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.app.IntegrationTest;
import com.radiology.health.app.domain.PatientInfo;
import com.radiology.health.app.domain.PatientTestInfo;
import com.radiology.health.app.domain.TestTimings;
import com.radiology.health.app.repository.PatientTestInfoRepository;
import com.radiology.health.app.service.dto.PatientTestInfoDTO;
import com.radiology.health.app.service.mapper.PatientTestInfoMapper;
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
 * Integration tests for the {@link PatientTestInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PatientTestInfoResourceIT {

    private static final String ENTITY_API_URL = "/api/patient-test-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PatientTestInfoRepository patientTestInfoRepository;

    @Autowired
    private PatientTestInfoMapper patientTestInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientTestInfoMockMvc;

    private PatientTestInfo patientTestInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientTestInfo createEntity(EntityManager em) {
        PatientTestInfo patientTestInfo = new PatientTestInfo();
        // Add required entity
        PatientInfo patientInfo;
        if (TestUtil.findAll(em, PatientInfo.class).isEmpty()) {
            patientInfo = PatientInfoResourceIT.createEntity(em);
            em.persist(patientInfo);
            em.flush();
        } else {
            patientInfo = TestUtil.findAll(em, PatientInfo.class).get(0);
        }
        patientTestInfo.setPatientInfo(patientInfo);
        // Add required entity
        TestTimings testTimings;
        if (TestUtil.findAll(em, TestTimings.class).isEmpty()) {
            testTimings = TestTimingsResourceIT.createEntity(em);
            em.persist(testTimings);
            em.flush();
        } else {
            testTimings = TestUtil.findAll(em, TestTimings.class).get(0);
        }
        patientTestInfo.setTestTimings(testTimings);
        return patientTestInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientTestInfo createUpdatedEntity(EntityManager em) {
        PatientTestInfo patientTestInfo = new PatientTestInfo();
        // Add required entity
        PatientInfo patientInfo;
        if (TestUtil.findAll(em, PatientInfo.class).isEmpty()) {
            patientInfo = PatientInfoResourceIT.createUpdatedEntity(em);
            em.persist(patientInfo);
            em.flush();
        } else {
            patientInfo = TestUtil.findAll(em, PatientInfo.class).get(0);
        }
        patientTestInfo.setPatientInfo(patientInfo);
        // Add required entity
        TestTimings testTimings;
        if (TestUtil.findAll(em, TestTimings.class).isEmpty()) {
            testTimings = TestTimingsResourceIT.createUpdatedEntity(em);
            em.persist(testTimings);
            em.flush();
        } else {
            testTimings = TestUtil.findAll(em, TestTimings.class).get(0);
        }
        patientTestInfo.setTestTimings(testTimings);
        return patientTestInfo;
    }

    @BeforeEach
    public void initTest() {
        patientTestInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createPatientTestInfo() throws Exception {
        int databaseSizeBeforeCreate = patientTestInfoRepository.findAll().size();
        // Create the PatientTestInfo
        PatientTestInfoDTO patientTestInfoDTO = patientTestInfoMapper.toDto(patientTestInfo);
        restPatientTestInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientTestInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PatientTestInfo in the database
        List<PatientTestInfo> patientTestInfoList = patientTestInfoRepository.findAll();
        assertThat(patientTestInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PatientTestInfo testPatientTestInfo = patientTestInfoList.get(patientTestInfoList.size() - 1);
    }

    @Test
    @Transactional
    void createPatientTestInfoWithExistingId() throws Exception {
        // Create the PatientTestInfo with an existing ID
        patientTestInfo.setId(1L);
        PatientTestInfoDTO patientTestInfoDTO = patientTestInfoMapper.toDto(patientTestInfo);

        int databaseSizeBeforeCreate = patientTestInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientTestInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientTestInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientTestInfo in the database
        List<PatientTestInfo> patientTestInfoList = patientTestInfoRepository.findAll();
        assertThat(patientTestInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPatientTestInfos() throws Exception {
        // Initialize the database
        patientTestInfoRepository.saveAndFlush(patientTestInfo);

        // Get all the patientTestInfoList
        restPatientTestInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientTestInfo.getId().intValue())));
    }

    @Test
    @Transactional
    void getPatientTestInfo() throws Exception {
        // Initialize the database
        patientTestInfoRepository.saveAndFlush(patientTestInfo);

        // Get the patientTestInfo
        restPatientTestInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, patientTestInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patientTestInfo.getId().intValue()));
    }

    @Test
    @Transactional
    void getPatientTestInfosByIdFiltering() throws Exception {
        // Initialize the database
        patientTestInfoRepository.saveAndFlush(patientTestInfo);

        Long id = patientTestInfo.getId();

        defaultPatientTestInfoShouldBeFound("id.equals=" + id);
        defaultPatientTestInfoShouldNotBeFound("id.notEquals=" + id);

        defaultPatientTestInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPatientTestInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultPatientTestInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPatientTestInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPatientTestInfosByPatientInfoIsEqualToSomething() throws Exception {
        PatientInfo patientInfo;
        if (TestUtil.findAll(em, PatientInfo.class).isEmpty()) {
            patientTestInfoRepository.saveAndFlush(patientTestInfo);
            patientInfo = PatientInfoResourceIT.createEntity(em);
        } else {
            patientInfo = TestUtil.findAll(em, PatientInfo.class).get(0);
        }
        em.persist(patientInfo);
        em.flush();
        patientTestInfo.setPatientInfo(patientInfo);
        patientTestInfoRepository.saveAndFlush(patientTestInfo);
        Long patientInfoId = patientInfo.getId();
        // Get all the patientTestInfoList where patientInfo equals to patientInfoId
        defaultPatientTestInfoShouldBeFound("patientInfoId.equals=" + patientInfoId);

        // Get all the patientTestInfoList where patientInfo equals to (patientInfoId + 1)
        defaultPatientTestInfoShouldNotBeFound("patientInfoId.equals=" + (patientInfoId + 1));
    }

    @Test
    @Transactional
    void getAllPatientTestInfosByTestTimingsIsEqualToSomething() throws Exception {
        TestTimings testTimings;
        if (TestUtil.findAll(em, TestTimings.class).isEmpty()) {
            patientTestInfoRepository.saveAndFlush(patientTestInfo);
            testTimings = TestTimingsResourceIT.createEntity(em);
        } else {
            testTimings = TestUtil.findAll(em, TestTimings.class).get(0);
        }
        em.persist(testTimings);
        em.flush();
        patientTestInfo.setTestTimings(testTimings);
        patientTestInfoRepository.saveAndFlush(patientTestInfo);
        Long testTimingsId = testTimings.getId();
        // Get all the patientTestInfoList where testTimings equals to testTimingsId
        defaultPatientTestInfoShouldBeFound("testTimingsId.equals=" + testTimingsId);

        // Get all the patientTestInfoList where testTimings equals to (testTimingsId + 1)
        defaultPatientTestInfoShouldNotBeFound("testTimingsId.equals=" + (testTimingsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPatientTestInfoShouldBeFound(String filter) throws Exception {
        restPatientTestInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientTestInfo.getId().intValue())));

        // Check, that the count call also returns 1
        restPatientTestInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPatientTestInfoShouldNotBeFound(String filter) throws Exception {
        restPatientTestInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPatientTestInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPatientTestInfo() throws Exception {
        // Get the patientTestInfo
        restPatientTestInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPatientTestInfo() throws Exception {
        // Initialize the database
        patientTestInfoRepository.saveAndFlush(patientTestInfo);

        int databaseSizeBeforeUpdate = patientTestInfoRepository.findAll().size();

        // Update the patientTestInfo
        PatientTestInfo updatedPatientTestInfo = patientTestInfoRepository.findById(patientTestInfo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPatientTestInfo are not directly saved in db
        em.detach(updatedPatientTestInfo);
        PatientTestInfoDTO patientTestInfoDTO = patientTestInfoMapper.toDto(updatedPatientTestInfo);

        restPatientTestInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientTestInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientTestInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the PatientTestInfo in the database
        List<PatientTestInfo> patientTestInfoList = patientTestInfoRepository.findAll();
        assertThat(patientTestInfoList).hasSize(databaseSizeBeforeUpdate);
        PatientTestInfo testPatientTestInfo = patientTestInfoList.get(patientTestInfoList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingPatientTestInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientTestInfoRepository.findAll().size();
        patientTestInfo.setId(longCount.incrementAndGet());

        // Create the PatientTestInfo
        PatientTestInfoDTO patientTestInfoDTO = patientTestInfoMapper.toDto(patientTestInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientTestInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientTestInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientTestInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientTestInfo in the database
        List<PatientTestInfo> patientTestInfoList = patientTestInfoRepository.findAll();
        assertThat(patientTestInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPatientTestInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientTestInfoRepository.findAll().size();
        patientTestInfo.setId(longCount.incrementAndGet());

        // Create the PatientTestInfo
        PatientTestInfoDTO patientTestInfoDTO = patientTestInfoMapper.toDto(patientTestInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientTestInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientTestInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientTestInfo in the database
        List<PatientTestInfo> patientTestInfoList = patientTestInfoRepository.findAll();
        assertThat(patientTestInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPatientTestInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientTestInfoRepository.findAll().size();
        patientTestInfo.setId(longCount.incrementAndGet());

        // Create the PatientTestInfo
        PatientTestInfoDTO patientTestInfoDTO = patientTestInfoMapper.toDto(patientTestInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientTestInfoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientTestInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientTestInfo in the database
        List<PatientTestInfo> patientTestInfoList = patientTestInfoRepository.findAll();
        assertThat(patientTestInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePatientTestInfoWithPatch() throws Exception {
        // Initialize the database
        patientTestInfoRepository.saveAndFlush(patientTestInfo);

        int databaseSizeBeforeUpdate = patientTestInfoRepository.findAll().size();

        // Update the patientTestInfo using partial update
        PatientTestInfo partialUpdatedPatientTestInfo = new PatientTestInfo();
        partialUpdatedPatientTestInfo.setId(patientTestInfo.getId());

        restPatientTestInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientTestInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatientTestInfo))
            )
            .andExpect(status().isOk());

        // Validate the PatientTestInfo in the database
        List<PatientTestInfo> patientTestInfoList = patientTestInfoRepository.findAll();
        assertThat(patientTestInfoList).hasSize(databaseSizeBeforeUpdate);
        PatientTestInfo testPatientTestInfo = patientTestInfoList.get(patientTestInfoList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdatePatientTestInfoWithPatch() throws Exception {
        // Initialize the database
        patientTestInfoRepository.saveAndFlush(patientTestInfo);

        int databaseSizeBeforeUpdate = patientTestInfoRepository.findAll().size();

        // Update the patientTestInfo using partial update
        PatientTestInfo partialUpdatedPatientTestInfo = new PatientTestInfo();
        partialUpdatedPatientTestInfo.setId(patientTestInfo.getId());

        restPatientTestInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientTestInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatientTestInfo))
            )
            .andExpect(status().isOk());

        // Validate the PatientTestInfo in the database
        List<PatientTestInfo> patientTestInfoList = patientTestInfoRepository.findAll();
        assertThat(patientTestInfoList).hasSize(databaseSizeBeforeUpdate);
        PatientTestInfo testPatientTestInfo = patientTestInfoList.get(patientTestInfoList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingPatientTestInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientTestInfoRepository.findAll().size();
        patientTestInfo.setId(longCount.incrementAndGet());

        // Create the PatientTestInfo
        PatientTestInfoDTO patientTestInfoDTO = patientTestInfoMapper.toDto(patientTestInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientTestInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patientTestInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientTestInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientTestInfo in the database
        List<PatientTestInfo> patientTestInfoList = patientTestInfoRepository.findAll();
        assertThat(patientTestInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPatientTestInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientTestInfoRepository.findAll().size();
        patientTestInfo.setId(longCount.incrementAndGet());

        // Create the PatientTestInfo
        PatientTestInfoDTO patientTestInfoDTO = patientTestInfoMapper.toDto(patientTestInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientTestInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientTestInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientTestInfo in the database
        List<PatientTestInfo> patientTestInfoList = patientTestInfoRepository.findAll();
        assertThat(patientTestInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPatientTestInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientTestInfoRepository.findAll().size();
        patientTestInfo.setId(longCount.incrementAndGet());

        // Create the PatientTestInfo
        PatientTestInfoDTO patientTestInfoDTO = patientTestInfoMapper.toDto(patientTestInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientTestInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientTestInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientTestInfo in the database
        List<PatientTestInfo> patientTestInfoList = patientTestInfoRepository.findAll();
        assertThat(patientTestInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePatientTestInfo() throws Exception {
        // Initialize the database
        patientTestInfoRepository.saveAndFlush(patientTestInfo);

        int databaseSizeBeforeDelete = patientTestInfoRepository.findAll().size();

        // Delete the patientTestInfo
        restPatientTestInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, patientTestInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientTestInfo> patientTestInfoList = patientTestInfoRepository.findAll();
        assertThat(patientTestInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
