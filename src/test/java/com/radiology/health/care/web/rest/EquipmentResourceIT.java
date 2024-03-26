package com.radiology.health.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.care.IntegrationTest;
import com.radiology.health.care.domain.Equipment;
import com.radiology.health.care.domain.Room;
import com.radiology.health.care.domain.TechnicianEquipmentMapping;
import com.radiology.health.care.domain.TestCategories;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.repository.EquipmentRepository;
import com.radiology.health.care.repository.RoomRepository;
import com.radiology.health.care.service.dto.EquipmentDTO;
import com.radiology.health.care.service.dto.RoomDTO;
import com.radiology.health.care.service.mapper.EquipmentMapper;
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
 * Integration tests for the {@link EquipmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EquipmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";
    private static final Integer DEFAULT_ROOM = 1;

    private static final String ENTITY_API_URL = "/api/equipment";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipmentMockMvc;

    private Equipment equipment;

    @Autowired
    private RoomRepository roomRepository;

    private RoomResourceIT roomResourceIT;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipment createEntity(EntityManager em) {
        Equipment equipment = new Equipment().name(DEFAULT_NAME);
        Room room = RoomResourceIT.createEntity(em);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        em.persist(room);
        em.flush();
        equipment.setUser(user);
        equipment.setRoom(room);
        return equipment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipment createUpdatedEntity(EntityManager em) {
        Equipment equipment = new Equipment().name(UPDATED_NAME);
        Room room = RoomResourceIT.createEntity(em);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        em.persist(room);
        em.flush();
        equipment.setUser(user);
        equipment.setRoom(room);
        return equipment;
    }

    @BeforeEach
    public void initTest() {
        equipment = createEntity(em);
    }

    @Test
    @Transactional
    void createEquipment() throws Exception {
        // Create a Room entity and save it
        //        Room room = new Room();
        //        room.setRoomNo(1);
        //
        Room room = RoomResourceIT.createEntity(em);
        room = roomRepository.save(room);
        int databaseSizeBeforeCreate = equipmentRepository.findAll().size();

        // Create the Equipment entity and set its attributes
        Equipment equipment = new Equipment();
        equipment.setName(DEFAULT_NAME);
        equipment.setRoom(room); // Set the Room entity
        // Set other attributes as needed

        // Convert Equipment entity to DTO
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);
        equipmentDTO.setRoomId(room.getId());
        System.out.println("RoomId" + room.getId());

        // Perform POST request to create Equipment
        restEquipmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isCreated());

        // Validate Equipment creation in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeCreate + 1);
        Equipment testEquipment = equipmentList.get(equipmentList.size() - 1);
        assertThat(testEquipment.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createEquipmentWithExistingId() throws Exception {
        // Create the Equipment with an existing ID
        equipment.setId(1L);
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        int databaseSizeBeforeCreate = equipmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentRepository.findAll().size();
        // set the field null
        equipment.setName(null);

        // Create the Equipment, which fails.
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        restEquipmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isBadRequest());

        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        // Get all the equipmentList
        restEquipmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        // Get the equipment
        restEquipmentMockMvc
            .perform(get(ENTITY_API_URL_ID, equipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getEquipmentByIdFiltering() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        Long id = equipment.getId();

        defaultEquipmentShouldBeFound("id.equals=" + id);
        defaultEquipmentShouldNotBeFound("id.notEquals=" + id);

        defaultEquipmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEquipmentShouldNotBeFound("id.greaterThan=" + id);

        defaultEquipmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEquipmentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEquipmentByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        // Get all the equipmentList where name equals to DEFAULT_NAME
        defaultEquipmentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the equipmentList where name equals to UPDATED_NAME
        defaultEquipmentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEquipmentByNameIsInShouldWork() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        // Get all the equipmentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEquipmentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the equipmentList where name equals to UPDATED_NAME
        defaultEquipmentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEquipmentByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        // Get all the equipmentList where name is not null
        defaultEquipmentShouldBeFound("name.specified=true");

        // Get all the equipmentList where name is null
        defaultEquipmentShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllEquipmentByNameContainsSomething() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        // Get all the equipmentList where name contains DEFAULT_NAME
        defaultEquipmentShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the equipmentList where name contains UPDATED_NAME
        defaultEquipmentShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEquipmentByNameNotContainsSomething() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        // Get all the equipmentList where name does not contain DEFAULT_NAME
        defaultEquipmentShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the equipmentList where name does not contain UPDATED_NAME
        defaultEquipmentShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEquipmentByRoomIsEqualToSomething() throws Exception {
        Room room;
        if (TestUtil.findAll(em, Room.class).isEmpty()) {
            equipmentRepository.saveAndFlush(equipment);
            room = RoomResourceIT.createEntity(em);
        } else {
            room = TestUtil.findAll(em, Room.class).get(0);
        }
        em.persist(room);
        em.flush();
        equipment.setRoom(room);
        equipmentRepository.saveAndFlush(equipment);
        Long roomId = room.getId();
        // Get all the equipmentList where room equals to roomId
        defaultEquipmentShouldBeFound("roomId.equals=" + roomId);

        // Get all the equipmentList where room equals to (roomId + 1)
        defaultEquipmentShouldNotBeFound("roomId.equals=" + (roomId + 1));
    }

    @Test
    @Transactional
    void getAllEquipmentByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            equipmentRepository.saveAndFlush(equipment);
            user = UserResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        equipment.setUser(user);
        equipmentRepository.saveAndFlush(equipment);
        Long userId = user.getId();
        // Get all the equipmentList where user equals to userId
        defaultEquipmentShouldBeFound("userId.equals=" + userId);

        // Get all the equipmentList where user equals to (userId + 1)
        defaultEquipmentShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllEquipmentByTechnicianEquipmentMappingIsEqualToSomething() throws Exception {
        TechnicianEquipmentMapping technicianEquipmentMapping;
        if (TestUtil.findAll(em, TechnicianEquipmentMapping.class).isEmpty()) {
            equipmentRepository.saveAndFlush(equipment);
            technicianEquipmentMapping = TechnicianEquipmentMappingResourceIT.createEntity(em);
        } else {
            technicianEquipmentMapping = TestUtil.findAll(em, TechnicianEquipmentMapping.class).get(0);
        }
        em.persist(technicianEquipmentMapping);
        em.flush();
        equipment.addTechnicianEquipmentMapping(technicianEquipmentMapping);
        equipmentRepository.saveAndFlush(equipment);
        Long technicianEquipmentMappingId = technicianEquipmentMapping.getId();
        // Get all the equipmentList where technicianEquipmentMapping equals to technicianEquipmentMappingId
        defaultEquipmentShouldBeFound("technicianEquipmentMappingId.equals=" + technicianEquipmentMappingId);

        // Get all the equipmentList where technicianEquipmentMapping equals to (technicianEquipmentMappingId + 1)
        defaultEquipmentShouldNotBeFound("technicianEquipmentMappingId.equals=" + (technicianEquipmentMappingId + 1));
    }

    @Test
    @Transactional
    void getAllEquipmentByTestCategoriesIsEqualToSomething() throws Exception {
        TestCategories testCategories;
        if (TestUtil.findAll(em, TestCategories.class).isEmpty()) {
            equipmentRepository.saveAndFlush(equipment);
            testCategories = TestCategoriesResourceIT.createEntity(em);
        } else {
            testCategories = TestUtil.findAll(em, TestCategories.class).get(0);
        }
        em.persist(testCategories);
        em.flush();
        equipment.addTestCategories(testCategories);
        equipmentRepository.saveAndFlush(equipment);
        Long testCategoriesId = testCategories.getId();
        // Get all the equipmentList where testCategories equals to testCategoriesId
        defaultEquipmentShouldBeFound("testCategoriesId.equals=" + testCategoriesId);

        // Get all the equipmentList where testCategories equals to (testCategoriesId + 1)
        defaultEquipmentShouldNotBeFound("testCategoriesId.equals=" + (testCategoriesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEquipmentShouldBeFound(String filter) throws Exception {
        restEquipmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restEquipmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEquipmentShouldNotBeFound(String filter) throws Exception {
        restEquipmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEquipmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEquipment() throws Exception {
        // Get the equipment
        restEquipmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();

        // Update the equipment
        Equipment updatedEquipment = equipmentRepository.findById(equipment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEquipment are not directly saved in db
        em.detach(updatedEquipment);
        updatedEquipment.name(UPDATED_NAME);
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(updatedEquipment);

        restEquipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
        Equipment testEquipment = equipmentList.get(equipmentList.size() - 1);
        assertThat(testEquipment.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingEquipment() throws Exception {
        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();
        equipment.setId(longCount.incrementAndGet());

        // Create the Equipment
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEquipment() throws Exception {
        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();
        equipment.setId(longCount.incrementAndGet());

        // Create the Equipment
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEquipment() throws Exception {
        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();
        equipment.setId(longCount.incrementAndGet());

        // Create the Equipment
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEquipmentWithPatch() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();

        // Update the equipment using partial update
        Equipment partialUpdatedEquipment = new Equipment();
        partialUpdatedEquipment.setId(equipment.getId());

        restEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEquipment))
            )
            .andExpect(status().isOk());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
        Equipment testEquipment = equipmentList.get(equipmentList.size() - 1);
        assertThat(testEquipment.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateEquipmentWithPatch() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();

        // Update the equipment using partial update
        Equipment partialUpdatedEquipment = new Equipment();
        partialUpdatedEquipment.setId(equipment.getId());

        partialUpdatedEquipment.name(UPDATED_NAME);

        restEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEquipment))
            )
            .andExpect(status().isOk());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
        Equipment testEquipment = equipmentList.get(equipmentList.size() - 1);
        assertThat(testEquipment.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingEquipment() throws Exception {
        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();
        equipment.setId(longCount.incrementAndGet());

        // Create the Equipment
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, equipmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(equipmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEquipment() throws Exception {
        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();
        equipment.setId(longCount.incrementAndGet());

        // Create the Equipment
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(equipmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEquipment() throws Exception {
        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();
        equipment.setId(longCount.incrementAndGet());

        // Create the Equipment
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(equipmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        int databaseSizeBeforeDelete = equipmentRepository.findAll().size();

        // Delete the equipment
        restEquipmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, equipment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
