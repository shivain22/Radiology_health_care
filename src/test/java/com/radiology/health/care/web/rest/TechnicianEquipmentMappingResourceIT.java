package com.radiology.health.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.care.IntegrationTest;
import com.radiology.health.care.domain.Employee;
import com.radiology.health.care.domain.Equipment;
import com.radiology.health.care.domain.TechnicianEquipmentMapping;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.repository.TechnicianEquipmentMappingRepository;
import com.radiology.health.care.service.dto.TechnicianEquipmentMappingDTO;
import com.radiology.health.care.service.mapper.TechnicianEquipmentMappingMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link TechnicianEquipmentMappingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TechnicianEquipmentMappingResourceIT {

    private static final Instant DEFAULT_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/technician-equipment-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TechnicianEquipmentMappingRepository technicianEquipmentMappingRepository;

    @Autowired
    private TechnicianEquipmentMappingMapper technicianEquipmentMappingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTechnicianEquipmentMappingMockMvc;

    private TechnicianEquipmentMapping technicianEquipmentMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechnicianEquipmentMapping createEntity(EntityManager em) {
        TechnicianEquipmentMapping technicianEquipmentMapping = new TechnicianEquipmentMapping().dateTime(DEFAULT_DATE_TIME);
        // Add required entity
        Equipment equipment;
        if (TestUtil.findAll(em, Equipment.class).isEmpty()) {
            equipment = EquipmentResourceIT.createEntity(em);
            em.persist(equipment);
            em.flush();
        } else {
            equipment = TestUtil.findAll(em, Equipment.class).get(0);
        }
        technicianEquipmentMapping.setEquipment(equipment);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        technicianEquipmentMapping.setEmployee(employee);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        technicianEquipmentMapping.setUser(user);
        return technicianEquipmentMapping;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechnicianEquipmentMapping createUpdatedEntity(EntityManager em) {
        TechnicianEquipmentMapping technicianEquipmentMapping = new TechnicianEquipmentMapping().dateTime(UPDATED_DATE_TIME);
        // Add required entity
        Equipment equipment;
        if (TestUtil.findAll(em, Equipment.class).isEmpty()) {
            equipment = EquipmentResourceIT.createUpdatedEntity(em);
            em.persist(equipment);
            em.flush();
        } else {
            equipment = TestUtil.findAll(em, Equipment.class).get(0);
        }
        technicianEquipmentMapping.setEquipment(equipment);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createUpdatedEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        technicianEquipmentMapping.setEmployee(employee);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        technicianEquipmentMapping.setUser(user);
        return technicianEquipmentMapping;
    }

    @BeforeEach
    public void initTest() {
        technicianEquipmentMapping = createEntity(em);
    }

    @Test
    @Transactional
    void createTechnicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeCreate = technicianEquipmentMappingRepository.findAll().size();
        // Create the TechnicianEquipmentMapping
        TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO = technicianEquipmentMappingMapper.toDto(technicianEquipmentMapping);
        restTechnicianEquipmentMappingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(technicianEquipmentMappingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TechnicianEquipmentMapping in the database
        List<TechnicianEquipmentMapping> technicianEquipmentMappingList = technicianEquipmentMappingRepository.findAll();
        assertThat(technicianEquipmentMappingList).hasSize(databaseSizeBeforeCreate + 1);
        TechnicianEquipmentMapping testTechnicianEquipmentMapping = technicianEquipmentMappingList.get(
            technicianEquipmentMappingList.size() - 1
        );
        assertThat(testTechnicianEquipmentMapping.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
    }

    @Test
    @Transactional
    void createTechnicianEquipmentMappingWithExistingId() throws Exception {
        // Create the TechnicianEquipmentMapping with an existing ID
        technicianEquipmentMapping.setId(1L);
        TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO = technicianEquipmentMappingMapper.toDto(technicianEquipmentMapping);

        int databaseSizeBeforeCreate = technicianEquipmentMappingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechnicianEquipmentMappingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(technicianEquipmentMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TechnicianEquipmentMapping in the database
        List<TechnicianEquipmentMapping> technicianEquipmentMappingList = technicianEquipmentMappingRepository.findAll();
        assertThat(technicianEquipmentMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = technicianEquipmentMappingRepository.findAll().size();
        // set the field null
        technicianEquipmentMapping.setDateTime(null);

        // Create the TechnicianEquipmentMapping, which fails.
        TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO = technicianEquipmentMappingMapper.toDto(technicianEquipmentMapping);

        restTechnicianEquipmentMappingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(technicianEquipmentMappingDTO))
            )
            .andExpect(status().isBadRequest());

        List<TechnicianEquipmentMapping> technicianEquipmentMappingList = technicianEquipmentMappingRepository.findAll();
        assertThat(technicianEquipmentMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTechnicianEquipmentMappings() throws Exception {
        // Initialize the database
        technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);

        // Get all the technicianEquipmentMappingList
        restTechnicianEquipmentMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(technicianEquipmentMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME.toString())));
    }

    @Test
    @Transactional
    void getTechnicianEquipmentMapping() throws Exception {
        // Initialize the database
        technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);

        // Get the technicianEquipmentMapping
        restTechnicianEquipmentMappingMockMvc
            .perform(get(ENTITY_API_URL_ID, technicianEquipmentMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(technicianEquipmentMapping.getId().intValue()))
            .andExpect(jsonPath("$.dateTime").value(DEFAULT_DATE_TIME.toString()));
    }

    @Test
    @Transactional
    void getTechnicianEquipmentMappingsByIdFiltering() throws Exception {
        // Initialize the database
        technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);

        Long id = technicianEquipmentMapping.getId();

        defaultTechnicianEquipmentMappingShouldBeFound("id.equals=" + id);
        defaultTechnicianEquipmentMappingShouldNotBeFound("id.notEquals=" + id);

        defaultTechnicianEquipmentMappingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTechnicianEquipmentMappingShouldNotBeFound("id.greaterThan=" + id);

        defaultTechnicianEquipmentMappingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTechnicianEquipmentMappingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTechnicianEquipmentMappingsByDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);

        // Get all the technicianEquipmentMappingList where dateTime equals to DEFAULT_DATE_TIME
        defaultTechnicianEquipmentMappingShouldBeFound("dateTime.equals=" + DEFAULT_DATE_TIME);

        // Get all the technicianEquipmentMappingList where dateTime equals to UPDATED_DATE_TIME
        defaultTechnicianEquipmentMappingShouldNotBeFound("dateTime.equals=" + UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllTechnicianEquipmentMappingsByDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);

        // Get all the technicianEquipmentMappingList where dateTime in DEFAULT_DATE_TIME or UPDATED_DATE_TIME
        defaultTechnicianEquipmentMappingShouldBeFound("dateTime.in=" + DEFAULT_DATE_TIME + "," + UPDATED_DATE_TIME);

        // Get all the technicianEquipmentMappingList where dateTime equals to UPDATED_DATE_TIME
        defaultTechnicianEquipmentMappingShouldNotBeFound("dateTime.in=" + UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllTechnicianEquipmentMappingsByDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);

        // Get all the technicianEquipmentMappingList where dateTime is not null
        defaultTechnicianEquipmentMappingShouldBeFound("dateTime.specified=true");

        // Get all the technicianEquipmentMappingList where dateTime is null
        defaultTechnicianEquipmentMappingShouldNotBeFound("dateTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTechnicianEquipmentMappingsByEquipmentIsEqualToSomething() throws Exception {
        Equipment equipment;
        if (TestUtil.findAll(em, Equipment.class).isEmpty()) {
            technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);
            equipment = EquipmentResourceIT.createEntity(em);
        } else {
            equipment = TestUtil.findAll(em, Equipment.class).get(0);
        }
        em.persist(equipment);
        em.flush();
        technicianEquipmentMapping.setEquipment(equipment);
        technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);
        Long equipmentId = equipment.getId();
        // Get all the technicianEquipmentMappingList where equipment equals to equipmentId
        defaultTechnicianEquipmentMappingShouldBeFound("equipmentId.equals=" + equipmentId);

        // Get all the technicianEquipmentMappingList where equipment equals to (equipmentId + 1)
        defaultTechnicianEquipmentMappingShouldNotBeFound("equipmentId.equals=" + (equipmentId + 1));
    }

    @Test
    @Transactional
    void getAllTechnicianEquipmentMappingsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        technicianEquipmentMapping.setEmployee(employee);
        technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);
        Long employeeId = employee.getId();
        // Get all the technicianEquipmentMappingList where employee equals to employeeId
        defaultTechnicianEquipmentMappingShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the technicianEquipmentMappingList where employee equals to (employeeId + 1)
        defaultTechnicianEquipmentMappingShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllTechnicianEquipmentMappingsByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);
            user = UserResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        technicianEquipmentMapping.setUser(user);
        technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);
        Long userId = user.getId();
        // Get all the technicianEquipmentMappingList where user equals to userId
        defaultTechnicianEquipmentMappingShouldBeFound("userId.equals=" + userId);

        // Get all the technicianEquipmentMappingList where user equals to (userId + 1)
        defaultTechnicianEquipmentMappingShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTechnicianEquipmentMappingShouldBeFound(String filter) throws Exception {
        restTechnicianEquipmentMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(technicianEquipmentMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME.toString())));

        // Check, that the count call also returns 1
        restTechnicianEquipmentMappingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTechnicianEquipmentMappingShouldNotBeFound(String filter) throws Exception {
        restTechnicianEquipmentMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTechnicianEquipmentMappingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTechnicianEquipmentMapping() throws Exception {
        // Get the technicianEquipmentMapping
        restTechnicianEquipmentMappingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTechnicianEquipmentMapping() throws Exception {
        // Initialize the database
        technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);

        int databaseSizeBeforeUpdate = technicianEquipmentMappingRepository.findAll().size();

        // Update the technicianEquipmentMapping
        TechnicianEquipmentMapping updatedTechnicianEquipmentMapping = technicianEquipmentMappingRepository
            .findById(technicianEquipmentMapping.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTechnicianEquipmentMapping are not directly saved in db
        em.detach(updatedTechnicianEquipmentMapping);
        updatedTechnicianEquipmentMapping.dateTime(UPDATED_DATE_TIME);
        TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO = technicianEquipmentMappingMapper.toDto(
            updatedTechnicianEquipmentMapping
        );

        restTechnicianEquipmentMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, technicianEquipmentMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(technicianEquipmentMappingDTO))
            )
            .andExpect(status().isOk());

        // Validate the TechnicianEquipmentMapping in the database
        List<TechnicianEquipmentMapping> technicianEquipmentMappingList = technicianEquipmentMappingRepository.findAll();
        assertThat(technicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
        TechnicianEquipmentMapping testTechnicianEquipmentMapping = technicianEquipmentMappingList.get(
            technicianEquipmentMappingList.size() - 1
        );
        assertThat(testTechnicianEquipmentMapping.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingTechnicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeUpdate = technicianEquipmentMappingRepository.findAll().size();
        technicianEquipmentMapping.setId(longCount.incrementAndGet());

        // Create the TechnicianEquipmentMapping
        TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO = technicianEquipmentMappingMapper.toDto(technicianEquipmentMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechnicianEquipmentMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, technicianEquipmentMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(technicianEquipmentMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TechnicianEquipmentMapping in the database
        List<TechnicianEquipmentMapping> technicianEquipmentMappingList = technicianEquipmentMappingRepository.findAll();
        assertThat(technicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTechnicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeUpdate = technicianEquipmentMappingRepository.findAll().size();
        technicianEquipmentMapping.setId(longCount.incrementAndGet());

        // Create the TechnicianEquipmentMapping
        TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO = technicianEquipmentMappingMapper.toDto(technicianEquipmentMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTechnicianEquipmentMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(technicianEquipmentMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TechnicianEquipmentMapping in the database
        List<TechnicianEquipmentMapping> technicianEquipmentMappingList = technicianEquipmentMappingRepository.findAll();
        assertThat(technicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTechnicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeUpdate = technicianEquipmentMappingRepository.findAll().size();
        technicianEquipmentMapping.setId(longCount.incrementAndGet());

        // Create the TechnicianEquipmentMapping
        TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO = technicianEquipmentMappingMapper.toDto(technicianEquipmentMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTechnicianEquipmentMappingMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(technicianEquipmentMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TechnicianEquipmentMapping in the database
        List<TechnicianEquipmentMapping> technicianEquipmentMappingList = technicianEquipmentMappingRepository.findAll();
        assertThat(technicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTechnicianEquipmentMappingWithPatch() throws Exception {
        // Initialize the database
        technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);

        int databaseSizeBeforeUpdate = technicianEquipmentMappingRepository.findAll().size();

        // Update the technicianEquipmentMapping using partial update
        TechnicianEquipmentMapping partialUpdatedTechnicianEquipmentMapping = new TechnicianEquipmentMapping();
        partialUpdatedTechnicianEquipmentMapping.setId(technicianEquipmentMapping.getId());

        restTechnicianEquipmentMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTechnicianEquipmentMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTechnicianEquipmentMapping))
            )
            .andExpect(status().isOk());

        // Validate the TechnicianEquipmentMapping in the database
        List<TechnicianEquipmentMapping> technicianEquipmentMappingList = technicianEquipmentMappingRepository.findAll();
        assertThat(technicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
        TechnicianEquipmentMapping testTechnicianEquipmentMapping = technicianEquipmentMappingList.get(
            technicianEquipmentMappingList.size() - 1
        );
        assertThat(testTechnicianEquipmentMapping.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateTechnicianEquipmentMappingWithPatch() throws Exception {
        // Initialize the database
        technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);

        int databaseSizeBeforeUpdate = technicianEquipmentMappingRepository.findAll().size();

        // Update the technicianEquipmentMapping using partial update
        TechnicianEquipmentMapping partialUpdatedTechnicianEquipmentMapping = new TechnicianEquipmentMapping();
        partialUpdatedTechnicianEquipmentMapping.setId(technicianEquipmentMapping.getId());

        partialUpdatedTechnicianEquipmentMapping.dateTime(UPDATED_DATE_TIME);

        restTechnicianEquipmentMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTechnicianEquipmentMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTechnicianEquipmentMapping))
            )
            .andExpect(status().isOk());

        // Validate the TechnicianEquipmentMapping in the database
        List<TechnicianEquipmentMapping> technicianEquipmentMappingList = technicianEquipmentMappingRepository.findAll();
        assertThat(technicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
        TechnicianEquipmentMapping testTechnicianEquipmentMapping = technicianEquipmentMappingList.get(
            technicianEquipmentMappingList.size() - 1
        );
        assertThat(testTechnicianEquipmentMapping.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingTechnicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeUpdate = technicianEquipmentMappingRepository.findAll().size();
        technicianEquipmentMapping.setId(longCount.incrementAndGet());

        // Create the TechnicianEquipmentMapping
        TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO = technicianEquipmentMappingMapper.toDto(technicianEquipmentMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechnicianEquipmentMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, technicianEquipmentMappingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(technicianEquipmentMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TechnicianEquipmentMapping in the database
        List<TechnicianEquipmentMapping> technicianEquipmentMappingList = technicianEquipmentMappingRepository.findAll();
        assertThat(technicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTechnicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeUpdate = technicianEquipmentMappingRepository.findAll().size();
        technicianEquipmentMapping.setId(longCount.incrementAndGet());

        // Create the TechnicianEquipmentMapping
        TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO = technicianEquipmentMappingMapper.toDto(technicianEquipmentMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTechnicianEquipmentMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(technicianEquipmentMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TechnicianEquipmentMapping in the database
        List<TechnicianEquipmentMapping> technicianEquipmentMappingList = technicianEquipmentMappingRepository.findAll();
        assertThat(technicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTechnicianEquipmentMapping() throws Exception {
        int databaseSizeBeforeUpdate = technicianEquipmentMappingRepository.findAll().size();
        technicianEquipmentMapping.setId(longCount.incrementAndGet());

        // Create the TechnicianEquipmentMapping
        TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO = technicianEquipmentMappingMapper.toDto(technicianEquipmentMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTechnicianEquipmentMappingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(technicianEquipmentMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TechnicianEquipmentMapping in the database
        List<TechnicianEquipmentMapping> technicianEquipmentMappingList = technicianEquipmentMappingRepository.findAll();
        assertThat(technicianEquipmentMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTechnicianEquipmentMapping() throws Exception {
        // Initialize the database
        technicianEquipmentMappingRepository.saveAndFlush(technicianEquipmentMapping);

        int databaseSizeBeforeDelete = technicianEquipmentMappingRepository.findAll().size();

        // Delete the technicianEquipmentMapping
        restTechnicianEquipmentMappingMockMvc
            .perform(delete(ENTITY_API_URL_ID, technicianEquipmentMapping.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TechnicianEquipmentMapping> technicianEquipmentMappingList = technicianEquipmentMappingRepository.findAll();
        assertThat(technicianEquipmentMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
