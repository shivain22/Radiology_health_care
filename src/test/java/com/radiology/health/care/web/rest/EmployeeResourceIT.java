package com.radiology.health.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.care.IntegrationTest;
import com.radiology.health.care.domain.EmpService;
import com.radiology.health.care.domain.Employee;
import com.radiology.health.care.domain.PatientInfo;
import com.radiology.health.care.domain.Rank;
import com.radiology.health.care.domain.TechnicianEquipmentMapping;
import com.radiology.health.care.domain.Unit;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.repository.EmployeeRepository;
import com.radiology.health.care.service.dto.EmployeeDTO;
import com.radiology.health.care.service.mapper.EmployeeMapper;
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

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TECHNICIAN = false;
    private static final Boolean UPDATED_TECHNICIAN = true;

    private static final String DEFAULT_HIS = "AAAAAAAAAA";
    private static final String UPDATED_HIS = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NO = "BBBBBBBBBB";

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
        Employee employee = new Employee().name(DEFAULT_NAME).technician(DEFAULT_TECHNICIAN).his(DEFAULT_HIS).serviceNo(DEFAULT_SERVICE_NO);
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
        // Add required entity
        EmpService empService;
        if (TestUtil.findAll(em, EmpService.class).isEmpty()) {
            empService = EmpServiceResourceIT.createEntity(em);
            em.persist(empService);
            em.flush();
        } else {
            empService = TestUtil.findAll(em, EmpService.class).get(0);
        }
        employee.setEmpService(empService);
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
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        employee.setUser(user);
        return employee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee().name(UPDATED_NAME).technician(UPDATED_TECHNICIAN).his(UPDATED_HIS).serviceNo(UPDATED_SERVICE_NO);
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
        // Add required entity
        EmpService empService;
        if (TestUtil.findAll(em, EmpService.class).isEmpty()) {
            empService = EmpServiceResourceIT.createUpdatedEntity(em);
            em.persist(empService);
            em.flush();
        } else {
            empService = TestUtil.findAll(em, EmpService.class).get(0);
        }
        employee.setEmpService(empService);
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
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        employee.setUser(user);
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
        assertThat(testEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployee.getTechnician()).isEqualTo(DEFAULT_TECHNICIAN);
        assertThat(testEmployee.getHis()).isEqualTo(DEFAULT_HIS);
        assertThat(testEmployee.getServiceNo()).isEqualTo(DEFAULT_SERVICE_NO);
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].technician").value(hasItem(DEFAULT_TECHNICIAN.booleanValue())))
            .andExpect(jsonPath("$.[*].his").value(hasItem(DEFAULT_HIS)))
            .andExpect(jsonPath("$.[*].serviceNo").value(hasItem(DEFAULT_SERVICE_NO)));
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.technician").value(DEFAULT_TECHNICIAN.booleanValue()))
            .andExpect(jsonPath("$.his").value(DEFAULT_HIS))
            .andExpect(jsonPath("$.serviceNo").value(DEFAULT_SERVICE_NO));
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
    void getAllEmployeesByEmpServiceIsEqualToSomething() throws Exception {
        EmpService empService;
        if (TestUtil.findAll(em, EmpService.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            empService = EmpServiceResourceIT.createEntity(em);
        } else {
            empService = TestUtil.findAll(em, EmpService.class).get(0);
        }
        em.persist(empService);
        em.flush();
        employee.setEmpService(empService);
        employeeRepository.saveAndFlush(employee);
        Long empServiceId = empService.getId();
        // Get all the employeeList where empService equals to empServiceId
        defaultEmployeeShouldBeFound("empServiceId.equals=" + empServiceId);

        // Get all the employeeList where empService equals to (empServiceId + 1)
        defaultEmployeeShouldNotBeFound("empServiceId.equals=" + (empServiceId + 1));
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
    void getAllEmployeesByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            user = UserResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        employee.setUser(user);
        employeeRepository.saveAndFlush(employee);
        Long userId = user.getId();
        // Get all the employeeList where user equals to userId
        defaultEmployeeShouldBeFound("userId.equals=" + userId);

        // Get all the employeeList where user equals to (userId + 1)
        defaultEmployeeShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByTechnicianEquipmentMappingIsEqualToSomething() throws Exception {
        TechnicianEquipmentMapping technicianEquipmentMapping;
        if (TestUtil.findAll(em, TechnicianEquipmentMapping.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            technicianEquipmentMapping = TechnicianEquipmentMappingResourceIT.createEntity(em);
        } else {
            technicianEquipmentMapping = TestUtil.findAll(em, TechnicianEquipmentMapping.class).get(0);
        }
        em.persist(technicianEquipmentMapping);
        em.flush();
        employee.addTechnicianEquipmentMapping(technicianEquipmentMapping);
        employeeRepository.saveAndFlush(employee);
        Long technicianEquipmentMappingId = technicianEquipmentMapping.getId();
        // Get all the employeeList where technicianEquipmentMapping equals to technicianEquipmentMappingId
        defaultEmployeeShouldBeFound("technicianEquipmentMappingId.equals=" + technicianEquipmentMappingId);

        // Get all the employeeList where technicianEquipmentMapping equals to (technicianEquipmentMappingId + 1)
        defaultEmployeeShouldNotBeFound("technicianEquipmentMappingId.equals=" + (technicianEquipmentMappingId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByPatientInfoEmployeeIdIsEqualToSomething() throws Exception {
        PatientInfo patientInfoEmployeeId;
        if (TestUtil.findAll(em, PatientInfo.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            patientInfoEmployeeId = PatientInfoResourceIT.createEntity(em);
        } else {
            patientInfoEmployeeId = TestUtil.findAll(em, PatientInfo.class).get(0);
        }
        em.persist(patientInfoEmployeeId);
        em.flush();
        employee.addPatientInfoEmployeeId(patientInfoEmployeeId);
        employeeRepository.saveAndFlush(employee);
        Long patientInfoEmployeeIdId = patientInfoEmployeeId.getId();
        // Get all the employeeList where patientInfoEmployeeId equals to patientInfoEmployeeIdId
        defaultEmployeeShouldBeFound("patientInfoEmployeeIdId.equals=" + patientInfoEmployeeIdId);

        // Get all the employeeList where patientInfoEmployeeId equals to (patientInfoEmployeeIdId + 1)
        defaultEmployeeShouldNotBeFound("patientInfoEmployeeIdId.equals=" + (patientInfoEmployeeIdId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByPatientInfoEmployeeHisIsEqualToSomething() throws Exception {
        PatientInfo patientInfoEmployeeHis;
        if (TestUtil.findAll(em, PatientInfo.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            patientInfoEmployeeHis = PatientInfoResourceIT.createEntity(em);
        } else {
            patientInfoEmployeeHis = TestUtil.findAll(em, PatientInfo.class).get(0);
        }
        em.persist(patientInfoEmployeeHis);
        em.flush();
        employee.addPatientInfoEmployeeHis(patientInfoEmployeeHis);
        employeeRepository.saveAndFlush(employee);
        Long patientInfoEmployeeHisId = patientInfoEmployeeHis.getId();
        // Get all the employeeList where patientInfoEmployeeHis equals to patientInfoEmployeeHisId
        defaultEmployeeShouldBeFound("patientInfoEmployeeHisId.equals=" + patientInfoEmployeeHisId);

        // Get all the employeeList where patientInfoEmployeeHis equals to (patientInfoEmployeeHisId + 1)
        defaultEmployeeShouldNotBeFound("patientInfoEmployeeHisId.equals=" + (patientInfoEmployeeHisId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByPatientInfoEmployeeServiceNoIsEqualToSomething() throws Exception {
        PatientInfo patientInfoEmployeeServiceNo;
        if (TestUtil.findAll(em, PatientInfo.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            patientInfoEmployeeServiceNo = PatientInfoResourceIT.createEntity(em);
        } else {
            patientInfoEmployeeServiceNo = TestUtil.findAll(em, PatientInfo.class).get(0);
        }
        em.persist(patientInfoEmployeeServiceNo);
        em.flush();
        employee.addPatientInfoEmployeeServiceNo(patientInfoEmployeeServiceNo);
        employeeRepository.saveAndFlush(employee);
        Long patientInfoEmployeeServiceNoId = patientInfoEmployeeServiceNo.getId();
        // Get all the employeeList where patientInfoEmployeeServiceNo equals to patientInfoEmployeeServiceNoId
        defaultEmployeeShouldBeFound("patientInfoEmployeeServiceNoId.equals=" + patientInfoEmployeeServiceNoId);

        // Get all the employeeList where patientInfoEmployeeServiceNo equals to (patientInfoEmployeeServiceNoId + 1)
        defaultEmployeeShouldNotBeFound("patientInfoEmployeeServiceNoId.equals=" + (patientInfoEmployeeServiceNoId + 1));
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].technician").value(hasItem(DEFAULT_TECHNICIAN.booleanValue())))
            .andExpect(jsonPath("$.[*].his").value(hasItem(DEFAULT_HIS)))
            .andExpect(jsonPath("$.[*].serviceNo").value(hasItem(DEFAULT_SERVICE_NO)));

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
        updatedEmployee.name(UPDATED_NAME).technician(UPDATED_TECHNICIAN).his(UPDATED_HIS).serviceNo(UPDATED_SERVICE_NO);
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
        assertThat(testEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployee.getTechnician()).isEqualTo(UPDATED_TECHNICIAN);
        assertThat(testEmployee.getHis()).isEqualTo(UPDATED_HIS);
        assertThat(testEmployee.getServiceNo()).isEqualTo(UPDATED_SERVICE_NO);
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

        partialUpdatedEmployee.name(UPDATED_NAME);

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
        assertThat(testEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployee.getTechnician()).isEqualTo(DEFAULT_TECHNICIAN);
        assertThat(testEmployee.getHis()).isEqualTo(DEFAULT_HIS);
        assertThat(testEmployee.getServiceNo()).isEqualTo(DEFAULT_SERVICE_NO);
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

        partialUpdatedEmployee.name(UPDATED_NAME).technician(UPDATED_TECHNICIAN).his(UPDATED_HIS).serviceNo(UPDATED_SERVICE_NO);

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
        assertThat(testEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployee.getTechnician()).isEqualTo(UPDATED_TECHNICIAN);
        assertThat(testEmployee.getHis()).isEqualTo(UPDATED_HIS);
        assertThat(testEmployee.getServiceNo()).isEqualTo(UPDATED_SERVICE_NO);
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
