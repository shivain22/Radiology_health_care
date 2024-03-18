package com.radiology.health.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.app.IntegrationTest;
import com.radiology.health.app.domain.Equipments;
import com.radiology.health.app.domain.Rooms;
import com.radiology.health.app.domain.TechicianEquipmentMapping;
import com.radiology.health.app.domain.TestCatogories;
import com.radiology.health.app.repository.EquipmentsRepository;
import com.radiology.health.app.service.dto.EquipmentsDTO;
import com.radiology.health.app.service.mapper.EquipmentsMapper;
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
 * Integration tests for the {@link EquipmentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EquipmentsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/equipments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EquipmentsRepository equipmentsRepository;

    @Autowired
    private EquipmentsMapper equipmentsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipmentsMockMvc;

    private Equipments equipments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipments createEntity(EntityManager em) {
        Equipments equipments = new Equipments().name(DEFAULT_NAME);
        // Add required entity
        Rooms rooms;
        if (TestUtil.findAll(em, Rooms.class).isEmpty()) {
            rooms = RoomsResourceIT.createEntity(em);
            em.persist(rooms);
            em.flush();
        } else {
            rooms = TestUtil.findAll(em, Rooms.class).get(0);
        }
        equipments.setRooms(rooms);
        return equipments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipments createUpdatedEntity(EntityManager em) {
        Equipments equipments = new Equipments().name(UPDATED_NAME);
        // Add required entity
        Rooms rooms;
        if (TestUtil.findAll(em, Rooms.class).isEmpty()) {
            rooms = RoomsResourceIT.createUpdatedEntity(em);
            em.persist(rooms);
            em.flush();
        } else {
            rooms = TestUtil.findAll(em, Rooms.class).get(0);
        }
        equipments.setRooms(rooms);
        return equipments;
    }

    @BeforeEach
    public void initTest() {
        equipments = createEntity(em);
    }

    @Test
    @Transactional
    void createEquipments() throws Exception {
        int databaseSizeBeforeCreate = equipmentsRepository.findAll().size();
        // Create the Equipments
        EquipmentsDTO equipmentsDTO = equipmentsMapper.toDto(equipments);
        restEquipmentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipmentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Equipments in the database
        List<Equipments> equipmentsList = equipmentsRepository.findAll();
        assertThat(equipmentsList).hasSize(databaseSizeBeforeCreate + 1);
        Equipments testEquipments = equipmentsList.get(equipmentsList.size() - 1);
        assertThat(testEquipments.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createEquipmentsWithExistingId() throws Exception {
        // Create the Equipments with an existing ID
        equipments.setId(1L);
        EquipmentsDTO equipmentsDTO = equipmentsMapper.toDto(equipments);

        int databaseSizeBeforeCreate = equipmentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipmentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipmentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equipments in the database
        List<Equipments> equipmentsList = equipmentsRepository.findAll();
        assertThat(equipmentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentsRepository.findAll().size();
        // set the field null
        equipments.setName(null);

        // Create the Equipments, which fails.
        EquipmentsDTO equipmentsDTO = equipmentsMapper.toDto(equipments);

        restEquipmentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipmentsDTO)))
            .andExpect(status().isBadRequest());

        List<Equipments> equipmentsList = equipmentsRepository.findAll();
        assertThat(equipmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEquipments() throws Exception {
        // Initialize the database
        equipmentsRepository.saveAndFlush(equipments);

        // Get all the equipmentsList
        restEquipmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipments.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getEquipments() throws Exception {
        // Initialize the database
        equipmentsRepository.saveAndFlush(equipments);

        // Get the equipments
        restEquipmentsMockMvc
            .perform(get(ENTITY_API_URL_ID, equipments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipments.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getEquipmentsByIdFiltering() throws Exception {
        // Initialize the database
        equipmentsRepository.saveAndFlush(equipments);

        Long id = equipments.getId();

        defaultEquipmentsShouldBeFound("id.equals=" + id);
        defaultEquipmentsShouldNotBeFound("id.notEquals=" + id);

        defaultEquipmentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEquipmentsShouldNotBeFound("id.greaterThan=" + id);

        defaultEquipmentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEquipmentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEquipmentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        equipmentsRepository.saveAndFlush(equipments);

        // Get all the equipmentsList where name equals to DEFAULT_NAME
        defaultEquipmentsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the equipmentsList where name equals to UPDATED_NAME
        defaultEquipmentsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEquipmentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        equipmentsRepository.saveAndFlush(equipments);

        // Get all the equipmentsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEquipmentsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the equipmentsList where name equals to UPDATED_NAME
        defaultEquipmentsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEquipmentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        equipmentsRepository.saveAndFlush(equipments);

        // Get all the equipmentsList where name is not null
        defaultEquipmentsShouldBeFound("name.specified=true");

        // Get all the equipmentsList where name is null
        defaultEquipmentsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllEquipmentsByNameContainsSomething() throws Exception {
        // Initialize the database
        equipmentsRepository.saveAndFlush(equipments);

        // Get all the equipmentsList where name contains DEFAULT_NAME
        defaultEquipmentsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the equipmentsList where name contains UPDATED_NAME
        defaultEquipmentsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEquipmentsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        equipmentsRepository.saveAndFlush(equipments);

        // Get all the equipmentsList where name does not contain DEFAULT_NAME
        defaultEquipmentsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the equipmentsList where name does not contain UPDATED_NAME
        defaultEquipmentsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEquipmentsByRoomsIsEqualToSomething() throws Exception {
        Rooms rooms;
        if (TestUtil.findAll(em, Rooms.class).isEmpty()) {
            equipmentsRepository.saveAndFlush(equipments);
            rooms = RoomsResourceIT.createEntity(em);
        } else {
            rooms = TestUtil.findAll(em, Rooms.class).get(0);
        }
        em.persist(rooms);
        em.flush();
        equipments.setRooms(rooms);
        equipmentsRepository.saveAndFlush(equipments);
        Long roomsId = rooms.getId();
        // Get all the equipmentsList where rooms equals to roomsId
        defaultEquipmentsShouldBeFound("roomsId.equals=" + roomsId);

        // Get all the equipmentsList where rooms equals to (roomsId + 1)
        defaultEquipmentsShouldNotBeFound("roomsId.equals=" + (roomsId + 1));
    }

    @Test
    @Transactional
    void getAllEquipmentsByTestCatogoriesIsEqualToSomething() throws Exception {
        TestCatogories testCatogories;
        if (TestUtil.findAll(em, TestCatogories.class).isEmpty()) {
            equipmentsRepository.saveAndFlush(equipments);
            testCatogories = TestCatogoriesResourceIT.createEntity(em);
        } else {
            testCatogories = TestUtil.findAll(em, TestCatogories.class).get(0);
        }
        em.persist(testCatogories);
        em.flush();
        equipments.addTestCatogories(testCatogories);
        equipmentsRepository.saveAndFlush(equipments);
        Long testCatogoriesId = testCatogories.getId();
        // Get all the equipmentsList where testCatogories equals to testCatogoriesId
        defaultEquipmentsShouldBeFound("testCatogoriesId.equals=" + testCatogoriesId);

        // Get all the equipmentsList where testCatogories equals to (testCatogoriesId + 1)
        defaultEquipmentsShouldNotBeFound("testCatogoriesId.equals=" + (testCatogoriesId + 1));
    }

    @Test
    @Transactional
    void getAllEquipmentsByTechicianEquipmentMappingIsEqualToSomething() throws Exception {
        TechicianEquipmentMapping techicianEquipmentMapping;
        if (TestUtil.findAll(em, TechicianEquipmentMapping.class).isEmpty()) {
            equipmentsRepository.saveAndFlush(equipments);
            techicianEquipmentMapping = TechicianEquipmentMappingResourceIT.createEntity(em);
        } else {
            techicianEquipmentMapping = TestUtil.findAll(em, TechicianEquipmentMapping.class).get(0);
        }
        em.persist(techicianEquipmentMapping);
        em.flush();
        equipments.addTechicianEquipmentMapping(techicianEquipmentMapping);
        equipmentsRepository.saveAndFlush(equipments);
        Long techicianEquipmentMappingId = techicianEquipmentMapping.getId();
        // Get all the equipmentsList where techicianEquipmentMapping equals to techicianEquipmentMappingId
        defaultEquipmentsShouldBeFound("techicianEquipmentMappingId.equals=" + techicianEquipmentMappingId);

        // Get all the equipmentsList where techicianEquipmentMapping equals to (techicianEquipmentMappingId + 1)
        defaultEquipmentsShouldNotBeFound("techicianEquipmentMappingId.equals=" + (techicianEquipmentMappingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEquipmentsShouldBeFound(String filter) throws Exception {
        restEquipmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipments.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restEquipmentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEquipmentsShouldNotBeFound(String filter) throws Exception {
        restEquipmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEquipmentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEquipments() throws Exception {
        // Get the equipments
        restEquipmentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEquipments() throws Exception {
        // Initialize the database
        equipmentsRepository.saveAndFlush(equipments);

        int databaseSizeBeforeUpdate = equipmentsRepository.findAll().size();

        // Update the equipments
        Equipments updatedEquipments = equipmentsRepository.findById(equipments.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEquipments are not directly saved in db
        em.detach(updatedEquipments);
        updatedEquipments.name(UPDATED_NAME);
        EquipmentsDTO equipmentsDTO = equipmentsMapper.toDto(updatedEquipments);

        restEquipmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipmentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipmentsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Equipments in the database
        List<Equipments> equipmentsList = equipmentsRepository.findAll();
        assertThat(equipmentsList).hasSize(databaseSizeBeforeUpdate);
        Equipments testEquipments = equipmentsList.get(equipmentsList.size() - 1);
        assertThat(testEquipments.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingEquipments() throws Exception {
        int databaseSizeBeforeUpdate = equipmentsRepository.findAll().size();
        equipments.setId(longCount.incrementAndGet());

        // Create the Equipments
        EquipmentsDTO equipmentsDTO = equipmentsMapper.toDto(equipments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipmentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipments in the database
        List<Equipments> equipmentsList = equipmentsRepository.findAll();
        assertThat(equipmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEquipments() throws Exception {
        int databaseSizeBeforeUpdate = equipmentsRepository.findAll().size();
        equipments.setId(longCount.incrementAndGet());

        // Create the Equipments
        EquipmentsDTO equipmentsDTO = equipmentsMapper.toDto(equipments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipments in the database
        List<Equipments> equipmentsList = equipmentsRepository.findAll();
        assertThat(equipmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEquipments() throws Exception {
        int databaseSizeBeforeUpdate = equipmentsRepository.findAll().size();
        equipments.setId(longCount.incrementAndGet());

        // Create the Equipments
        EquipmentsDTO equipmentsDTO = equipmentsMapper.toDto(equipments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipmentsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipments in the database
        List<Equipments> equipmentsList = equipmentsRepository.findAll();
        assertThat(equipmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEquipmentsWithPatch() throws Exception {
        // Initialize the database
        equipmentsRepository.saveAndFlush(equipments);

        int databaseSizeBeforeUpdate = equipmentsRepository.findAll().size();

        // Update the equipments using partial update
        Equipments partialUpdatedEquipments = new Equipments();
        partialUpdatedEquipments.setId(equipments.getId());

        restEquipmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEquipments))
            )
            .andExpect(status().isOk());

        // Validate the Equipments in the database
        List<Equipments> equipmentsList = equipmentsRepository.findAll();
        assertThat(equipmentsList).hasSize(databaseSizeBeforeUpdate);
        Equipments testEquipments = equipmentsList.get(equipmentsList.size() - 1);
        assertThat(testEquipments.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateEquipmentsWithPatch() throws Exception {
        // Initialize the database
        equipmentsRepository.saveAndFlush(equipments);

        int databaseSizeBeforeUpdate = equipmentsRepository.findAll().size();

        // Update the equipments using partial update
        Equipments partialUpdatedEquipments = new Equipments();
        partialUpdatedEquipments.setId(equipments.getId());

        partialUpdatedEquipments.name(UPDATED_NAME);

        restEquipmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEquipments))
            )
            .andExpect(status().isOk());

        // Validate the Equipments in the database
        List<Equipments> equipmentsList = equipmentsRepository.findAll();
        assertThat(equipmentsList).hasSize(databaseSizeBeforeUpdate);
        Equipments testEquipments = equipmentsList.get(equipmentsList.size() - 1);
        assertThat(testEquipments.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingEquipments() throws Exception {
        int databaseSizeBeforeUpdate = equipmentsRepository.findAll().size();
        equipments.setId(longCount.incrementAndGet());

        // Create the Equipments
        EquipmentsDTO equipmentsDTO = equipmentsMapper.toDto(equipments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, equipmentsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(equipmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipments in the database
        List<Equipments> equipmentsList = equipmentsRepository.findAll();
        assertThat(equipmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEquipments() throws Exception {
        int databaseSizeBeforeUpdate = equipmentsRepository.findAll().size();
        equipments.setId(longCount.incrementAndGet());

        // Create the Equipments
        EquipmentsDTO equipmentsDTO = equipmentsMapper.toDto(equipments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(equipmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipments in the database
        List<Equipments> equipmentsList = equipmentsRepository.findAll();
        assertThat(equipmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEquipments() throws Exception {
        int databaseSizeBeforeUpdate = equipmentsRepository.findAll().size();
        equipments.setId(longCount.incrementAndGet());

        // Create the Equipments
        EquipmentsDTO equipmentsDTO = equipmentsMapper.toDto(equipments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(equipmentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipments in the database
        List<Equipments> equipmentsList = equipmentsRepository.findAll();
        assertThat(equipmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEquipments() throws Exception {
        // Initialize the database
        equipmentsRepository.saveAndFlush(equipments);

        int databaseSizeBeforeDelete = equipmentsRepository.findAll().size();

        // Delete the equipments
        restEquipmentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, equipments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipments> equipmentsList = equipmentsRepository.findAll();
        assertThat(equipmentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
