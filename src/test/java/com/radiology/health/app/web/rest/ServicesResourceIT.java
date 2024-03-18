package com.radiology.health.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.app.IntegrationTest;
import com.radiology.health.app.domain.Employee;
import com.radiology.health.app.domain.Rank;
import com.radiology.health.app.domain.Services;
import com.radiology.health.app.repository.ServicesRepository;
import com.radiology.health.app.service.dto.ServicesDTO;
import com.radiology.health.app.service.mapper.ServicesMapper;
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
 * Integration tests for the {@link ServicesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServicesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ServicesMapper servicesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServicesMockMvc;

    private Services services;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Services createEntity(EntityManager em) {
        Services services = new Services().name(DEFAULT_NAME);
        return services;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Services createUpdatedEntity(EntityManager em) {
        Services services = new Services().name(UPDATED_NAME);
        return services;
    }

    @BeforeEach
    public void initTest() {
        services = createEntity(em);
    }

    @Test
    @Transactional
    void createServices() throws Exception {
        int databaseSizeBeforeCreate = servicesRepository.findAll().size();
        // Create the Services
        ServicesDTO servicesDTO = servicesMapper.toDto(services);
        restServicesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicesDTO)))
            .andExpect(status().isCreated());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeCreate + 1);
        Services testServices = servicesList.get(servicesList.size() - 1);
        assertThat(testServices.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createServicesWithExistingId() throws Exception {
        // Create the Services with an existing ID
        services.setId(1L);
        ServicesDTO servicesDTO = servicesMapper.toDto(services);

        int databaseSizeBeforeCreate = servicesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicesRepository.findAll().size();
        // set the field null
        services.setName(null);

        // Create the Services, which fails.
        ServicesDTO servicesDTO = servicesMapper.toDto(services);

        restServicesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicesDTO)))
            .andExpect(status().isBadRequest());

        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        // Get all the servicesList
        restServicesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(services.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        // Get the services
        restServicesMockMvc
            .perform(get(ENTITY_API_URL_ID, services.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(services.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getServicesByIdFiltering() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        Long id = services.getId();

        defaultServicesShouldBeFound("id.equals=" + id);
        defaultServicesShouldNotBeFound("id.notEquals=" + id);

        defaultServicesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServicesShouldNotBeFound("id.greaterThan=" + id);

        defaultServicesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServicesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllServicesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        // Get all the servicesList where name equals to DEFAULT_NAME
        defaultServicesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the servicesList where name equals to UPDATED_NAME
        defaultServicesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllServicesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        // Get all the servicesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultServicesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the servicesList where name equals to UPDATED_NAME
        defaultServicesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllServicesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        // Get all the servicesList where name is not null
        defaultServicesShouldBeFound("name.specified=true");

        // Get all the servicesList where name is null
        defaultServicesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllServicesByNameContainsSomething() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        // Get all the servicesList where name contains DEFAULT_NAME
        defaultServicesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the servicesList where name contains UPDATED_NAME
        defaultServicesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllServicesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        // Get all the servicesList where name does not contain DEFAULT_NAME
        defaultServicesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the servicesList where name does not contain UPDATED_NAME
        defaultServicesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllServicesByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            servicesRepository.saveAndFlush(services);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        services.addEmployee(employee);
        servicesRepository.saveAndFlush(services);
        Long employeeId = employee.getId();
        // Get all the servicesList where employee equals to employeeId
        defaultServicesShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the servicesList where employee equals to (employeeId + 1)
        defaultServicesShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllServicesByRankIsEqualToSomething() throws Exception {
        Rank rank;
        if (TestUtil.findAll(em, Rank.class).isEmpty()) {
            servicesRepository.saveAndFlush(services);
            rank = RankResourceIT.createEntity(em);
        } else {
            rank = TestUtil.findAll(em, Rank.class).get(0);
        }
        em.persist(rank);
        em.flush();
        services.addRank(rank);
        servicesRepository.saveAndFlush(services);
        Long rankId = rank.getId();
        // Get all the servicesList where rank equals to rankId
        defaultServicesShouldBeFound("rankId.equals=" + rankId);

        // Get all the servicesList where rank equals to (rankId + 1)
        defaultServicesShouldNotBeFound("rankId.equals=" + (rankId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServicesShouldBeFound(String filter) throws Exception {
        restServicesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(services.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restServicesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServicesShouldNotBeFound(String filter) throws Exception {
        restServicesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServicesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingServices() throws Exception {
        // Get the services
        restServicesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();

        // Update the services
        Services updatedServices = servicesRepository.findById(services.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServices are not directly saved in db
        em.detach(updatedServices);
        updatedServices.name(UPDATED_NAME);
        ServicesDTO servicesDTO = servicesMapper.toDto(updatedServices);

        restServicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servicesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
        Services testServices = servicesList.get(servicesList.size() - 1);
        assertThat(testServices.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingServices() throws Exception {
        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();
        services.setId(longCount.incrementAndGet());

        // Create the Services
        ServicesDTO servicesDTO = servicesMapper.toDto(services);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServices() throws Exception {
        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();
        services.setId(longCount.incrementAndGet());

        // Create the Services
        ServicesDTO servicesDTO = servicesMapper.toDto(services);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServices() throws Exception {
        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();
        services.setId(longCount.incrementAndGet());

        // Create the Services
        ServicesDTO servicesDTO = servicesMapper.toDto(services);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServicesWithPatch() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();

        // Update the services using partial update
        Services partialUpdatedServices = new Services();
        partialUpdatedServices.setId(services.getId());

        partialUpdatedServices.name(UPDATED_NAME);

        restServicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServices.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServices))
            )
            .andExpect(status().isOk());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
        Services testServices = servicesList.get(servicesList.size() - 1);
        assertThat(testServices.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateServicesWithPatch() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();

        // Update the services using partial update
        Services partialUpdatedServices = new Services();
        partialUpdatedServices.setId(services.getId());

        partialUpdatedServices.name(UPDATED_NAME);

        restServicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServices.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServices))
            )
            .andExpect(status().isOk());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
        Services testServices = servicesList.get(servicesList.size() - 1);
        assertThat(testServices.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingServices() throws Exception {
        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();
        services.setId(longCount.incrementAndGet());

        // Create the Services
        ServicesDTO servicesDTO = servicesMapper.toDto(services);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServices() throws Exception {
        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();
        services.setId(longCount.incrementAndGet());

        // Create the Services
        ServicesDTO servicesDTO = servicesMapper.toDto(services);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServices() throws Exception {
        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();
        services.setId(longCount.incrementAndGet());

        // Create the Services
        ServicesDTO servicesDTO = servicesMapper.toDto(services);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(servicesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        int databaseSizeBeforeDelete = servicesRepository.findAll().size();

        // Delete the services
        restServicesMockMvc
            .perform(delete(ENTITY_API_URL_ID, services.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
