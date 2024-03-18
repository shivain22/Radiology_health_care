package com.radiology.health.app.service.impl;

import com.radiology.health.app.domain.TestTimings;
import com.radiology.health.app.repository.TestTimingsRepository;
import com.radiology.health.app.service.TestTimingsService;
import com.radiology.health.app.service.dto.TestTimingsDTO;
import com.radiology.health.app.service.mapper.TestTimingsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.app.domain.TestTimings}.
 */
@Service
@Transactional
public class TestTimingsServiceImpl implements TestTimingsService {

    private final Logger log = LoggerFactory.getLogger(TestTimingsServiceImpl.class);

    private final TestTimingsRepository testTimingsRepository;

    private final TestTimingsMapper testTimingsMapper;

    public TestTimingsServiceImpl(TestTimingsRepository testTimingsRepository, TestTimingsMapper testTimingsMapper) {
        this.testTimingsRepository = testTimingsRepository;
        this.testTimingsMapper = testTimingsMapper;
    }

    @Override
    public TestTimingsDTO save(TestTimingsDTO testTimingsDTO) {
        log.debug("Request to save TestTimings : {}", testTimingsDTO);
        TestTimings testTimings = testTimingsMapper.toEntity(testTimingsDTO);
        testTimings = testTimingsRepository.save(testTimings);
        return testTimingsMapper.toDto(testTimings);
    }

    @Override
    public TestTimingsDTO update(TestTimingsDTO testTimingsDTO) {
        log.debug("Request to update TestTimings : {}", testTimingsDTO);
        TestTimings testTimings = testTimingsMapper.toEntity(testTimingsDTO);
        testTimings = testTimingsRepository.save(testTimings);
        return testTimingsMapper.toDto(testTimings);
    }

    @Override
    public Optional<TestTimingsDTO> partialUpdate(TestTimingsDTO testTimingsDTO) {
        log.debug("Request to partially update TestTimings : {}", testTimingsDTO);

        return testTimingsRepository
            .findById(testTimingsDTO.getId())
            .map(existingTestTimings -> {
                testTimingsMapper.partialUpdate(existingTestTimings, testTimingsDTO);

                return existingTestTimings;
            })
            .map(testTimingsRepository::save)
            .map(testTimingsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TestTimingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TestTimings");
        return testTimingsRepository.findAll(pageable).map(testTimingsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TestTimingsDTO> findOne(Long id) {
        log.debug("Request to get TestTimings : {}", id);
        return testTimingsRepository.findById(id).map(testTimingsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TestTimings : {}", id);
        testTimingsRepository.deleteById(id);
    }
}
