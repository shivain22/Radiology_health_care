package com.radiology.health.care.service.impl;

import com.radiology.health.care.domain.TestCategories;
import com.radiology.health.care.repository.TestCategoriesRepository;
import com.radiology.health.care.repository.UserRepository;
import com.radiology.health.care.security.SecurityUtils;
import com.radiology.health.care.service.TestCategoriesService;
import com.radiology.health.care.service.dto.AdminUserDTO;
import com.radiology.health.care.service.dto.TestCategoriesDTO;
import com.radiology.health.care.service.mapper.TestCategoriesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.care.domain.TestCategories}.
 */
@Service
@Transactional
public class TestCategoriesServiceImpl implements TestCategoriesService {

    private final Logger log = LoggerFactory.getLogger(TestCategoriesServiceImpl.class);

    private final TestCategoriesRepository testCategoriesRepository;

    private final TestCategoriesMapper testCategoriesMapper;

    private final UserRepository userRepository;

    public TestCategoriesServiceImpl(
        TestCategoriesRepository testCategoriesRepository,
        TestCategoriesMapper testCategoriesMapper,
        UserRepository userRepository
    ) {
        this.testCategoriesRepository = testCategoriesRepository;
        this.testCategoriesMapper = testCategoriesMapper;
        this.userRepository = userRepository;
    }

    @Override
    public TestCategoriesDTO save(TestCategoriesDTO testCategoriesDTO) {
        log.debug("Request to save TestCategories : {}", testCategoriesDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        testCategoriesDTO.setUserId(adminUser.getId());
        testCategoriesDTO.setLogin(adminUser.getLogin());
        TestCategories testCategories = testCategoriesMapper.toEntity(testCategoriesDTO);
        testCategories = testCategoriesRepository.save(testCategories);
        return testCategoriesMapper.toDto(testCategories);
    }

    @Override
    public TestCategoriesDTO update(TestCategoriesDTO testCategoriesDTO) {
        log.debug("Request to update TestCategories : {}", testCategoriesDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        testCategoriesDTO.setUserId(adminUser.getId());
        testCategoriesDTO.setLogin(adminUser.getLogin());
        TestCategories testCategories = testCategoriesMapper.toEntity(testCategoriesDTO);
        testCategories = testCategoriesRepository.save(testCategories);
        return testCategoriesMapper.toDto(testCategories);
    }

    @Override
    public Optional<TestCategoriesDTO> partialUpdate(TestCategoriesDTO testCategoriesDTO) {
        log.debug("Request to partially update TestCategories : {}", testCategoriesDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        testCategoriesDTO.setUserId(adminUser.getId());
        testCategoriesDTO.setLogin(adminUser.getLogin());

        return testCategoriesRepository
            .findById(testCategoriesDTO.getId())
            .map(existingTestCategories -> {
                testCategoriesMapper.partialUpdate(existingTestCategories, testCategoriesDTO);

                return existingTestCategories;
            })
            .map(testCategoriesRepository::save)
            .map(testCategoriesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TestCategoriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TestCategories");
        return testCategoriesRepository.findAll(pageable).map(testCategoriesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TestCategoriesDTO> findOne(Long id) {
        log.debug("Request to get TestCategories : {}", id);
        return testCategoriesRepository.findById(id).map(testCategoriesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TestCategories : {}", id);
        testCategoriesRepository.deleteById(id);
    }
}
