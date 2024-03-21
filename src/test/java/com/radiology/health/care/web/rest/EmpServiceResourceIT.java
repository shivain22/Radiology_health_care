package com.radiology.health.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.care.IntegrationTest;
import com.radiology.health.care.domain.EmpService;
import com.radiology.health.care.domain.Employee;
import com.radiology.health.care.domain.Rank;
import com.radiology.health.care.domain.Unit;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.repository.EmpServiceRepository;
import com.radiology.health.care.service.dto.EmpServiceDTO;
import com.radiology.health.care.service.mapper.EmpServiceMapper;
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
 * Integration tests for the {@link EmpServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpServiceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/emp-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmpServiceRepository empServiceRepository;

    @Autowired
    private EmpServiceMapper empServiceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpServiceMockMvc;

    private EmpService empService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpService createEntity(EntityManager em) {
        EmpService empService = new EmpService().name(DEFAULT_NAME);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        empService.setUser(user);
        return empService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpService createUpdatedEntity(EntityManager em) {
        EmpService empService = new EmpService().name(UPDATED_NAME);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        empService.setUser(user);
        return empService;
    }

    @BeforeEach
    public void initTest() {
        empService = createEntity(em);
    }

    @Test
    @Transactional
    void createEmpService() throws Exception {
        int databaseSizeBeforeCreate = empServiceRepository.findAll().size();
        // Create the EmpService
        EmpServiceDTO empServiceDTO = empServiceMapper.toDto(empService);
        restEmpServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empServiceDTO)))
            .andExpect(status().isCreated());

        // Validate the EmpService in the database
        List<EmpService> empServiceList = empServiceRepository.findAll();
        assertThat(empServiceList).hasSize(databaseSizeBeforeCreate + 1);
        EmpService testEmpService = empServiceList.get(empServiceList.size() - 1);
        assertThat(testEmpService.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createEmpServiceWithExistingId() throws Exception {
        // Create the EmpService with an existing ID
        empService.setId(1L);
        EmpServiceDTO empServiceDTO = empServiceMapper.toDto(empService);

        int databaseSizeBeforeCreate = empServiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmpService in the database
        List<EmpService> empServiceList = empServiceRepository.findAll();
        assertThat(empServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = empServiceRepository.findAll().size();
        // set the field null
        empService.setName(null);

        // Create the EmpService, which fails.
        EmpServiceDTO empServiceDTO = empServiceMapper.toDto(empService);

        restEmpServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empServiceDTO)))
            .andExpect(status().isBadRequest());

        List<EmpService> empServiceList = empServiceRepository.findAll();
        assertThat(empServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmpServices() throws Exception {
        // Initialize the database
        empServiceRepository.saveAndFlush(empService);

        // Get all the empServiceList
        restEmpServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getEmpService() throws Exception {
        // Initialize the database
        empServiceRepository.saveAndFlush(empService);

        // Get the empService
        restEmpServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, empService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getEmpServicesByIdFiltering() throws Exception {
        // Initialize the database
        empServiceRepository.saveAndFlush(empService);

        Long id = empService.getId();

        defaultEmpServiceShouldBeFound("id.equals=" + id);
        defaultEmpServiceShouldNotBeFound("id.notEquals=" + id);

        defaultEmpServiceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmpServiceShouldNotBeFound("id.greaterThan=" + id);

        defaultEmpServiceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmpServiceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmpServicesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        empServiceRepository.saveAndFlush(empService);

        // Get all the empServiceList where name equals to DEFAULT_NAME
        defaultEmpServiceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the empServiceList where name equals to UPDATED_NAME
        defaultEmpServiceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmpServicesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        empServiceRepository.saveAndFlush(empService);

        // Get all the empServiceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEmpServiceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the empServiceList where name equals to UPDATED_NAME
        defaultEmpServiceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmpServicesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        empServiceRepository.saveAndFlush(empService);

        // Get all the empServiceList where name is not null
        defaultEmpServiceShouldBeFound("name.specified=true");

        // Get all the empServiceList where name is null
        defaultEmpServiceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpServicesByNameContainsSomething() throws Exception {
        // Initialize the database
        empServiceRepository.saveAndFlush(empService);

        // Get all the empServiceList where name contains DEFAULT_NAME
        defaultEmpServiceShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the empServiceList where name contains UPDATED_NAME
        defaultEmpServiceShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmpServicesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        empServiceRepository.saveAndFlush(empService);

        // Get all the empServiceList where name does not contain DEFAULT_NAME
        defaultEmpServiceShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the empServiceList where name does not contain UPDATED_NAME
        defaultEmpServiceShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmpServicesByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            empServiceRepository.saveAndFlush(empService);
            user = UserResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        empService.setUser(user);
        empServiceRepository.saveAndFlush(empService);
        Long userId = user.getId();
        // Get all the empServiceList where user equals to userId
        defaultEmpServiceShouldBeFound("userId.equals=" + userId);

        // Get all the empServiceList where user equals to (userId + 1)
        defaultEmpServiceShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllEmpServicesByRankIsEqualToSomething() throws Exception {
        Rank rank;
        if (TestUtil.findAll(em, Rank.class).isEmpty()) {
            empServiceRepository.saveAndFlush(empService);
            rank = RankResourceIT.createEntity(em);
        } else {
            rank = TestUtil.findAll(em, Rank.class).get(0);
        }
        em.persist(rank);
        em.flush();
        empService.addRank(rank);
        empServiceRepository.saveAndFlush(empService);
        Long rankId = rank.getId();
        // Get all the empServiceList where rank equals to rankId
        defaultEmpServiceShouldBeFound("rankId.equals=" + rankId);

        // Get all the empServiceList where rank equals to (rankId + 1)
        defaultEmpServiceShouldNotBeFound("rankId.equals=" + (rankId + 1));
    }

    @Test
    @Transactional
    void getAllEmpServicesByUnitIsEqualToSomething() throws Exception {
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            empServiceRepository.saveAndFlush(empService);
            unit = UnitResourceIT.createEntity(em);
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        em.persist(unit);
        em.flush();
        empService.addUnit(unit);
        empServiceRepository.saveAndFlush(empService);
        Long unitId = unit.getId();
        // Get all the empServiceList where unit equals to unitId
        defaultEmpServiceShouldBeFound("unitId.equals=" + unitId);

        // Get all the empServiceList where unit equals to (unitId + 1)
        defaultEmpServiceShouldNotBeFound("unitId.equals=" + (unitId + 1));
    }

    @Test
    @Transactional
    void getAllEmpServicesByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            empServiceRepository.saveAndFlush(empService);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        empService.addEmployee(employee);
        empServiceRepository.saveAndFlush(empService);
        Long employeeId = employee.getId();
        // Get all the empServiceList where employee equals to employeeId
        defaultEmpServiceShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the empServiceList where employee equals to (employeeId + 1)
        defaultEmpServiceShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmpServiceShouldBeFound(String filter) throws Exception {
        restEmpServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restEmpServiceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmpServiceShouldNotBeFound(String filter) throws Exception {
        restEmpServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmpServiceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmpService() throws Exception {
        // Get the empService
        restEmpServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmpService() throws Exception {
        // Initialize the database
        empServiceRepository.saveAndFlush(empService);

        int databaseSizeBeforeUpdate = empServiceRepository.findAll().size();

        // Update the empService
        EmpService updatedEmpService = empServiceRepository.findById(empService.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmpService are not directly saved in db
        em.detach(updatedEmpService);
        updatedEmpService.name(UPDATED_NAME);
        EmpServiceDTO empServiceDTO = empServiceMapper.toDto(updatedEmpService);

        restEmpServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empServiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empServiceDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmpService in the database
        List<EmpService> empServiceList = empServiceRepository.findAll();
        assertThat(empServiceList).hasSize(databaseSizeBeforeUpdate);
        EmpService testEmpService = empServiceList.get(empServiceList.size() - 1);
        assertThat(testEmpService.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingEmpService() throws Exception {
        int databaseSizeBeforeUpdate = empServiceRepository.findAll().size();
        empService.setId(longCount.incrementAndGet());

        // Create the EmpService
        EmpServiceDTO empServiceDTO = empServiceMapper.toDto(empService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empServiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpService in the database
        List<EmpService> empServiceList = empServiceRepository.findAll();
        assertThat(empServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpService() throws Exception {
        int databaseSizeBeforeUpdate = empServiceRepository.findAll().size();
        empService.setId(longCount.incrementAndGet());

        // Create the EmpService
        EmpServiceDTO empServiceDTO = empServiceMapper.toDto(empService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpService in the database
        List<EmpService> empServiceList = empServiceRepository.findAll();
        assertThat(empServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpService() throws Exception {
        int databaseSizeBeforeUpdate = empServiceRepository.findAll().size();
        empService.setId(longCount.incrementAndGet());

        // Create the EmpService
        EmpServiceDTO empServiceDTO = empServiceMapper.toDto(empService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpServiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empServiceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpService in the database
        List<EmpService> empServiceList = empServiceRepository.findAll();
        assertThat(empServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpServiceWithPatch() throws Exception {
        // Initialize the database
        empServiceRepository.saveAndFlush(empService);

        int databaseSizeBeforeUpdate = empServiceRepository.findAll().size();

        // Update the empService using partial update
        EmpService partialUpdatedEmpService = new EmpService();
        partialUpdatedEmpService.setId(empService.getId());

        restEmpServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpService))
            )
            .andExpect(status().isOk());

        // Validate the EmpService in the database
        List<EmpService> empServiceList = empServiceRepository.findAll();
        assertThat(empServiceList).hasSize(databaseSizeBeforeUpdate);
        EmpService testEmpService = empServiceList.get(empServiceList.size() - 1);
        assertThat(testEmpService.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateEmpServiceWithPatch() throws Exception {
        // Initialize the database
        empServiceRepository.saveAndFlush(empService);

        int databaseSizeBeforeUpdate = empServiceRepository.findAll().size();

        // Update the empService using partial update
        EmpService partialUpdatedEmpService = new EmpService();
        partialUpdatedEmpService.setId(empService.getId());

        partialUpdatedEmpService.name(UPDATED_NAME);

        restEmpServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpService))
            )
            .andExpect(status().isOk());

        // Validate the EmpService in the database
        List<EmpService> empServiceList = empServiceRepository.findAll();
        assertThat(empServiceList).hasSize(databaseSizeBeforeUpdate);
        EmpService testEmpService = empServiceList.get(empServiceList.size() - 1);
        assertThat(testEmpService.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingEmpService() throws Exception {
        int databaseSizeBeforeUpdate = empServiceRepository.findAll().size();
        empService.setId(longCount.incrementAndGet());

        // Create the EmpService
        EmpServiceDTO empServiceDTO = empServiceMapper.toDto(empService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empServiceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpService in the database
        List<EmpService> empServiceList = empServiceRepository.findAll();
        assertThat(empServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpService() throws Exception {
        int databaseSizeBeforeUpdate = empServiceRepository.findAll().size();
        empService.setId(longCount.incrementAndGet());

        // Create the EmpService
        EmpServiceDTO empServiceDTO = empServiceMapper.toDto(empService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpService in the database
        List<EmpService> empServiceList = empServiceRepository.findAll();
        assertThat(empServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpService() throws Exception {
        int databaseSizeBeforeUpdate = empServiceRepository.findAll().size();
        empService.setId(longCount.incrementAndGet());

        // Create the EmpService
        EmpServiceDTO empServiceDTO = empServiceMapper.toDto(empService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpServiceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(empServiceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpService in the database
        List<EmpService> empServiceList = empServiceRepository.findAll();
        assertThat(empServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpService() throws Exception {
        // Initialize the database
        empServiceRepository.saveAndFlush(empService);

        int databaseSizeBeforeDelete = empServiceRepository.findAll().size();

        // Delete the empService
        restEmpServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, empService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmpService> empServiceList = empServiceRepository.findAll();
        assertThat(empServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
