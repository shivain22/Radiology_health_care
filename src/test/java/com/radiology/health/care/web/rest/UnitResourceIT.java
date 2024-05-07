package com.radiology.health.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.care.IntegrationTest;
import com.radiology.health.care.domain.EmpService;
import com.radiology.health.care.domain.Employee;
import com.radiology.health.care.domain.Unit;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.repository.UnitRepository;
import com.radiology.health.care.service.dto.UnitDTO;
import com.radiology.health.care.service.mapper.UnitMapper;
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
 * Integration tests for the {@link UnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitMapper unitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnitMockMvc;

    private Unit unit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unit createEntity(EntityManager em) {
        Unit unit = new Unit().name(DEFAULT_NAME);
        // Add required entity
        EmpService empService;
        if (TestUtil.findAll(em, EmpService.class).isEmpty()) {
            empService = EmpServiceResourceIT.createEntity(em);
            em.persist(empService);
            em.flush();
        } else {
            empService = TestUtil.findAll(em, EmpService.class).get(0);
        }
        unit.setEmpService(empService);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        unit.setUser(user);
        return unit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unit createUpdatedEntity(EntityManager em) {
        Unit unit = new Unit().name(UPDATED_NAME);
        // Add required entity
        EmpService empService;
        if (TestUtil.findAll(em, EmpService.class).isEmpty()) {
            empService = EmpServiceResourceIT.createUpdatedEntity(em);
            em.persist(empService);
            em.flush();
        } else {
            empService = TestUtil.findAll(em, EmpService.class).get(0);
        }
        unit.setEmpService(empService);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        unit.setUser(user);
        return unit;
    }

    @BeforeEach
    public void initTest() {
        unit = createEntity(em);
    }

    @Test
    @Transactional
    void createUnit() throws Exception {
        int databaseSizeBeforeCreate = unitRepository.findAll().size();
        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);
        restUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isCreated());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate + 1);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createUnitWithExistingId() throws Exception {
        // Create the Unit with an existing ID
        unit.setId(1L);
        UnitDTO unitDTO = unitMapper.toDto(unit);

        int databaseSizeBeforeCreate = unitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setName(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUnits() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get the unit
        restUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getUnitsByIdFiltering() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        Long id = unit.getId();

        defaultUnitShouldBeFound("id.equals=" + id);
        defaultUnitShouldNotBeFound("id.notEquals=" + id);

        defaultUnitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUnitShouldNotBeFound("id.greaterThan=" + id);

        defaultUnitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUnitShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUnitsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where name equals to DEFAULT_NAME
        defaultUnitShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the unitList where name equals to UPDATED_NAME
        defaultUnitShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUnitsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUnitShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the unitList where name equals to UPDATED_NAME
        defaultUnitShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUnitsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where name is not null
        defaultUnitShouldBeFound("name.specified=true");

        // Get all the unitList where name is null
        defaultUnitShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllUnitsByNameContainsSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where name contains DEFAULT_NAME
        defaultUnitShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the unitList where name contains UPDATED_NAME
        defaultUnitShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUnitsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where name does not contain DEFAULT_NAME
        defaultUnitShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the unitList where name does not contain UPDATED_NAME
        defaultUnitShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUnitsByEmpServiceIsEqualToSomething() throws Exception {
        EmpService empService;
        if (TestUtil.findAll(em, EmpService.class).isEmpty()) {
            unitRepository.saveAndFlush(unit);
            empService = EmpServiceResourceIT.createEntity(em);
        } else {
            empService = TestUtil.findAll(em, EmpService.class).get(0);
        }
        em.persist(empService);
        em.flush();
        unit.setEmpService(empService);
        unitRepository.saveAndFlush(unit);
        Long empServiceId = empService.getId();
        // Get all the unitList where empService equals to empServiceId
        defaultUnitShouldBeFound("empServiceId.equals=" + empServiceId);

        // Get all the unitList where empService equals to (empServiceId + 1)
        defaultUnitShouldNotBeFound("empServiceId.equals=" + (empServiceId + 1));
    }

    @Test
    @Transactional
    void getAllUnitsByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            unitRepository.saveAndFlush(unit);
            user = UserResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        unit.setUser(user);
        unitRepository.saveAndFlush(unit);
        Long userId = user.getId();
        // Get all the unitList where user equals to userId
        defaultUnitShouldBeFound("userId.equals=" + userId);

        // Get all the unitList where user equals to (userId + 1)
        defaultUnitShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllUnitsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            unitRepository.saveAndFlush(unit);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        unit.addEmployee(employee);
        unitRepository.saveAndFlush(unit);
        Long employeeId = employee.getId();
        // Get all the unitList where employee equals to employeeId
        defaultUnitShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the unitList where employee equals to (employeeId + 1)
        defaultUnitShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUnitShouldBeFound(String filter) throws Exception {
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUnitShouldNotBeFound(String filter) throws Exception {
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUnit() throws Exception {
        // Get the unit
        restUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Update the unit
        Unit updatedUnit = unitRepository.findById(unit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUnit are not directly saved in db
        em.detach(updatedUnit);
        updatedUnit.name(UPDATED_NAME);
        UnitDTO unitDTO = unitMapper.toDto(updatedUnit);

        restUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unitDTO))
            )
            .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();
        unit.setId(longCount.incrementAndGet());

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();
        unit.setId(longCount.incrementAndGet());

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();
        unit.setId(longCount.incrementAndGet());

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUnitWithPatch() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Update the unit using partial update
        Unit partialUpdatedUnit = new Unit();
        partialUpdatedUnit.setId(unit.getId());

        restUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnit))
            )
            .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateUnitWithPatch() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Update the unit using partial update
        Unit partialUpdatedUnit = new Unit();
        partialUpdatedUnit.setId(unit.getId());

        partialUpdatedUnit.name(UPDATED_NAME);

        restUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnit))
            )
            .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();
        unit.setId(longCount.incrementAndGet());

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();
        unit.setId(longCount.incrementAndGet());

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();
        unit.setId(longCount.incrementAndGet());

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        int databaseSizeBeforeDelete = unitRepository.findAll().size();

        // Delete the unit
        restUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, unit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
