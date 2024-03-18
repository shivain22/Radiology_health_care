package com.radiology.health.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.app.IntegrationTest;
import com.radiology.health.app.domain.Employee;
import com.radiology.health.app.domain.PatientInfo;
import com.radiology.health.app.domain.Rank;
import com.radiology.health.app.domain.Services;
import com.radiology.health.app.domain.TechicianEquipmentMapping;
import com.radiology.health.app.domain.Unit;
import com.radiology.health.app.repository.EmployeeRepository;
import com.radiology.health.app.service.dto.EmployeeDTO;
import com.radiology.health.app.service.mapper.EmployeeMapper;
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
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeResourceIT {

    private static final String DEFAULT_HIS = "AAAAAAAAAA";
    private static final String UPDATED_HIS = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TECHNICIAN = 1;
    private static final Integer UPDATED_TECHNICIAN = 2;
    private static final Integer SMALLER_TECHNICIAN = 1 - 1;

    private static final String ENTITY_API_URL = "/api/employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee().his(DEFAULT_HIS).serviceNo(DEFAULT_SERVICE_NO).name(DEFAULT_NAME).technician(DEFAULT_TECHNICIAN);
        // Add required entity
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            unit = UnitResourceIT.createEntity(em);
            em.persist(unit);
            em.flush();
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        employee.setUnit(unit);
        // Add required entity
        Services services;
        if (TestUtil.findAll(em, Services.class).isEmpty()) {
            services = ServicesResourceIT.createEntity(em);
            em.persist(services);
            em.flush();
        } else {
            services = TestUtil.findAll(em, Services.class).get(0);
        }
        employee.setServices(services);
        // Add required entity
        Rank rank;
        if (TestUtil.findAll(em, Rank.class).isEmpty()) {
            rank = RankResourceIT.createEntity(em);
            em.persist(rank);
            em.flush();
        } else {
            rank = TestUtil.findAll(em, Rank.class).get(0);
        }
        employee.setRank(rank);
        return employee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee().his(UPDATED_HIS).serviceNo(UPDATED_SERVICE_NO).name(UPDATED_NAME).technician(UPDATED_TECHNICIAN);
        // Add required entity
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            unit = UnitResourceIT.createUpdatedEntity(em);
            em.persist(unit);
            em.flush();
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        employee.setUnit(unit);
        // Add required entity
        Services services;
        if (TestUtil.findAll(em, Services.class).isEmpty()) {
            services = ServicesResourceIT.createUpdatedEntity(em);
            em.persist(services);
            em.flush();
        } else {
            services = TestUtil.findAll(em, Services.class).get(0);
        }
        employee.setServices(services);
        // Add required entity
        Rank rank;
        if (TestUtil.findAll(em, Rank.class).isEmpty()) {
            rank = RankResourceIT.createUpdatedEntity(em);
            em.persist(rank);
            em.flush();
        } else {
            rank = TestUtil.findAll(em, Rank.class).get(0);
        }
        employee.setRank(rank);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getHis()).isEqualTo(DEFAULT_HIS);
        assertThat(testEmployee.getServiceNo()).isEqualTo(DEFAULT_SERVICE_NO);
        assertThat(testEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployee.getTechnician()).isEqualTo(DEFAULT_TECHNICIAN);
    }

    @Test
    @Transactional
    void createEmployeeWithExistingId() throws Exception {
        // Create the Employee with an existing ID
        employee.setId(1L);
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].his").value(hasItem(DEFAULT_HIS)))
            .andExpect(jsonPath("$.[*].serviceNo").value(hasItem(DEFAULT_SERVICE_NO)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].technician").value(hasItem(DEFAULT_TECHNICIAN)));
    }

    @Test
    @Transactional
    void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.his").value(DEFAULT_HIS))
            .andExpect(jsonPath("$.serviceNo").value(DEFAULT_SERVICE_NO))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.technician").value(DEFAULT_TECHNICIAN));
    }

    @Test
    @Transactional
    void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        Long id = employee.getId();

        defaultEmployeeShouldBeFound("id.equals=" + id);
        defaultEmployeeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeesByHisIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where his equals to DEFAULT_HIS
        defaultEmployeeShouldBeFound("his.equals=" + DEFAULT_HIS);

        // Get all the employeeList where his equals to UPDATED_HIS
        defaultEmployeeShouldNotBeFound("his.equals=" + UPDATED_HIS);
    }

    @Test
    @Transactional
    void getAllEmployeesByHisIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where his in DEFAULT_HIS or UPDATED_HIS
        defaultEmployeeShouldBeFound("his.in=" + DEFAULT_HIS + "," + UPDATED_HIS);

        // Get all the employeeList where his equals to UPDATED_HIS
        defaultEmployeeShouldNotBeFound("his.in=" + UPDATED_HIS);
    }

    @Test
    @Transactional
    void getAllEmployeesByHisIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where his is not null
        defaultEmployeeShouldBeFound("his.specified=true");

        // Get all the employeeList where his is null
        defaultEmployeeShouldNotBeFound("his.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByHisContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where his contains DEFAULT_HIS
        defaultEmployeeShouldBeFound("his.contains=" + DEFAULT_HIS);

        // Get all the employeeList where his contains UPDATED_HIS
        defaultEmployeeShouldNotBeFound("his.contains=" + UPDATED_HIS);
    }

    @Test
    @Transactional
    void getAllEmployeesByHisNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where his does not contain DEFAULT_HIS
        defaultEmployeeShouldNotBeFound("his.doesNotContain=" + DEFAULT_HIS);

        // Get all the employeeList where his does not contain UPDATED_HIS
        defaultEmployeeShouldBeFound("his.doesNotContain=" + UPDATED_HIS);
    }

    @Test
    @Transactional
    void getAllEmployeesByServiceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where serviceNo equals to DEFAULT_SERVICE_NO
        defaultEmployeeShouldBeFound("serviceNo.equals=" + DEFAULT_SERVICE_NO);

        // Get all the employeeList where serviceNo equals to UPDATED_SERVICE_NO
        defaultEmployeeShouldNotBeFound("serviceNo.equals=" + UPDATED_SERVICE_NO);
    }

    @Test
    @Transactional
    void getAllEmployeesByServiceNoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where serviceNo in DEFAULT_SERVICE_NO or UPDATED_SERVICE_NO
        defaultEmployeeShouldBeFound("serviceNo.in=" + DEFAULT_SERVICE_NO + "," + UPDATED_SERVICE_NO);

        // Get all the employeeList where serviceNo equals to UPDATED_SERVICE_NO
        defaultEmployeeShouldNotBeFound("serviceNo.in=" + UPDATED_SERVICE_NO);
    }

    @Test
    @Transactional
    void getAllEmployeesByServiceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where serviceNo is not null
        defaultEmployeeShouldBeFound("serviceNo.specified=true");

        // Get all the employeeList where serviceNo is null
        defaultEmployeeShouldNotBeFound("serviceNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByServiceNoContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where serviceNo contains DEFAULT_SERVICE_NO
        defaultEmployeeShouldBeFound("serviceNo.contains=" + DEFAULT_SERVICE_NO);

        // Get all the employeeList where serviceNo contains UPDATED_SERVICE_NO
        defaultEmployeeShouldNotBeFound("serviceNo.contains=" + UPDATED_SERVICE_NO);
    }

    @Test
    @Transactional
    void getAllEmployeesByServiceNoNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where serviceNo does not contain DEFAULT_SERVICE_NO
        defaultEmployeeShouldNotBeFound("serviceNo.doesNotContain=" + DEFAULT_SERVICE_NO);

        // Get all the employeeList where serviceNo does not contain UPDATED_SERVICE_NO
        defaultEmployeeShouldBeFound("serviceNo.doesNotContain=" + UPDATED_SERVICE_NO);
    }

    @Test
    @Transactional
    void getAllEmployeesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where name equals to DEFAULT_NAME
        defaultEmployeeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the employeeList where name equals to UPDATED_NAME
        defaultEmployeeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEmployeeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the employeeList where name equals to UPDATED_NAME
        defaultEmployeeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where name is not null
        defaultEmployeeShouldBeFound("name.specified=true");

        // Get all the employeeList where name is null
        defaultEmployeeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where name contains DEFAULT_NAME
        defaultEmployeeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the employeeList where name contains UPDATED_NAME
        defaultEmployeeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where name does not contain DEFAULT_NAME
        defaultEmployeeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the employeeList where name does not contain UPDATED_NAME
        defaultEmployeeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByTechnicianIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where technician equals to DEFAULT_TECHNICIAN
        defaultEmployeeShouldBeFound("technician.equals=" + DEFAULT_TECHNICIAN);

        // Get all the employeeList where technician equals to UPDATED_TECHNICIAN
        defaultEmployeeShouldNotBeFound("technician.equals=" + UPDATED_TECHNICIAN);
    }

    @Test
    @Transactional
    void getAllEmployeesByTechnicianIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where technician in DEFAULT_TECHNICIAN or UPDATED_TECHNICIAN
        defaultEmployeeShouldBeFound("technician.in=" + DEFAULT_TECHNICIAN + "," + UPDATED_TECHNICIAN);

        // Get all the employeeList where technician equals to UPDATED_TECHNICIAN
        defaultEmployeeShouldNotBeFound("technician.in=" + UPDATED_TECHNICIAN);
    }

    @Test
    @Transactional
    void getAllEmployeesByTechnicianIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where technician is not null
        defaultEmployeeShouldBeFound("technician.specified=true");

        // Get all the employeeList where technician is null
        defaultEmployeeShouldNotBeFound("technician.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByTechnicianIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where technician is greater than or equal to DEFAULT_TECHNICIAN
        defaultEmployeeShouldBeFound("technician.greaterThanOrEqual=" + DEFAULT_TECHNICIAN);

        // Get all the employeeList where technician is greater than or equal to UPDATED_TECHNICIAN
        defaultEmployeeShouldNotBeFound("technician.greaterThanOrEqual=" + UPDATED_TECHNICIAN);
    }

    @Test
    @Transactional
    void getAllEmployeesByTechnicianIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where technician is less than or equal to DEFAULT_TECHNICIAN
        defaultEmployeeShouldBeFound("technician.lessThanOrEqual=" + DEFAULT_TECHNICIAN);

        // Get all the employeeList where technician is less than or equal to SMALLER_TECHNICIAN
        defaultEmployeeShouldNotBeFound("technician.lessThanOrEqual=" + SMALLER_TECHNICIAN);
    }

    @Test
    @Transactional
    void getAllEmployeesByTechnicianIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where technician is less than DEFAULT_TECHNICIAN
        defaultEmployeeShouldNotBeFound("technician.lessThan=" + DEFAULT_TECHNICIAN);

        // Get all the employeeList where technician is less than UPDATED_TECHNICIAN
        defaultEmployeeShouldBeFound("technician.lessThan=" + UPDATED_TECHNICIAN);
    }

    @Test
    @Transactional
    void getAllEmployeesByTechnicianIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where technician is greater than DEFAULT_TECHNICIAN
        defaultEmployeeShouldNotBeFound("technician.greaterThan=" + DEFAULT_TECHNICIAN);

        // Get all the employeeList where technician is greater than SMALLER_TECHNICIAN
        defaultEmployeeShouldBeFound("technician.greaterThan=" + SMALLER_TECHNICIAN);
    }

    @Test
    @Transactional
    void getAllEmployeesByUnitIsEqualToSomething() throws Exception {
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            unit = UnitResourceIT.createEntity(em);
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        em.persist(unit);
        em.flush();
        employee.setUnit(unit);
        employeeRepository.saveAndFlush(employee);
        Long unitId = unit.getId();
        // Get all the employeeList where unit equals to unitId
        defaultEmployeeShouldBeFound("unitId.equals=" + unitId);

        // Get all the employeeList where unit equals to (unitId + 1)
        defaultEmployeeShouldNotBeFound("unitId.equals=" + (unitId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByServicesIsEqualToSomething() throws Exception {
        Services services;
        if (TestUtil.findAll(em, Services.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            services = ServicesResourceIT.createEntity(em);
        } else {
            services = TestUtil.findAll(em, Services.class).get(0);
        }
        em.persist(services);
        em.flush();
        employee.setServices(services);
        employeeRepository.saveAndFlush(employee);
        Long servicesId = services.getId();
        // Get all the employeeList where services equals to servicesId
        defaultEmployeeShouldBeFound("servicesId.equals=" + servicesId);

        // Get all the employeeList where services equals to (servicesId + 1)
        defaultEmployeeShouldNotBeFound("servicesId.equals=" + (servicesId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByRankIsEqualToSomething() throws Exception {
        Rank rank;
        if (TestUtil.findAll(em, Rank.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            rank = RankResourceIT.createEntity(em);
        } else {
            rank = TestUtil.findAll(em, Rank.class).get(0);
        }
        em.persist(rank);
        em.flush();
        employee.setRank(rank);
        employeeRepository.saveAndFlush(employee);
        Long rankId = rank.getId();
        // Get all the employeeList where rank equals to rankId
        defaultEmployeeShouldBeFound("rankId.equals=" + rankId);

        // Get all the employeeList where rank equals to (rankId + 1)
        defaultEmployeeShouldNotBeFound("rankId.equals=" + (rankId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByPatientInfoIsEqualToSomething() throws Exception {
        PatientInfo patientInfo;
        if (TestUtil.findAll(em, PatientInfo.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            patientInfo = PatientInfoResourceIT.createEntity(em);
        } else {
            patientInfo = TestUtil.findAll(em, PatientInfo.class).get(0);
        }
        em.persist(patientInfo);
        em.flush();
        employee.addPatientInfo(patientInfo);
        employeeRepository.saveAndFlush(employee);
        Long patientInfoId = patientInfo.getId();
        // Get all the employeeList where patientInfo equals to patientInfoId
        defaultEmployeeShouldBeFound("patientInfoId.equals=" + patientInfoId);

        // Get all the employeeList where patientInfo equals to (patientInfoId + 1)
        defaultEmployeeShouldNotBeFound("patientInfoId.equals=" + (patientInfoId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByTechicianEquipmentMappingIsEqualToSomething() throws Exception {
        TechicianEquipmentMapping techicianEquipmentMapping;
        if (TestUtil.findAll(em, TechicianEquipmentMapping.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            techicianEquipmentMapping = TechicianEquipmentMappingResourceIT.createEntity(em);
        } else {
            techicianEquipmentMapping = TestUtil.findAll(em, TechicianEquipmentMapping.class).get(0);
        }
        em.persist(techicianEquipmentMapping);
        em.flush();
        employee.addTechicianEquipmentMapping(techicianEquipmentMapping);
        employeeRepository.saveAndFlush(employee);
        Long techicianEquipmentMappingId = techicianEquipmentMapping.getId();
        // Get all the employeeList where techicianEquipmentMapping equals to techicianEquipmentMappingId
        defaultEmployeeShouldBeFound("techicianEquipmentMappingId.equals=" + techicianEquipmentMappingId);

        // Get all the employeeList where techicianEquipmentMapping equals to (techicianEquipmentMappingId + 1)
        defaultEmployeeShouldNotBeFound("techicianEquipmentMappingId.equals=" + (techicianEquipmentMappingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].his").value(hasItem(DEFAULT_HIS)))
            .andExpect(jsonPath("$.[*].serviceNo").value(hasItem(DEFAULT_SERVICE_NO)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].technician").value(hasItem(DEFAULT_TECHNICIAN)));

        // Check, that the count call also returns 1
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee.his(UPDATED_HIS).serviceNo(UPDATED_SERVICE_NO).name(UPDATED_NAME).technician(UPDATED_TECHNICIAN);
        EmployeeDTO employeeDTO = employeeMapper.toDto(updatedEmployee);

        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getHis()).isEqualTo(UPDATED_HIS);
        assertThat(testEmployee.getServiceNo()).isEqualTo(UPDATED_SERVICE_NO);
        assertThat(testEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployee.getTechnician()).isEqualTo(UPDATED_TECHNICIAN);
    }

    @Test
    @Transactional
    void putNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(longCount.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(longCount.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(longCount.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getHis()).isEqualTo(DEFAULT_HIS);
        assertThat(testEmployee.getServiceNo()).isEqualTo(DEFAULT_SERVICE_NO);
        assertThat(testEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployee.getTechnician()).isEqualTo(DEFAULT_TECHNICIAN);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee.his(UPDATED_HIS).serviceNo(UPDATED_SERVICE_NO).name(UPDATED_NAME).technician(UPDATED_TECHNICIAN);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getHis()).isEqualTo(UPDATED_HIS);
        assertThat(testEmployee.getServiceNo()).isEqualTo(UPDATED_SERVICE_NO);
        assertThat(testEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployee.getTechnician()).isEqualTo(UPDATED_TECHNICIAN);
    }

    @Test
    @Transactional
    void patchNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(longCount.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(longCount.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(longCount.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
