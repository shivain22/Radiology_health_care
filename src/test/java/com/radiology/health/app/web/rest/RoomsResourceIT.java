package com.radiology.health.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.app.IntegrationTest;
import com.radiology.health.app.domain.Equipments;
import com.radiology.health.app.domain.Rooms;
import com.radiology.health.app.repository.RoomsRepository;
import com.radiology.health.app.service.dto.RoomsDTO;
import com.radiology.health.app.service.mapper.RoomsMapper;
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
 * Integration tests for the {@link RoomsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomsResourceIT {

    private static final Integer DEFAULT_ROOM_NO = 1;
    private static final Integer UPDATED_ROOM_NO = 2;
    private static final Integer SMALLER_ROOM_NO = 1 - 1;

    private static final String ENTITY_API_URL = "/api/rooms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private RoomsMapper roomsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomsMockMvc;

    private Rooms rooms;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rooms createEntity(EntityManager em) {
        Rooms rooms = new Rooms().roomNo(DEFAULT_ROOM_NO);
        return rooms;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rooms createUpdatedEntity(EntityManager em) {
        Rooms rooms = new Rooms().roomNo(UPDATED_ROOM_NO);
        return rooms;
    }

    @BeforeEach
    public void initTest() {
        rooms = createEntity(em);
    }

    @Test
    @Transactional
    void createRooms() throws Exception {
        int databaseSizeBeforeCreate = roomsRepository.findAll().size();
        // Create the Rooms
        RoomsDTO roomsDTO = roomsMapper.toDto(rooms);
        restRoomsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomsDTO)))
            .andExpect(status().isCreated());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeCreate + 1);
        Rooms testRooms = roomsList.get(roomsList.size() - 1);
        assertThat(testRooms.getRoomNo()).isEqualTo(DEFAULT_ROOM_NO);
    }

    @Test
    @Transactional
    void createRoomsWithExistingId() throws Exception {
        // Create the Rooms with an existing ID
        rooms.setId(1L);
        RoomsDTO roomsDTO = roomsMapper.toDto(rooms);

        int databaseSizeBeforeCreate = roomsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRoomNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomsRepository.findAll().size();
        // set the field null
        rooms.setRoomNo(null);

        // Create the Rooms, which fails.
        RoomsDTO roomsDTO = roomsMapper.toDto(rooms);

        restRoomsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomsDTO)))
            .andExpect(status().isBadRequest());

        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRooms() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        // Get all the roomsList
        restRoomsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rooms.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomNo").value(hasItem(DEFAULT_ROOM_NO)));
    }

    @Test
    @Transactional
    void getRooms() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        // Get the rooms
        restRoomsMockMvc
            .perform(get(ENTITY_API_URL_ID, rooms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rooms.getId().intValue()))
            .andExpect(jsonPath("$.roomNo").value(DEFAULT_ROOM_NO));
    }

    @Test
    @Transactional
    void getRoomsByIdFiltering() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        Long id = rooms.getId();

        defaultRoomsShouldBeFound("id.equals=" + id);
        defaultRoomsShouldNotBeFound("id.notEquals=" + id);

        defaultRoomsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRoomsShouldNotBeFound("id.greaterThan=" + id);

        defaultRoomsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRoomsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRoomsByRoomNoIsEqualToSomething() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        // Get all the roomsList where roomNo equals to DEFAULT_ROOM_NO
        defaultRoomsShouldBeFound("roomNo.equals=" + DEFAULT_ROOM_NO);

        // Get all the roomsList where roomNo equals to UPDATED_ROOM_NO
        defaultRoomsShouldNotBeFound("roomNo.equals=" + UPDATED_ROOM_NO);
    }

    @Test
    @Transactional
    void getAllRoomsByRoomNoIsInShouldWork() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        // Get all the roomsList where roomNo in DEFAULT_ROOM_NO or UPDATED_ROOM_NO
        defaultRoomsShouldBeFound("roomNo.in=" + DEFAULT_ROOM_NO + "," + UPDATED_ROOM_NO);

        // Get all the roomsList where roomNo equals to UPDATED_ROOM_NO
        defaultRoomsShouldNotBeFound("roomNo.in=" + UPDATED_ROOM_NO);
    }

    @Test
    @Transactional
    void getAllRoomsByRoomNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        // Get all the roomsList where roomNo is not null
        defaultRoomsShouldBeFound("roomNo.specified=true");

        // Get all the roomsList where roomNo is null
        defaultRoomsShouldNotBeFound("roomNo.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomsByRoomNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        // Get all the roomsList where roomNo is greater than or equal to DEFAULT_ROOM_NO
        defaultRoomsShouldBeFound("roomNo.greaterThanOrEqual=" + DEFAULT_ROOM_NO);

        // Get all the roomsList where roomNo is greater than or equal to UPDATED_ROOM_NO
        defaultRoomsShouldNotBeFound("roomNo.greaterThanOrEqual=" + UPDATED_ROOM_NO);
    }

    @Test
    @Transactional
    void getAllRoomsByRoomNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        // Get all the roomsList where roomNo is less than or equal to DEFAULT_ROOM_NO
        defaultRoomsShouldBeFound("roomNo.lessThanOrEqual=" + DEFAULT_ROOM_NO);

        // Get all the roomsList where roomNo is less than or equal to SMALLER_ROOM_NO
        defaultRoomsShouldNotBeFound("roomNo.lessThanOrEqual=" + SMALLER_ROOM_NO);
    }

    @Test
    @Transactional
    void getAllRoomsByRoomNoIsLessThanSomething() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        // Get all the roomsList where roomNo is less than DEFAULT_ROOM_NO
        defaultRoomsShouldNotBeFound("roomNo.lessThan=" + DEFAULT_ROOM_NO);

        // Get all the roomsList where roomNo is less than UPDATED_ROOM_NO
        defaultRoomsShouldBeFound("roomNo.lessThan=" + UPDATED_ROOM_NO);
    }

    @Test
    @Transactional
    void getAllRoomsByRoomNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        // Get all the roomsList where roomNo is greater than DEFAULT_ROOM_NO
        defaultRoomsShouldNotBeFound("roomNo.greaterThan=" + DEFAULT_ROOM_NO);

        // Get all the roomsList where roomNo is greater than SMALLER_ROOM_NO
        defaultRoomsShouldBeFound("roomNo.greaterThan=" + SMALLER_ROOM_NO);
    }

    @Test
    @Transactional
    void getAllRoomsByEquipmentsIsEqualToSomething() throws Exception {
        Equipments equipments;
        if (TestUtil.findAll(em, Equipments.class).isEmpty()) {
            roomsRepository.saveAndFlush(rooms);
            equipments = EquipmentsResourceIT.createEntity(em);
        } else {
            equipments = TestUtil.findAll(em, Equipments.class).get(0);
        }
        em.persist(equipments);
        em.flush();
        rooms.addEquipments(equipments);
        roomsRepository.saveAndFlush(rooms);
        Long equipmentsId = equipments.getId();
        // Get all the roomsList where equipments equals to equipmentsId
        defaultRoomsShouldBeFound("equipmentsId.equals=" + equipmentsId);

        // Get all the roomsList where equipments equals to (equipmentsId + 1)
        defaultRoomsShouldNotBeFound("equipmentsId.equals=" + (equipmentsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRoomsShouldBeFound(String filter) throws Exception {
        restRoomsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rooms.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomNo").value(hasItem(DEFAULT_ROOM_NO)));

        // Check, that the count call also returns 1
        restRoomsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRoomsShouldNotBeFound(String filter) throws Exception {
        restRoomsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoomsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRooms() throws Exception {
        // Get the rooms
        restRoomsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRooms() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        int databaseSizeBeforeUpdate = roomsRepository.findAll().size();

        // Update the rooms
        Rooms updatedRooms = roomsRepository.findById(rooms.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRooms are not directly saved in db
        em.detach(updatedRooms);
        updatedRooms.roomNo(UPDATED_ROOM_NO);
        RoomsDTO roomsDTO = roomsMapper.toDto(updatedRooms);

        restRoomsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeUpdate);
        Rooms testRooms = roomsList.get(roomsList.size() - 1);
        assertThat(testRooms.getRoomNo()).isEqualTo(UPDATED_ROOM_NO);
    }

    @Test
    @Transactional
    void putNonExistingRooms() throws Exception {
        int databaseSizeBeforeUpdate = roomsRepository.findAll().size();
        rooms.setId(longCount.incrementAndGet());

        // Create the Rooms
        RoomsDTO roomsDTO = roomsMapper.toDto(rooms);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRooms() throws Exception {
        int databaseSizeBeforeUpdate = roomsRepository.findAll().size();
        rooms.setId(longCount.incrementAndGet());

        // Create the Rooms
        RoomsDTO roomsDTO = roomsMapper.toDto(rooms);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRooms() throws Exception {
        int databaseSizeBeforeUpdate = roomsRepository.findAll().size();
        rooms.setId(longCount.incrementAndGet());

        // Create the Rooms
        RoomsDTO roomsDTO = roomsMapper.toDto(rooms);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomsWithPatch() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        int databaseSizeBeforeUpdate = roomsRepository.findAll().size();

        // Update the rooms using partial update
        Rooms partialUpdatedRooms = new Rooms();
        partialUpdatedRooms.setId(rooms.getId());

        restRoomsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRooms.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRooms))
            )
            .andExpect(status().isOk());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeUpdate);
        Rooms testRooms = roomsList.get(roomsList.size() - 1);
        assertThat(testRooms.getRoomNo()).isEqualTo(DEFAULT_ROOM_NO);
    }

    @Test
    @Transactional
    void fullUpdateRoomsWithPatch() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        int databaseSizeBeforeUpdate = roomsRepository.findAll().size();

        // Update the rooms using partial update
        Rooms partialUpdatedRooms = new Rooms();
        partialUpdatedRooms.setId(rooms.getId());

        partialUpdatedRooms.roomNo(UPDATED_ROOM_NO);

        restRoomsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRooms.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRooms))
            )
            .andExpect(status().isOk());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeUpdate);
        Rooms testRooms = roomsList.get(roomsList.size() - 1);
        assertThat(testRooms.getRoomNo()).isEqualTo(UPDATED_ROOM_NO);
    }

    @Test
    @Transactional
    void patchNonExistingRooms() throws Exception {
        int databaseSizeBeforeUpdate = roomsRepository.findAll().size();
        rooms.setId(longCount.incrementAndGet());

        // Create the Rooms
        RoomsDTO roomsDTO = roomsMapper.toDto(rooms);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roomsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRooms() throws Exception {
        int databaseSizeBeforeUpdate = roomsRepository.findAll().size();
        rooms.setId(longCount.incrementAndGet());

        // Create the Rooms
        RoomsDTO roomsDTO = roomsMapper.toDto(rooms);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRooms() throws Exception {
        int databaseSizeBeforeUpdate = roomsRepository.findAll().size();
        rooms.setId(longCount.incrementAndGet());

        // Create the Rooms
        RoomsDTO roomsDTO = roomsMapper.toDto(rooms);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roomsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRooms() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        int databaseSizeBeforeDelete = roomsRepository.findAll().size();

        // Delete the rooms
        restRoomsMockMvc
            .perform(delete(ENTITY_API_URL_ID, rooms.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
