package com.radiology.health.care.web.rest;

import static com.radiology.health.care.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.care.IntegrationTest;
import com.radiology.health.care.domain.OfficeTimings;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.repository.OfficeTimingsRepository;
import com.radiology.health.care.service.dto.OfficeTimingsDTO;
import com.radiology.health.care.service.mapper.OfficeTimingsMapper;
import jakarta.persistence.EntityManager;
import java.sql.Time;
import java.time.*;
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
 * Integration tests for the {@link OfficeTimingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OfficeTimingsResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalTime DEFAULT_SHIFT_START = LocalTime.from(Instant.ofEpochMilli(0L));
    private static final LocalTime UPDATED_SHIFT_START = LocalTime.from(ZonedDateTime.now(ZoneId.systemDefault()).withNano(0));
    private static final LocalTime SMALLER_SHIFT_START = LocalTime.from(Instant.ofEpochMilli(-1L));

    private static final LocalTime DEFAULT_SHIFT_END = LocalTime.from(Instant.ofEpochMilli(0L));
    private static final LocalTime UPDATED_SHIFT_END = LocalTime.from(Instant.now().truncatedTo(ChronoUnit.MILLIS));

    private static final Boolean DEFAULT_DEFAULT_TIMINGS = false;
    private static final Boolean UPDATED_DEFAULT_TIMINGS = true;

    private static final String ENTITY_API_URL = "/api/office-timings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OfficeTimingsRepository officeTimingsRepository;

    @Autowired
    private OfficeTimingsMapper officeTimingsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfficeTimingsMockMvc;

    private OfficeTimings officeTimings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfficeTimings createEntity(EntityManager em) {
        OfficeTimings officeTimings = new OfficeTimings()
            .date(DEFAULT_DATE)
            .shiftStart(DEFAULT_SHIFT_START)
            .shiftEnd(DEFAULT_SHIFT_END)
            .defaultTimings(DEFAULT_DEFAULT_TIMINGS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        officeTimings.setUser(user);
        return officeTimings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfficeTimings createUpdatedEntity(EntityManager em) {
        OfficeTimings officeTimings = new OfficeTimings()
            .date(UPDATED_DATE)
            .shiftStart(UPDATED_SHIFT_START)
            .shiftEnd(UPDATED_SHIFT_END)
            .defaultTimings(UPDATED_DEFAULT_TIMINGS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        officeTimings.setUser(user);
        return officeTimings;
    }

    @BeforeEach
    public void initTest() {
        officeTimings = createEntity(em);
    }

    @Test
    @Transactional
    void createOfficeTimings() throws Exception {
        int databaseSizeBeforeCreate = officeTimingsRepository.findAll().size();
        // Create the OfficeTimings
        OfficeTimingsDTO officeTimingsDTO = officeTimingsMapper.toDto(officeTimings);
        restOfficeTimingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(officeTimingsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OfficeTimings in the database
        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeCreate + 1);
        OfficeTimings testOfficeTimings = officeTimingsList.get(officeTimingsList.size() - 1);
        assertThat(testOfficeTimings.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testOfficeTimings.getShiftStart()).isEqualTo(DEFAULT_SHIFT_START);
        assertThat(testOfficeTimings.getShiftEnd()).isEqualTo(DEFAULT_SHIFT_END);
        assertThat(testOfficeTimings.getDefaultTimings()).isEqualTo(DEFAULT_DEFAULT_TIMINGS);
    }

    @Test
    @Transactional
    void createOfficeTimingsWithExistingId() throws Exception {
        // Create the OfficeTimings with an existing ID
        officeTimings.setId(1L);
        OfficeTimingsDTO officeTimingsDTO = officeTimingsMapper.toDto(officeTimings);

        int databaseSizeBeforeCreate = officeTimingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfficeTimingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(officeTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OfficeTimings in the database
        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkShiftStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeTimingsRepository.findAll().size();
        // set the field null
        officeTimings.setShiftStart(null);

        // Create the OfficeTimings, which fails.
        OfficeTimingsDTO officeTimingsDTO = officeTimingsMapper.toDto(officeTimings);

        restOfficeTimingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(officeTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShiftEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeTimingsRepository.findAll().size();
        // set the field null
        officeTimings.setShiftEnd(null);

        // Create the OfficeTimings, which fails.
        OfficeTimingsDTO officeTimingsDTO = officeTimingsMapper.toDto(officeTimings);

        restOfficeTimingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(officeTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOfficeTimings() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList
        restOfficeTimingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(officeTimings.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].shiftStart").value(hasItem(DEFAULT_SHIFT_START)))
            .andExpect(jsonPath("$.[*].shiftEnd").value(hasItem(DEFAULT_SHIFT_END.toString())))
            .andExpect(jsonPath("$.[*].defaultTimings").value(hasItem(DEFAULT_DEFAULT_TIMINGS.booleanValue())));
    }

    @Test
    @Transactional
    void getOfficeTimings() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get the officeTimings
        restOfficeTimingsMockMvc
            .perform(get(ENTITY_API_URL_ID, officeTimings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(officeTimings.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.shiftStart").value(DEFAULT_SHIFT_START))
            .andExpect(jsonPath("$.shiftEnd").value(DEFAULT_SHIFT_END.toString()))
            .andExpect(jsonPath("$.defaultTimings").value(DEFAULT_DEFAULT_TIMINGS.booleanValue()));
    }

    @Test
    @Transactional
    void getOfficeTimingsByIdFiltering() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        Long id = officeTimings.getId();

        defaultOfficeTimingsShouldBeFound("id.equals=" + id);
        defaultOfficeTimingsShouldNotBeFound("id.notEquals=" + id);

        defaultOfficeTimingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOfficeTimingsShouldNotBeFound("id.greaterThan=" + id);

        defaultOfficeTimingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOfficeTimingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where date equals to DEFAULT_DATE
        defaultOfficeTimingsShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the officeTimingsList where date equals to UPDATED_DATE
        defaultOfficeTimingsShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where date in DEFAULT_DATE or UPDATED_DATE
        defaultOfficeTimingsShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the officeTimingsList where date equals to UPDATED_DATE
        defaultOfficeTimingsShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where date is not null
        defaultOfficeTimingsShouldBeFound("date.specified=true");

        // Get all the officeTimingsList where date is null
        defaultOfficeTimingsShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where date is greater than or equal to DEFAULT_DATE
        defaultOfficeTimingsShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the officeTimingsList where date is greater than or equal to UPDATED_DATE
        defaultOfficeTimingsShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where date is less than or equal to DEFAULT_DATE
        defaultOfficeTimingsShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the officeTimingsList where date is less than or equal to SMALLER_DATE
        defaultOfficeTimingsShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where date is less than DEFAULT_DATE
        defaultOfficeTimingsShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the officeTimingsList where date is less than UPDATED_DATE
        defaultOfficeTimingsShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where date is greater than DEFAULT_DATE
        defaultOfficeTimingsShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the officeTimingsList where date is greater than SMALLER_DATE
        defaultOfficeTimingsShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByShiftStartIsEqualToSomething() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where shiftStart equals to DEFAULT_SHIFT_START
        defaultOfficeTimingsShouldBeFound("shiftStart.equals=" + DEFAULT_SHIFT_START);

        // Get all the officeTimingsList where shiftStart equals to UPDATED_SHIFT_START
        defaultOfficeTimingsShouldNotBeFound("shiftStart.equals=" + UPDATED_SHIFT_START);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByShiftStartIsInShouldWork() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where shiftStart in DEFAULT_SHIFT_START or UPDATED_SHIFT_START
        defaultOfficeTimingsShouldBeFound("shiftStart.in=" + DEFAULT_SHIFT_START + "," + UPDATED_SHIFT_START);

        // Get all the officeTimingsList where shiftStart equals to UPDATED_SHIFT_START
        defaultOfficeTimingsShouldNotBeFound("shiftStart.in=" + UPDATED_SHIFT_START);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByShiftStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where shiftStart is not null
        defaultOfficeTimingsShouldBeFound("shiftStart.specified=true");

        // Get all the officeTimingsList where shiftStart is null
        defaultOfficeTimingsShouldNotBeFound("shiftStart.specified=false");
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByShiftStartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where shiftStart is greater than or equal to DEFAULT_SHIFT_START
        defaultOfficeTimingsShouldBeFound("shiftStart.greaterThanOrEqual=" + DEFAULT_SHIFT_START);

        // Get all the officeTimingsList where shiftStart is greater than or equal to UPDATED_SHIFT_START
        defaultOfficeTimingsShouldNotBeFound("shiftStart.greaterThanOrEqual=" + UPDATED_SHIFT_START);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByShiftStartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where shiftStart is less than or equal to DEFAULT_SHIFT_START
        defaultOfficeTimingsShouldBeFound("shiftStart.lessThanOrEqual=" + DEFAULT_SHIFT_START);

        // Get all the officeTimingsList where shiftStart is less than or equal to SMALLER_SHIFT_START
        defaultOfficeTimingsShouldNotBeFound("shiftStart.lessThanOrEqual=" + SMALLER_SHIFT_START);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByShiftStartIsLessThanSomething() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where shiftStart is less than DEFAULT_SHIFT_START
        defaultOfficeTimingsShouldNotBeFound("shiftStart.lessThan=" + DEFAULT_SHIFT_START);

        // Get all the officeTimingsList where shiftStart is less than UPDATED_SHIFT_START
        defaultOfficeTimingsShouldBeFound("shiftStart.lessThan=" + UPDATED_SHIFT_START);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByShiftStartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where shiftStart is greater than DEFAULT_SHIFT_START
        defaultOfficeTimingsShouldNotBeFound("shiftStart.greaterThan=" + DEFAULT_SHIFT_START);

        // Get all the officeTimingsList where shiftStart is greater than SMALLER_SHIFT_START
        defaultOfficeTimingsShouldBeFound("shiftStart.greaterThan=" + SMALLER_SHIFT_START);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByShiftEndIsEqualToSomething() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where shiftEnd equals to DEFAULT_SHIFT_END
        defaultOfficeTimingsShouldBeFound("shiftEnd.equals=" + DEFAULT_SHIFT_END);

        // Get all the officeTimingsList where shiftEnd equals to UPDATED_SHIFT_END
        defaultOfficeTimingsShouldNotBeFound("shiftEnd.equals=" + UPDATED_SHIFT_END);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByShiftEndIsInShouldWork() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where shiftEnd in DEFAULT_SHIFT_END or UPDATED_SHIFT_END
        defaultOfficeTimingsShouldBeFound("shiftEnd.in=" + DEFAULT_SHIFT_END + "," + UPDATED_SHIFT_END);

        // Get all the officeTimingsList where shiftEnd equals to UPDATED_SHIFT_END
        defaultOfficeTimingsShouldNotBeFound("shiftEnd.in=" + UPDATED_SHIFT_END);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByShiftEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where shiftEnd is not null
        defaultOfficeTimingsShouldBeFound("shiftEnd.specified=true");

        // Get all the officeTimingsList where shiftEnd is null
        defaultOfficeTimingsShouldNotBeFound("shiftEnd.specified=false");
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByDefaultTimingsIsEqualToSomething() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where defaultTimings equals to DEFAULT_DEFAULT_TIMINGS
        defaultOfficeTimingsShouldBeFound("defaultTimings.equals=" + DEFAULT_DEFAULT_TIMINGS);

        // Get all the officeTimingsList where defaultTimings equals to UPDATED_DEFAULT_TIMINGS
        defaultOfficeTimingsShouldNotBeFound("defaultTimings.equals=" + UPDATED_DEFAULT_TIMINGS);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByDefaultTimingsIsInShouldWork() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where defaultTimings in DEFAULT_DEFAULT_TIMINGS or UPDATED_DEFAULT_TIMINGS
        defaultOfficeTimingsShouldBeFound("defaultTimings.in=" + DEFAULT_DEFAULT_TIMINGS + "," + UPDATED_DEFAULT_TIMINGS);

        // Get all the officeTimingsList where defaultTimings equals to UPDATED_DEFAULT_TIMINGS
        defaultOfficeTimingsShouldNotBeFound("defaultTimings.in=" + UPDATED_DEFAULT_TIMINGS);
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByDefaultTimingsIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        // Get all the officeTimingsList where defaultTimings is not null
        defaultOfficeTimingsShouldBeFound("defaultTimings.specified=true");

        // Get all the officeTimingsList where defaultTimings is null
        defaultOfficeTimingsShouldNotBeFound("defaultTimings.specified=false");
    }

    @Test
    @Transactional
    void getAllOfficeTimingsByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            officeTimingsRepository.saveAndFlush(officeTimings);
            user = UserResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        officeTimings.setUser(user);
        officeTimingsRepository.saveAndFlush(officeTimings);
        Long userId = user.getId();
        // Get all the officeTimingsList where user equals to userId
        defaultOfficeTimingsShouldBeFound("userId.equals=" + userId);

        // Get all the officeTimingsList where user equals to (userId + 1)
        defaultOfficeTimingsShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOfficeTimingsShouldBeFound(String filter) throws Exception {
        restOfficeTimingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(officeTimings.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].shiftStart").value(hasItem(DEFAULT_SHIFT_START)))
            .andExpect(jsonPath("$.[*].shiftEnd").value(hasItem(DEFAULT_SHIFT_END.toString())))
            .andExpect(jsonPath("$.[*].defaultTimings").value(hasItem(DEFAULT_DEFAULT_TIMINGS.booleanValue())));

        // Check, that the count call also returns 1
        restOfficeTimingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOfficeTimingsShouldNotBeFound(String filter) throws Exception {
        restOfficeTimingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOfficeTimingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOfficeTimings() throws Exception {
        // Get the officeTimings
        restOfficeTimingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOfficeTimings() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        int databaseSizeBeforeUpdate = officeTimingsRepository.findAll().size();

        // Update the officeTimings
        OfficeTimings updatedOfficeTimings = officeTimingsRepository.findById(officeTimings.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOfficeTimings are not directly saved in db
        em.detach(updatedOfficeTimings);
        updatedOfficeTimings
            .date(UPDATED_DATE)
            .shiftStart(UPDATED_SHIFT_START)
            .shiftEnd(UPDATED_SHIFT_END)
            .defaultTimings(UPDATED_DEFAULT_TIMINGS);
        OfficeTimingsDTO officeTimingsDTO = officeTimingsMapper.toDto(updatedOfficeTimings);

        restOfficeTimingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, officeTimingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(officeTimingsDTO))
            )
            .andExpect(status().isOk());

        // Validate the OfficeTimings in the database
        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeUpdate);
        OfficeTimings testOfficeTimings = officeTimingsList.get(officeTimingsList.size() - 1);
        assertThat(testOfficeTimings.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOfficeTimings.getShiftStart()).isEqualTo(UPDATED_SHIFT_START);
        assertThat(testOfficeTimings.getShiftEnd()).isEqualTo(UPDATED_SHIFT_END);
        assertThat(testOfficeTimings.getDefaultTimings()).isEqualTo(UPDATED_DEFAULT_TIMINGS);
    }

    @Test
    @Transactional
    void putNonExistingOfficeTimings() throws Exception {
        int databaseSizeBeforeUpdate = officeTimingsRepository.findAll().size();
        officeTimings.setId(longCount.incrementAndGet());

        // Create the OfficeTimings
        OfficeTimingsDTO officeTimingsDTO = officeTimingsMapper.toDto(officeTimings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficeTimingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, officeTimingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(officeTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OfficeTimings in the database
        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOfficeTimings() throws Exception {
        int databaseSizeBeforeUpdate = officeTimingsRepository.findAll().size();
        officeTimings.setId(longCount.incrementAndGet());

        // Create the OfficeTimings
        OfficeTimingsDTO officeTimingsDTO = officeTimingsMapper.toDto(officeTimings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfficeTimingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(officeTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OfficeTimings in the database
        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOfficeTimings() throws Exception {
        int databaseSizeBeforeUpdate = officeTimingsRepository.findAll().size();
        officeTimings.setId(longCount.incrementAndGet());

        // Create the OfficeTimings
        OfficeTimingsDTO officeTimingsDTO = officeTimingsMapper.toDto(officeTimings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfficeTimingsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(officeTimingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OfficeTimings in the database
        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOfficeTimingsWithPatch() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        int databaseSizeBeforeUpdate = officeTimingsRepository.findAll().size();

        // Update the officeTimings using partial update
        OfficeTimings partialUpdatedOfficeTimings = new OfficeTimings();
        partialUpdatedOfficeTimings.setId(officeTimings.getId());

        partialUpdatedOfficeTimings.date(UPDATED_DATE).defaultTimings(UPDATED_DEFAULT_TIMINGS);

        restOfficeTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOfficeTimings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOfficeTimings))
            )
            .andExpect(status().isOk());

        // Validate the OfficeTimings in the database
        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeUpdate);
        OfficeTimings testOfficeTimings = officeTimingsList.get(officeTimingsList.size() - 1);
        assertThat(testOfficeTimings.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOfficeTimings.getShiftStart()).isEqualTo(DEFAULT_SHIFT_START);
        assertThat(testOfficeTimings.getShiftEnd()).isEqualTo(DEFAULT_SHIFT_END);
        assertThat(testOfficeTimings.getDefaultTimings()).isEqualTo(UPDATED_DEFAULT_TIMINGS);
    }

    @Test
    @Transactional
    void fullUpdateOfficeTimingsWithPatch() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        int databaseSizeBeforeUpdate = officeTimingsRepository.findAll().size();

        // Update the officeTimings using partial update
        OfficeTimings partialUpdatedOfficeTimings = new OfficeTimings();
        partialUpdatedOfficeTimings.setId(officeTimings.getId());

        partialUpdatedOfficeTimings
            .date(UPDATED_DATE)
            .shiftStart(UPDATED_SHIFT_START)
            .shiftEnd(UPDATED_SHIFT_END)
            .defaultTimings(UPDATED_DEFAULT_TIMINGS);

        restOfficeTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOfficeTimings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOfficeTimings))
            )
            .andExpect(status().isOk());

        // Validate the OfficeTimings in the database
        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeUpdate);
        OfficeTimings testOfficeTimings = officeTimingsList.get(officeTimingsList.size() - 1);
        assertThat(testOfficeTimings.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOfficeTimings.getShiftStart()).isEqualTo(UPDATED_SHIFT_START);
        assertThat(testOfficeTimings.getShiftEnd()).isEqualTo(UPDATED_SHIFT_END);
        assertThat(testOfficeTimings.getDefaultTimings()).isEqualTo(UPDATED_DEFAULT_TIMINGS);
    }

    @Test
    @Transactional
    void patchNonExistingOfficeTimings() throws Exception {
        int databaseSizeBeforeUpdate = officeTimingsRepository.findAll().size();
        officeTimings.setId(longCount.incrementAndGet());

        // Create the OfficeTimings
        OfficeTimingsDTO officeTimingsDTO = officeTimingsMapper.toDto(officeTimings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficeTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, officeTimingsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(officeTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OfficeTimings in the database
        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOfficeTimings() throws Exception {
        int databaseSizeBeforeUpdate = officeTimingsRepository.findAll().size();
        officeTimings.setId(longCount.incrementAndGet());

        // Create the OfficeTimings
        OfficeTimingsDTO officeTimingsDTO = officeTimingsMapper.toDto(officeTimings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfficeTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(officeTimingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OfficeTimings in the database
        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOfficeTimings() throws Exception {
        int databaseSizeBeforeUpdate = officeTimingsRepository.findAll().size();
        officeTimings.setId(longCount.incrementAndGet());

        // Create the OfficeTimings
        OfficeTimingsDTO officeTimingsDTO = officeTimingsMapper.toDto(officeTimings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfficeTimingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(officeTimingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OfficeTimings in the database
        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOfficeTimings() throws Exception {
        // Initialize the database
        officeTimingsRepository.saveAndFlush(officeTimings);

        int databaseSizeBeforeDelete = officeTimingsRepository.findAll().size();

        // Delete the officeTimings
        restOfficeTimingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, officeTimings.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OfficeTimings> officeTimingsList = officeTimingsRepository.findAll();
        assertThat(officeTimingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
