package com.radiology.health.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.app.IntegrationTest;
import com.radiology.health.app.domain.Employee;
import com.radiology.health.app.domain.Equipments;
import com.radiology.health.app.domain.TechicianEquipmentMapping;
import com.radiology.health.app.repository.TechicianEquipmentMappingRepository;
import com.radiology.health.app.service.dto.TechicianEquipmentMappingDTO;
import com.radiology.health.app.service.mapper.TechicianEquipmentMappingMapper;
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
 * Integration tests for the {@link TechicianEquipmentMappingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TechicianEquipmentMappingResourceIT {

    private static final String DEFAULT_DATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_DATE_TIME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/techician-equipment-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TechicianEquipmentMappingRepository techicianEquipmentMappingRepository;

    @Autowired
    private TechicianEquipmentMappingMapper techicianEquipmentMappingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTechicianEquipmentMappingMockMvc;

    private TechicianEquipmentMapping techicianEquipmentMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechicianEquipmentMapping createEntity(EntityManager em) {
        TechicianEquipmentMapping techicianEquipmentMapping = new TechicianEquipmentMapping().dateTime(DEFAULT_DATE_TIME);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        techicianEquipmentMapping.setEmployee(employee);
        // Add required entity
        Equipments equipments;
        if (TestUtil.findAll(em, Equipments.class).isEmpty()) {
            equipments = EquipmentsResourceIT.createEntity(em);
            em.persist(equipments);
            em.flush();
        } else {
            equipments = TestUtil.findAll(em, Equipments.class).get(0);
        }
        techicianEquipmentMapping.setEquipments(equipments);
        return techicianEquipmentMapping;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechicianEquipmentMapping createUpdatedEntity(EntityManager em) {
        TechicianEquipmentMapping techicianEquipmentMapping = new TechicianEquipmentMapping().dateTime(UPDATED_DATE_TIME);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createUpdatedEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        techicianEquipmentMapping.setEmployee(employee);
        // Add required entity
        Equipments equipments;
        if (TestUtil.findAll(em, Equipments.class).isEmpty()) {
            equipments = EquipmentsResourceIT.createUpdatedEntity(em);
            em.persist(equipments);
            em.flush();
        } else {
            equipments = TestUtil.findAll(em, Equipments.class).get(0);
        }
        techicianEquipmentMapping.setEquipments(equipments);
        return techicianEquipmentMapping;
    }

    @BeforeEach
    public void initTest() {
        techicianEquipmentMapping = createEntity(em);
    }

    @Test
    @Transactional
    void createTechicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeCreate = techicianEquipmentMappingRepository.findAll().size();
        // Create the TechicianEquipmentMapping
        TechicianEquipmentMappingDTO techicianEquipmentMappingDTO = techicianEquipmentMappingMapper.toDto(techicianEquipmentMapping);
        restTechicianEquipmentMappingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(techicianEquipmentMappingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TechicianEquipmentMapping in the database
        List<TechicianEquipmentMapping> techicianEquipmentMappingList = techicianEquipmentMappingRepository.findAll();
        assertThat(techicianEquipmentMappingList).hasSize(databaseSizeBeforeCreate + 1);
        TechicianEquipmentMapping testTechicianEquipmentMapping = techicianEquipmentMappingList.get(
            techicianEquipmentMappingList.size() - 1
        );
        assertThat(testTechicianEquipmentMapping.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
    }

    @Test
    @Transactional
    void createTechicianEquipmentMappingWithExistingId() throws Exception {
        // Create the TechicianEquipmentMapping with an existing ID
        techicianEquipmentMapping.setId(1L);
        TechicianEquipmentMappingDTO techicianEquipmentMappingDTO = techicianEquipmentMappingMapper.toDto(techicianEquipmentMapping);

        int databaseSizeBeforeCreate = techicianEquipmentMappingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechicianEquipmentMappingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(techicianEquipmentMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TechicianEquipmentMapping in the database
        List<TechicianEquipmentMapping> techicianEquipmentMappingList = techicianEquipmentMappingRepository.findAll();
        assertThat(techicianEquipmentMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = techicianEquipmentMappingRepository.findAll().size();
        // set the field null
        techicianEquipmentMapping.setDateTime(null);

        // Create the TechicianEquipmentMapping, which fails.
        TechicianEquipmentMappingDTO techicianEquipmentMappingDTO = techicianEquipmentMappingMapper.toDto(techicianEquipmentMapping);

        restTechicianEquipmentMappingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(techicianEquipmentMappingDTO))
            )
            .andExpect(status().isBadRequest());

        List<TechicianEquipmentMapping> techicianEquipmentMappingList = techicianEquipmentMappingRepository.findAll();
        assertThat(techicianEquipmentMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTechicianEquipmentMappings() throws Exception {
        // Initialize the database
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);

        // Get all the techicianEquipmentMappingList
        restTechicianEquipmentMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(techicianEquipmentMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME)));
    }

    @Test
    @Transactional
    void getTechicianEquipmentMapping() throws Exception {
        // Initialize the database
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);

        // Get the techicianEquipmentMapping
        restTechicianEquipmentMappingMockMvc
            .perform(get(ENTITY_API_URL_ID, techicianEquipmentMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(techicianEquipmentMapping.getId().intValue()))
            .andExpect(jsonPath("$.dateTime").value(DEFAULT_DATE_TIME));
    }

    @Test
    @Transactional
    void getTechicianEquipmentMappingsByIdFiltering() throws Exception {
        // Initialize the database
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);

        Long id = techicianEquipmentMapping.getId();

        defaultTechicianEquipmentMappingShouldBeFound("id.equals=" + id);
        defaultTechicianEquipmentMappingShouldNotBeFound("id.notEquals=" + id);

        defaultTechicianEquipmentMappingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTechicianEquipmentMappingShouldNotBeFound("id.greaterThan=" + id);

        defaultTechicianEquipmentMappingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTechicianEquipmentMappingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTechicianEquipmentMappingsByDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);

        // Get all the techicianEquipmentMappingList where dateTime equals to DEFAULT_DATE_TIME
        defaultTechicianEquipmentMappingShouldBeFound("dateTime.equals=" + DEFAULT_DATE_TIME);

        // Get all the techicianEquipmentMappingList where dateTime equals to UPDATED_DATE_TIME
        defaultTechicianEquipmentMappingShouldNotBeFound("dateTime.equals=" + UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllTechicianEquipmentMappingsByDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);

        // Get all the techicianEquipmentMappingList where dateTime in DEFAULT_DATE_TIME or UPDATED_DATE_TIME
        defaultTechicianEquipmentMappingShouldBeFound("dateTime.in=" + DEFAULT_DATE_TIME + "," + UPDATED_DATE_TIME);

        // Get all the techicianEquipmentMappingList where dateTime equals to UPDATED_DATE_TIME
        defaultTechicianEquipmentMappingShouldNotBeFound("dateTime.in=" + UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllTechicianEquipmentMappingsByDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);

        // Get all the techicianEquipmentMappingList where dateTime is not null
        defaultTechicianEquipmentMappingShouldBeFound("dateTime.specified=true");

        // Get all the techicianEquipmentMappingList where dateTime is null
        defaultTechicianEquipmentMappingShouldNotBeFound("dateTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTechicianEquipmentMappingsByDateTimeContainsSomething() throws Exception {
        // Initialize the database
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);

        // Get all the techicianEquipmentMappingList where dateTime contains DEFAULT_DATE_TIME
        defaultTechicianEquipmentMappingShouldBeFound("dateTime.contains=" + DEFAULT_DATE_TIME);

        // Get all the techicianEquipmentMappingList where dateTime contains UPDATED_DATE_TIME
        defaultTechicianEquipmentMappingShouldNotBeFound("dateTime.contains=" + UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllTechicianEquipmentMappingsByDateTimeNotContainsSomething() throws Exception {
        // Initialize the database
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);

        // Get all the techicianEquipmentMappingList where dateTime does not contain DEFAULT_DATE_TIME
        defaultTechicianEquipmentMappingShouldNotBeFound("dateTime.doesNotContain=" + DEFAULT_DATE_TIME);

        // Get all the techicianEquipmentMappingList where dateTime does not contain UPDATED_DATE_TIME
        defaultTechicianEquipmentMappingShouldBeFound("dateTime.doesNotContain=" + UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllTechicianEquipmentMappingsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        techicianEquipmentMapping.setEmployee(employee);
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);
        Long employeeId = employee.getId();
        // Get all the techicianEquipmentMappingList where employee equals to employeeId
        defaultTechicianEquipmentMappingShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the techicianEquipmentMappingList where employee equals to (employeeId + 1)
        defaultTechicianEquipmentMappingShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllTechicianEquipmentMappingsByEquipmentsIsEqualToSomething() throws Exception {
        Equipments equipments;
        if (TestUtil.findAll(em, Equipments.class).isEmpty()) {
            techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);
            equipments = EquipmentsResourceIT.createEntity(em);
        } else {
            equipments = TestUtil.findAll(em, Equipments.class).get(0);
        }
        em.persist(equipments);
        em.flush();
        techicianEquipmentMapping.setEquipments(equipments);
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);
        Long equipmentsId = equipments.getId();
        // Get all the techicianEquipmentMappingList where equipments equals to equipmentsId
        defaultTechicianEquipmentMappingShouldBeFound("equipmentsId.equals=" + equipmentsId);

        // Get all the techicianEquipmentMappingList where equipments equals to (equipmentsId + 1)
        defaultTechicianEquipmentMappingShouldNotBeFound("equipmentsId.equals=" + (equipmentsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTechicianEquipmentMappingShouldBeFound(String filter) throws Exception {
        restTechicianEquipmentMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(techicianEquipmentMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME)));

        // Check, that the count call also returns 1
        restTechicianEquipmentMappingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTechicianEquipmentMappingShouldNotBeFound(String filter) throws Exception {
        restTechicianEquipmentMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTechicianEquipmentMappingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTechicianEquipmentMapping() throws Exception {
        // Get the techicianEquipmentMapping
        restTechicianEquipmentMappingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTechicianEquipmentMapping() throws Exception {
        // Initialize the database
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);

        int databaseSizeBeforeUpdate = techicianEquipmentMappingRepository.findAll().size();

        // Update the techicianEquipmentMapping
        TechicianEquipmentMapping updatedTechicianEquipmentMapping = techicianEquipmentMappingRepository
            .findById(techicianEquipmentMapping.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTechicianEquipmentMapping are not directly saved in db
        em.detach(updatedTechicianEquipmentMapping);
        updatedTechicianEquipmentMapping.dateTime(UPDATED_DATE_TIME);
        TechicianEquipmentMappingDTO techicianEquipmentMappingDTO = techicianEquipmentMappingMapper.toDto(updatedTechicianEquipmentMapping);

        restTechicianEquipmentMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, techicianEquipmentMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(techicianEquipmentMappingDTO))
            )
            .andExpect(status().isOk());

        // Validate the TechicianEquipmentMapping in the database
        List<TechicianEquipmentMapping> techicianEquipmentMappingList = techicianEquipmentMappingRepository.findAll();
        assertThat(techicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
        TechicianEquipmentMapping testTechicianEquipmentMapping = techicianEquipmentMappingList.get(
            techicianEquipmentMappingList.size() - 1
        );
        assertThat(testTechicianEquipmentMapping.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingTechicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeUpdate = techicianEquipmentMappingRepository.findAll().size();
        techicianEquipmentMapping.setId(longCount.incrementAndGet());

        // Create the TechicianEquipmentMapping
        TechicianEquipmentMappingDTO techicianEquipmentMappingDTO = techicianEquipmentMappingMapper.toDto(techicianEquipmentMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechicianEquipmentMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, techicianEquipmentMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(techicianEquipmentMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TechicianEquipmentMapping in the database
        List<TechicianEquipmentMapping> techicianEquipmentMappingList = techicianEquipmentMappingRepository.findAll();
        assertThat(techicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTechicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeUpdate = techicianEquipmentMappingRepository.findAll().size();
        techicianEquipmentMapping.setId(longCount.incrementAndGet());

        // Create the TechicianEquipmentMapping
        TechicianEquipmentMappingDTO techicianEquipmentMappingDTO = techicianEquipmentMappingMapper.toDto(techicianEquipmentMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTechicianEquipmentMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(techicianEquipmentMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TechicianEquipmentMapping in the database
        List<TechicianEquipmentMapping> techicianEquipmentMappingList = techicianEquipmentMappingRepository.findAll();
        assertThat(techicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTechicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeUpdate = techicianEquipmentMappingRepository.findAll().size();
        techicianEquipmentMapping.setId(longCount.incrementAndGet());

        // Create the TechicianEquipmentMapping
        TechicianEquipmentMappingDTO techicianEquipmentMappingDTO = techicianEquipmentMappingMapper.toDto(techicianEquipmentMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTechicianEquipmentMappingMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(techicianEquipmentMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TechicianEquipmentMapping in the database
        List<TechicianEquipmentMapping> techicianEquipmentMappingList = techicianEquipmentMappingRepository.findAll();
        assertThat(techicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTechicianEquipmentMappingWithPatch() throws Exception {
        // Initialize the database
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);

        int databaseSizeBeforeUpdate = techicianEquipmentMappingRepository.findAll().size();

        // Update the techicianEquipmentMapping using partial update
        TechicianEquipmentMapping partialUpdatedTechicianEquipmentMapping = new TechicianEquipmentMapping();
        partialUpdatedTechicianEquipmentMapping.setId(techicianEquipmentMapping.getId());

        partialUpdatedTechicianEquipmentMapping.dateTime(UPDATED_DATE_TIME);

        restTechicianEquipmentMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTechicianEquipmentMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTechicianEquipmentMapping))
            )
            .andExpect(status().isOk());

        // Validate the TechicianEquipmentMapping in the database
        List<TechicianEquipmentMapping> techicianEquipmentMappingList = techicianEquipmentMappingRepository.findAll();
        assertThat(techicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
        TechicianEquipmentMapping testTechicianEquipmentMapping = techicianEquipmentMappingList.get(
            techicianEquipmentMappingList.size() - 1
        );
        assertThat(testTechicianEquipmentMapping.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateTechicianEquipmentMappingWithPatch() throws Exception {
        // Initialize the database
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);

        int databaseSizeBeforeUpdate = techicianEquipmentMappingRepository.findAll().size();

        // Update the techicianEquipmentMapping using partial update
        TechicianEquipmentMapping partialUpdatedTechicianEquipmentMapping = new TechicianEquipmentMapping();
        partialUpdatedTechicianEquipmentMapping.setId(techicianEquipmentMapping.getId());

        partialUpdatedTechicianEquipmentMapping.dateTime(UPDATED_DATE_TIME);

        restTechicianEquipmentMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTechicianEquipmentMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTechicianEquipmentMapping))
            )
            .andExpect(status().isOk());

        // Validate the TechicianEquipmentMapping in the database
        List<TechicianEquipmentMapping> techicianEquipmentMappingList = techicianEquipmentMappingRepository.findAll();
        assertThat(techicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
        TechicianEquipmentMapping testTechicianEquipmentMapping = techicianEquipmentMappingList.get(
            techicianEquipmentMappingList.size() - 1
        );
        assertThat(testTechicianEquipmentMapping.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingTechicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeUpdate = techicianEquipmentMappingRepository.findAll().size();
        techicianEquipmentMapping.setId(longCount.incrementAndGet());

        // Create the TechicianEquipmentMapping
        TechicianEquipmentMappingDTO techicianEquipmentMappingDTO = techicianEquipmentMappingMapper.toDto(techicianEquipmentMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechicianEquipmentMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, techicianEquipmentMappingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(techicianEquipmentMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TechicianEquipmentMapping in the database
        List<TechicianEquipmentMapping> techicianEquipmentMappingList = techicianEquipmentMappingRepository.findAll();
        assertThat(techicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTechicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeUpdate = techicianEquipmentMappingRepository.findAll().size();
        techicianEquipmentMapping.setId(longCount.incrementAndGet());

        // Create the TechicianEquipmentMapping
        TechicianEquipmentMappingDTO techicianEquipmentMappingDTO = techicianEquipmentMappingMapper.toDto(techicianEquipmentMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTechicianEquipmentMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(techicianEquipmentMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TechicianEquipmentMapping in the database
        List<TechicianEquipmentMapping> techicianEquipmentMappingList = techicianEquipmentMappingRepository.findAll();
        assertThat(techicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTechicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeUpdate = techicianEquipmentMappingRepository.findAll().size();
        techicianEquipmentMapping.setId(longCount.incrementAndGet());

        // Create the TechicianEquipmentMapping
        TechicianEquipmentMappingDTO techicianEquipmentMappingDTO = techicianEquipmentMappingMapper.toDto(techicianEquipmentMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTechicianEquipmentMappingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(techicianEquipmentMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TechicianEquipmentMapping in the database
        List<TechicianEquipmentMapping> techicianEquipmentMappingList = techicianEquipmentMappingRepository.findAll();
        assertThat(techicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTechicianEquipmentMapping() throws Exception {
        // Initialize the database
        techicianEquipmentMappingRepository.saveAndFlush(techicianEquipmentMapping);

        int databaseSizeBeforeDelete = techicianEquipmentMappingRepository.findAll().size();

        // Delete the techicianEquipmentMapping
        restTechicianEquipmentMappingMockMvc
            .perform(delete(ENTITY_API_URL_ID, techicianEquipmentMapping.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TechicianEquipmentMapping> techicianEquipmentMappingList = techicianEquipmentMappingRepository.findAll();
        assertThat(techicianEquipmentMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
