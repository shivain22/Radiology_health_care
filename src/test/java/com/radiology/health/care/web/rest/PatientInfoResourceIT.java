package com.radiology.health.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.radiology.health.care.IntegrationTest;
import com.radiology.health.care.domain.Employee;
import com.radiology.health.care.domain.PatientInfo;
import com.radiology.health.care.domain.PatientTestTimings;
import com.radiology.health.care.repository.PatientInfoRepository;
import com.radiology.health.care.service.PatientInfoService;
import com.radiology.health.care.service.dto.PatientInfoDTO;
import com.radiology.health.care.service.mapper.PatientInfoMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PatientInfoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PatientInfoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final Integer SMALLER_AGE = 1 - 1;

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_BIRTH = "BBBBBBBBBB";

    private static final Long DEFAULT_MOBILE = 1L;
    private static final Long UPDATED_MOBILE = 2L;
    private static final Long SMALLER_MOBILE = 1L - 1L;

    private static final String DEFAULT_RELATION = "AAAAAAAAAA";
    private static final String UPDATED_RELATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/patient-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PatientInfoRepository patientInfoRepository;

    @Mock
    private PatientInfoRepository patientInfoRepositoryMock;

    @Autowired
    private PatientInfoMapper patientInfoMapper;

    @Mock
    private PatientInfoService patientInfoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientInfoMockMvc;

    private PatientInfo patientInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientInfo createEntity(EntityManager em) {
        PatientInfo patientInfo = new PatientInfo()
            .name(DEFAULT_NAME)
            .age(DEFAULT_AGE)
            .gender(DEFAULT_GENDER)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .mobile(DEFAULT_MOBILE)
            .relation(DEFAULT_RELATION);
        return patientInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientInfo createUpdatedEntity(EntityManager em) {
        PatientInfo patientInfo = new PatientInfo()
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .mobile(UPDATED_MOBILE)
            .relation(UPDATED_RELATION);
        return patientInfo;
    }

    @BeforeEach
    public void initTest() {
        patientInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createPatientInfo() throws Exception {
        int databaseSizeBeforeCreate = patientInfoRepository.findAll().size();
        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);
        restPatientInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PatientInfo testPatientInfo = patientInfoList.get(patientInfoList.size() - 1);
        assertThat(testPatientInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPatientInfo.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPatientInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatientInfo.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testPatientInfo.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testPatientInfo.getRelation()).isEqualTo(DEFAULT_RELATION);
    }

    @Test
    @Transactional
    void createPatientInfoWithExistingId() throws Exception {
        // Create the PatientInfo with an existing ID
        patientInfo.setId(1L);
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        int databaseSizeBeforeCreate = patientInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPatientInfos() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList
        restPatientInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPatientInfosWithEagerRelationshipsIsEnabled() throws Exception {
        when(patientInfoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPatientInfoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(patientInfoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPatientInfosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(patientInfoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPatientInfoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(patientInfoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPatientInfo() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get the patientInfo
        restPatientInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, patientInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patientInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION));
    }

    @Test
    @Transactional
    void getPatientInfosByIdFiltering() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        Long id = patientInfo.getId();

        defaultPatientInfoShouldBeFound("id.equals=" + id);
        defaultPatientInfoShouldNotBeFound("id.notEquals=" + id);

        defaultPatientInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPatientInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultPatientInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPatientInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPatientInfosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where name equals to DEFAULT_NAME
        defaultPatientInfoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the patientInfoList where name equals to UPDATED_NAME
        defaultPatientInfoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPatientInfoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the patientInfoList where name equals to UPDATED_NAME
        defaultPatientInfoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where name is not null
        defaultPatientInfoShouldBeFound("name.specified=true");

        // Get all the patientInfoList where name is null
        defaultPatientInfoShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByNameContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where name contains DEFAULT_NAME
        defaultPatientInfoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the patientInfoList where name contains UPDATED_NAME
        defaultPatientInfoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where name does not contain DEFAULT_NAME
        defaultPatientInfoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the patientInfoList where name does not contain UPDATED_NAME
        defaultPatientInfoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where age equals to DEFAULT_AGE
        defaultPatientInfoShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the patientInfoList where age equals to UPDATED_AGE
        defaultPatientInfoShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where age in DEFAULT_AGE or UPDATED_AGE
        defaultPatientInfoShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the patientInfoList where age equals to UPDATED_AGE
        defaultPatientInfoShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where age is not null
        defaultPatientInfoShouldBeFound("age.specified=true");

        // Get all the patientInfoList where age is null
        defaultPatientInfoShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where age is greater than or equal to DEFAULT_AGE
        defaultPatientInfoShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the patientInfoList where age is greater than or equal to UPDATED_AGE
        defaultPatientInfoShouldNotBeFound("age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where age is less than or equal to DEFAULT_AGE
        defaultPatientInfoShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the patientInfoList where age is less than or equal to SMALLER_AGE
        defaultPatientInfoShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where age is less than DEFAULT_AGE
        defaultPatientInfoShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the patientInfoList where age is less than UPDATED_AGE
        defaultPatientInfoShouldBeFound("age.lessThan=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where age is greater than DEFAULT_AGE
        defaultPatientInfoShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the patientInfoList where age is greater than SMALLER_AGE
        defaultPatientInfoShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where gender equals to DEFAULT_GENDER
        defaultPatientInfoShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the patientInfoList where gender equals to UPDATED_GENDER
        defaultPatientInfoShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientInfosByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultPatientInfoShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the patientInfoList where gender equals to UPDATED_GENDER
        defaultPatientInfoShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientInfosByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where gender is not null
        defaultPatientInfoShouldBeFound("gender.specified=true");

        // Get all the patientInfoList where gender is null
        defaultPatientInfoShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByGenderContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where gender contains DEFAULT_GENDER
        defaultPatientInfoShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the patientInfoList where gender contains UPDATED_GENDER
        defaultPatientInfoShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientInfosByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where gender does not contain DEFAULT_GENDER
        defaultPatientInfoShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the patientInfoList where gender does not contain UPDATED_GENDER
        defaultPatientInfoShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultPatientInfoShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the patientInfoList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultPatientInfoShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultPatientInfoShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the patientInfoList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultPatientInfoShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfBirth is not null
        defaultPatientInfoShouldBeFound("dateOfBirth.specified=true");

        // Get all the patientInfoList where dateOfBirth is null
        defaultPatientInfoShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfBirthContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfBirth contains DEFAULT_DATE_OF_BIRTH
        defaultPatientInfoShouldBeFound("dateOfBirth.contains=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the patientInfoList where dateOfBirth contains UPDATED_DATE_OF_BIRTH
        defaultPatientInfoShouldNotBeFound("dateOfBirth.contains=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfBirthNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfBirth does not contain DEFAULT_DATE_OF_BIRTH
        defaultPatientInfoShouldNotBeFound("dateOfBirth.doesNotContain=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the patientInfoList where dateOfBirth does not contain UPDATED_DATE_OF_BIRTH
        defaultPatientInfoShouldBeFound("dateOfBirth.doesNotContain=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllPatientInfosByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where mobile equals to DEFAULT_MOBILE
        defaultPatientInfoShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the patientInfoList where mobile equals to UPDATED_MOBILE
        defaultPatientInfoShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultPatientInfoShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the patientInfoList where mobile equals to UPDATED_MOBILE
        defaultPatientInfoShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where mobile is not null
        defaultPatientInfoShouldBeFound("mobile.specified=true");

        // Get all the patientInfoList where mobile is null
        defaultPatientInfoShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByMobileIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where mobile is greater than or equal to DEFAULT_MOBILE
        defaultPatientInfoShouldBeFound("mobile.greaterThanOrEqual=" + DEFAULT_MOBILE);

        // Get all the patientInfoList where mobile is greater than or equal to UPDATED_MOBILE
        defaultPatientInfoShouldNotBeFound("mobile.greaterThanOrEqual=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByMobileIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where mobile is less than or equal to DEFAULT_MOBILE
        defaultPatientInfoShouldBeFound("mobile.lessThanOrEqual=" + DEFAULT_MOBILE);

        // Get all the patientInfoList where mobile is less than or equal to SMALLER_MOBILE
        defaultPatientInfoShouldNotBeFound("mobile.lessThanOrEqual=" + SMALLER_MOBILE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByMobileIsLessThanSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where mobile is less than DEFAULT_MOBILE
        defaultPatientInfoShouldNotBeFound("mobile.lessThan=" + DEFAULT_MOBILE);

        // Get all the patientInfoList where mobile is less than UPDATED_MOBILE
        defaultPatientInfoShouldBeFound("mobile.lessThan=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByMobileIsGreaterThanSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where mobile is greater than DEFAULT_MOBILE
        defaultPatientInfoShouldNotBeFound("mobile.greaterThan=" + DEFAULT_MOBILE);

        // Get all the patientInfoList where mobile is greater than SMALLER_MOBILE
        defaultPatientInfoShouldBeFound("mobile.greaterThan=" + SMALLER_MOBILE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRelationIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where relation equals to DEFAULT_RELATION
        defaultPatientInfoShouldBeFound("relation.equals=" + DEFAULT_RELATION);

        // Get all the patientInfoList where relation equals to UPDATED_RELATION
        defaultPatientInfoShouldNotBeFound("relation.equals=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRelationIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where relation in DEFAULT_RELATION or UPDATED_RELATION
        defaultPatientInfoShouldBeFound("relation.in=" + DEFAULT_RELATION + "," + UPDATED_RELATION);

        // Get all the patientInfoList where relation equals to UPDATED_RELATION
        defaultPatientInfoShouldNotBeFound("relation.in=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRelationIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where relation is not null
        defaultPatientInfoShouldBeFound("relation.specified=true");

        // Get all the patientInfoList where relation is null
        defaultPatientInfoShouldNotBeFound("relation.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByRelationContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where relation contains DEFAULT_RELATION
        defaultPatientInfoShouldBeFound("relation.contains=" + DEFAULT_RELATION);

        // Get all the patientInfoList where relation contains UPDATED_RELATION
        defaultPatientInfoShouldNotBeFound("relation.contains=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRelationNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where relation does not contain DEFAULT_RELATION
        defaultPatientInfoShouldNotBeFound("relation.doesNotContain=" + DEFAULT_RELATION);

        // Get all the patientInfoList where relation does not contain UPDATED_RELATION
        defaultPatientInfoShouldBeFound("relation.doesNotContain=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllPatientInfosByEmployeeIdIsEqualToSomething() throws Exception {
        Employee employeeId;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            patientInfoRepository.saveAndFlush(patientInfo);
            employeeId = EmployeeResourceIT.createEntity(em);
        } else {
            employeeId = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employeeId);
        em.flush();
        patientInfo.setEmployeeId(employeeId);
        patientInfoRepository.saveAndFlush(patientInfo);
        Long employeeIdId = employeeId.getId();
        // Get all the patientInfoList where employeeId equals to employeeIdId
        defaultPatientInfoShouldBeFound("employeeIdId.equals=" + employeeIdId);

        // Get all the patientInfoList where employeeId equals to (employeeIdId + 1)
        defaultPatientInfoShouldNotBeFound("employeeIdId.equals=" + (employeeIdId + 1));
    }

    @Test
    @Transactional
    void getAllPatientInfosByEmployeeHisIsEqualToSomething() throws Exception {
        Employee employeeHis;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            patientInfoRepository.saveAndFlush(patientInfo);
            employeeHis = EmployeeResourceIT.createEntity(em);
        } else {
            employeeHis = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employeeHis);
        em.flush();
        patientInfo.setEmployeeHis(employeeHis);
        patientInfoRepository.saveAndFlush(patientInfo);
        Long employeeHisId = employeeHis.getId();
        // Get all the patientInfoList where employeeHis equals to employeeHisId
        defaultPatientInfoShouldBeFound("employeeHisId.equals=" + employeeHisId);

        // Get all the patientInfoList where employeeHis equals to (employeeHisId + 1)
        defaultPatientInfoShouldNotBeFound("employeeHisId.equals=" + (employeeHisId + 1));
    }

    @Test
    @Transactional
    void getAllPatientInfosByEmployeeServiceNoIsEqualToSomething() throws Exception {
        Employee employeeServiceNo;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            patientInfoRepository.saveAndFlush(patientInfo);
            employeeServiceNo = EmployeeResourceIT.createEntity(em);
        } else {
            employeeServiceNo = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employeeServiceNo);
        em.flush();
        patientInfo.setEmployeeServiceNo(employeeServiceNo);
        patientInfoRepository.saveAndFlush(patientInfo);
        Long employeeServiceNoId = employeeServiceNo.getId();
        // Get all the patientInfoList where employeeServiceNo equals to employeeServiceNoId
        defaultPatientInfoShouldBeFound("employeeServiceNoId.equals=" + employeeServiceNoId);

        // Get all the patientInfoList where employeeServiceNo equals to (employeeServiceNoId + 1)
        defaultPatientInfoShouldNotBeFound("employeeServiceNoId.equals=" + (employeeServiceNoId + 1));
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientTestTimingsIsEqualToSomething() throws Exception {
        PatientTestTimings patientTestTimings;
        if (TestUtil.findAll(em, PatientTestTimings.class).isEmpty()) {
            patientInfoRepository.saveAndFlush(patientInfo);
            patientTestTimings = PatientTestTimingsResourceIT.createEntity(em);
        } else {
            patientTestTimings = TestUtil.findAll(em, PatientTestTimings.class).get(0);
        }
        em.persist(patientTestTimings);
        em.flush();
        patientInfo.addPatientTestTimings(patientTestTimings);
        patientInfoRepository.saveAndFlush(patientInfo);
        Long patientTestTimingsId = patientTestTimings.getId();
        // Get all the patientInfoList where patientTestTimings equals to patientTestTimingsId
        defaultPatientInfoShouldBeFound("patientTestTimingsId.equals=" + patientTestTimingsId);

        // Get all the patientInfoList where patientTestTimings equals to (patientTestTimingsId + 1)
        defaultPatientInfoShouldNotBeFound("patientTestTimingsId.equals=" + (patientTestTimingsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPatientInfoShouldBeFound(String filter) throws Exception {
        restPatientInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION)));

        // Check, that the count call also returns 1
        restPatientInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPatientInfoShouldNotBeFound(String filter) throws Exception {
        restPatientInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPatientInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPatientInfo() throws Exception {
        // Get the patientInfo
        restPatientInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPatientInfo() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();

        // Update the patientInfo
        PatientInfo updatedPatientInfo = patientInfoRepository.findById(patientInfo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPatientInfo are not directly saved in db
        em.detach(updatedPatientInfo);
        updatedPatientInfo
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .mobile(UPDATED_MOBILE)
            .relation(UPDATED_RELATION);
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(updatedPatientInfo);

        restPatientInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
        PatientInfo testPatientInfo = patientInfoList.get(patientInfoList.size() - 1);
        assertThat(testPatientInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPatientInfo.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPatientInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatientInfo.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPatientInfo.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPatientInfo.getRelation()).isEqualTo(UPDATED_RELATION);
    }

    @Test
    @Transactional
    void putNonExistingPatientInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();
        patientInfo.setId(longCount.incrementAndGet());

        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPatientInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();
        patientInfo.setId(longCount.incrementAndGet());

        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPatientInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();
        patientInfo.setId(longCount.incrementAndGet());

        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePatientInfoWithPatch() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();

        // Update the patientInfo using partial update
        PatientInfo partialUpdatedPatientInfo = new PatientInfo();
        partialUpdatedPatientInfo.setId(patientInfo.getId());

        partialUpdatedPatientInfo.name(UPDATED_NAME).mobile(UPDATED_MOBILE).relation(UPDATED_RELATION);

        restPatientInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatientInfo))
            )
            .andExpect(status().isOk());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
        PatientInfo testPatientInfo = patientInfoList.get(patientInfoList.size() - 1);
        assertThat(testPatientInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPatientInfo.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPatientInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatientInfo.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testPatientInfo.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPatientInfo.getRelation()).isEqualTo(UPDATED_RELATION);
    }

    @Test
    @Transactional
    void fullUpdatePatientInfoWithPatch() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();

        // Update the patientInfo using partial update
        PatientInfo partialUpdatedPatientInfo = new PatientInfo();
        partialUpdatedPatientInfo.setId(patientInfo.getId());

        partialUpdatedPatientInfo
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .mobile(UPDATED_MOBILE)
            .relation(UPDATED_RELATION);

        restPatientInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatientInfo))
            )
            .andExpect(status().isOk());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
        PatientInfo testPatientInfo = patientInfoList.get(patientInfoList.size() - 1);
        assertThat(testPatientInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPatientInfo.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPatientInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatientInfo.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPatientInfo.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPatientInfo.getRelation()).isEqualTo(UPDATED_RELATION);
    }

    @Test
    @Transactional
    void patchNonExistingPatientInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();
        patientInfo.setId(longCount.incrementAndGet());

        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patientInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPatientInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();
        patientInfo.setId(longCount.incrementAndGet());

        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPatientInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();
        patientInfo.setId(longCount.incrementAndGet());

        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePatientInfo() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        int databaseSizeBeforeDelete = patientInfoRepository.findAll().size();

        // Delete the patientInfo
        restPatientInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, patientInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
