package com.radiology.health.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.app.IntegrationTest;
import com.radiology.health.app.domain.Employee;
import com.radiology.health.app.domain.Rank;
import com.radiology.health.app.domain.Services;
import com.radiology.health.app.repository.RankRepository;
import com.radiology.health.app.service.dto.RankDTO;
import com.radiology.health.app.service.mapper.RankMapper;
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
 * Integration tests for the {@link RankResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RankResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ranks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private RankMapper rankMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRankMockMvc;

    private Rank rank;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rank createEntity(EntityManager em) {
        Rank rank = new Rank().name(DEFAULT_NAME);
        // Add required entity
        Services services;
        if (TestUtil.findAll(em, Services.class).isEmpty()) {
            services = ServicesResourceIT.createEntity(em);
            em.persist(services);
            em.flush();
        } else {
            services = TestUtil.findAll(em, Services.class).get(0);
        }
        rank.setServices(services);
        return rank;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rank createUpdatedEntity(EntityManager em) {
        Rank rank = new Rank().name(UPDATED_NAME);
        // Add required entity
        Services services;
        if (TestUtil.findAll(em, Services.class).isEmpty()) {
            services = ServicesResourceIT.createUpdatedEntity(em);
            em.persist(services);
            em.flush();
        } else {
            services = TestUtil.findAll(em, Services.class).get(0);
        }
        rank.setServices(services);
        return rank;
    }

    @BeforeEach
    public void initTest() {
        rank = createEntity(em);
    }

    @Test
    @Transactional
    void createRank() throws Exception {
        int databaseSizeBeforeCreate = rankRepository.findAll().size();
        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);
        restRankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rankDTO)))
            .andExpect(status().isCreated());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeCreate + 1);
        Rank testRank = rankList.get(rankList.size() - 1);
        assertThat(testRank.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createRankWithExistingId() throws Exception {
        // Create the Rank with an existing ID
        rank.setId(1L);
        RankDTO rankDTO = rankMapper.toDto(rank);

        int databaseSizeBeforeCreate = rankRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rankRepository.findAll().size();
        // set the field null
        rank.setName(null);

        // Create the Rank, which fails.
        RankDTO rankDTO = rankMapper.toDto(rank);

        restRankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rankDTO)))
            .andExpect(status().isBadRequest());

        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRanks() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        // Get all the rankList
        restRankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rank.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRank() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        // Get the rank
        restRankMockMvc
            .perform(get(ENTITY_API_URL_ID, rank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rank.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getRanksByIdFiltering() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        Long id = rank.getId();

        defaultRankShouldBeFound("id.equals=" + id);
        defaultRankShouldNotBeFound("id.notEquals=" + id);

        defaultRankShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRankShouldNotBeFound("id.greaterThan=" + id);

        defaultRankShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRankShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRanksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        // Get all the rankList where name equals to DEFAULT_NAME
        defaultRankShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the rankList where name equals to UPDATED_NAME
        defaultRankShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRanksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        // Get all the rankList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRankShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the rankList where name equals to UPDATED_NAME
        defaultRankShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRanksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        // Get all the rankList where name is not null
        defaultRankShouldBeFound("name.specified=true");

        // Get all the rankList where name is null
        defaultRankShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllRanksByNameContainsSomething() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        // Get all the rankList where name contains DEFAULT_NAME
        defaultRankShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the rankList where name contains UPDATED_NAME
        defaultRankShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRanksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        // Get all the rankList where name does not contain DEFAULT_NAME
        defaultRankShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the rankList where name does not contain UPDATED_NAME
        defaultRankShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRanksByServicesIsEqualToSomething() throws Exception {
        Services services;
        if (TestUtil.findAll(em, Services.class).isEmpty()) {
            rankRepository.saveAndFlush(rank);
            services = ServicesResourceIT.createEntity(em);
        } else {
            services = TestUtil.findAll(em, Services.class).get(0);
        }
        em.persist(services);
        em.flush();
        rank.setServices(services);
        rankRepository.saveAndFlush(rank);
        Long servicesId = services.getId();
        // Get all the rankList where services equals to servicesId
        defaultRankShouldBeFound("servicesId.equals=" + servicesId);

        // Get all the rankList where services equals to (servicesId + 1)
        defaultRankShouldNotBeFound("servicesId.equals=" + (servicesId + 1));
    }

    @Test
    @Transactional
    void getAllRanksByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            rankRepository.saveAndFlush(rank);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        rank.addEmployee(employee);
        rankRepository.saveAndFlush(rank);
        Long employeeId = employee.getId();
        // Get all the rankList where employee equals to employeeId
        defaultRankShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the rankList where employee equals to (employeeId + 1)
        defaultRankShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRankShouldBeFound(String filter) throws Exception {
        restRankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rank.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restRankMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRankShouldNotBeFound(String filter) throws Exception {
        restRankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRankMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRank() throws Exception {
        // Get the rank
        restRankMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRank() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        int databaseSizeBeforeUpdate = rankRepository.findAll().size();

        // Update the rank
        Rank updatedRank = rankRepository.findById(rank.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRank are not directly saved in db
        em.detach(updatedRank);
        updatedRank.name(UPDATED_NAME);
        RankDTO rankDTO = rankMapper.toDto(updatedRank);

        restRankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
        Rank testRank = rankList.get(rankList.size() - 1);
        assertThat(testRank.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingRank() throws Exception {
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();
        rank.setId(longCount.incrementAndGet());

        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRank() throws Exception {
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();
        rank.setId(longCount.incrementAndGet());

        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRank() throws Exception {
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();
        rank.setId(longCount.incrementAndGet());

        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRankMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rankDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRankWithPatch() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        int databaseSizeBeforeUpdate = rankRepository.findAll().size();

        // Update the rank using partial update
        Rank partialUpdatedRank = new Rank();
        partialUpdatedRank.setId(rank.getId());

        restRankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRank.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRank))
            )
            .andExpect(status().isOk());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
        Rank testRank = rankList.get(rankList.size() - 1);
        assertThat(testRank.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateRankWithPatch() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        int databaseSizeBeforeUpdate = rankRepository.findAll().size();

        // Update the rank using partial update
        Rank partialUpdatedRank = new Rank();
        partialUpdatedRank.setId(rank.getId());

        partialUpdatedRank.name(UPDATED_NAME);

        restRankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRank.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRank))
            )
            .andExpect(status().isOk());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
        Rank testRank = rankList.get(rankList.size() - 1);
        assertThat(testRank.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingRank() throws Exception {
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();
        rank.setId(longCount.incrementAndGet());

        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rankDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRank() throws Exception {
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();
        rank.setId(longCount.incrementAndGet());

        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRank() throws Exception {
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();
        rank.setId(longCount.incrementAndGet());

        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRankMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rankDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRank() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        int databaseSizeBeforeDelete = rankRepository.findAll().size();

        // Delete the rank
        restRankMockMvc
            .perform(delete(ENTITY_API_URL_ID, rank.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
