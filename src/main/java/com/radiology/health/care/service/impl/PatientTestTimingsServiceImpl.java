package com.radiology.health.care.service.impl;

import com.radiology.health.care.domain.PatientTestTimings;
import com.radiology.health.care.domain.TestCategories;
import com.radiology.health.care.repository.PatientTestTimingsRepository;
import com.radiology.health.care.repository.TestCategoriesRepository;
import com.radiology.health.care.repository.UserRepository;
import com.radiology.health.care.security.SecurityUtils;
import com.radiology.health.care.service.PatientTestTimingsService;
import com.radiology.health.care.service.TestCategoriesService;
import com.radiology.health.care.service.dto.AdminUserDTO;
import com.radiology.health.care.service.dto.PatientTestTimingsDTO;
import com.radiology.health.care.service.dto.TestCategoriesDTO;
import com.radiology.health.care.service.mapper.PatientTestTimingsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.care.domain.PatientTestTimings}.
 */
@Service
@Transactional
public class PatientTestTimingsServiceImpl implements PatientTestTimingsService {

    private final Logger log = LoggerFactory.getLogger(PatientTestTimingsServiceImpl.class);

    private final PatientTestTimingsRepository patientTestTimingsRepository;

    private final PatientTestTimingsMapper patientTestTimingsMapper;

    private final UserRepository userRepository;

    private final TestCategoriesService testCategoriesService;

    public PatientTestTimingsServiceImpl(
        PatientTestTimingsRepository patientTestTimingsRepository,
        PatientTestTimingsMapper patientTestTimingsMapper,
        UserRepository userRepository,
        TestCategoriesRepository testCategoriesRepository,
        TestCategoriesService testCategoriesService
    ) {
        this.patientTestTimingsRepository = patientTestTimingsRepository;
        this.patientTestTimingsMapper = patientTestTimingsMapper;
        this.userRepository = userRepository;

        this.testCategoriesService = testCategoriesService;
    }

    @Override
    public PatientTestTimingsDTO save(PatientTestTimingsDTO patientTestTimingsDTO) {
        log.debug("Request to save PatientTestTimings : {}", patientTestTimingsDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        Optional<TestCategoriesDTO> testCategories = testCategoriesService.findOne(patientTestTimingsDTO.getTestCategoriesId());
        patientTestTimingsDTO = patientTestTimingsRepository.setEndTiming(patientTestTimingsDTO, testCategories);
        log.debug("After PatientTestTimings  Endtime set : {}", patientTestTimingsDTO);
        patientTestTimingsDTO.setLogin(adminUser.getLogin());
        PatientTestTimings patientTestTimings = patientTestTimingsMapper.toEntity(patientTestTimingsDTO);
        patientTestTimings = patientTestTimingsRepository.save(patientTestTimings);
        return patientTestTimingsMapper.toDto(patientTestTimings);
    }

    @Override
    public PatientTestTimingsDTO update(PatientTestTimingsDTO patientTestTimingsDTO) {
        log.debug("Request to update PatientTestTimings : {}", patientTestTimingsDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        patientTestTimingsDTO.setLogin(adminUser.getLogin());
        PatientTestTimings patientTestTimings = patientTestTimingsMapper.toEntity(patientTestTimingsDTO);
        patientTestTimings = patientTestTimingsRepository.save(patientTestTimings);
        return patientTestTimingsMapper.toDto(patientTestTimings);
    }

    @Override
    public Optional<PatientTestTimingsDTO> partialUpdate(PatientTestTimingsDTO patientTestTimingsDTO) {
        log.debug("Request to partially update PatientTestTimings : {}", patientTestTimingsDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        patientTestTimingsDTO.setLogin(adminUser.getLogin());

        return patientTestTimingsRepository
            .findById(patientTestTimingsDTO.getId())
            .map(existingPatientTestTimings -> {
                patientTestTimingsMapper.partialUpdate(existingPatientTestTimings, patientTestTimingsDTO);

                return existingPatientTestTimings;
            })
            .map(patientTestTimingsRepository::save)
            .map(patientTestTimingsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientTestTimingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PatientTestTimings");
        return patientTestTimingsRepository.findAll(pageable).map(patientTestTimingsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientTestTimingsDTO> findOne(Long id) {
        log.debug("Request to get PatientTestTimings : {}", id);
        return patientTestTimingsRepository.findById(id).map(patientTestTimingsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PatientTestTimings : {}", id);
        patientTestTimingsRepository.deleteById(id);
    }
}
