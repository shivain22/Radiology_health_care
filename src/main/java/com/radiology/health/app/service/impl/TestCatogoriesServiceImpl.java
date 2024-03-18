package com.radiology.health.app.service.impl;

import com.radiology.health.app.domain.TestCatogories;
import com.radiology.health.app.repository.TestCatogoriesRepository;
import com.radiology.health.app.service.TestCatogoriesService;
import com.radiology.health.app.service.dto.TestCatogoriesDTO;
import com.radiology.health.app.service.mapper.TestCatogoriesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.app.domain.TestCatogories}.
 */
@Service
@Transactional
public class TestCatogoriesServiceImpl implements TestCatogoriesService {

    private final Logger log = LoggerFactory.getLogger(TestCatogoriesServiceImpl.class);

    private final TestCatogoriesRepository testCatogoriesRepository;

    private final TestCatogoriesMapper testCatogoriesMapper;

    public TestCatogoriesServiceImpl(TestCatogoriesRepository testCatogoriesRepository, TestCatogoriesMapper testCatogoriesMapper) {
        this.testCatogoriesRepository = testCatogoriesRepository;
        this.testCatogoriesMapper = testCatogoriesMapper;
    }

    @Override
    public TestCatogoriesDTO save(TestCatogoriesDTO testCatogoriesDTO) {
        log.debug("Request to save TestCatogories : {}", testCatogoriesDTO);
        TestCatogories testCatogories = testCatogoriesMapper.toEntity(testCatogoriesDTO);
        testCatogories = testCatogoriesRepository.save(testCatogories);
        return testCatogoriesMapper.toDto(testCatogories);
    }

    @Override
    public TestCatogoriesDTO update(TestCatogoriesDTO testCatogoriesDTO) {
        log.debug("Request to update TestCatogories : {}", testCatogoriesDTO);
        TestCatogories testCatogories = testCatogoriesMapper.toEntity(testCatogoriesDTO);
        testCatogories = testCatogoriesRepository.save(testCatogories);
        return testCatogoriesMapper.toDto(testCatogories);
    }

    @Override
    public Optional<TestCatogoriesDTO> partialUpdate(TestCatogoriesDTO testCatogoriesDTO) {
        log.debug("Request to partially update TestCatogories : {}", testCatogoriesDTO);

        return testCatogoriesRepository
            .findById(testCatogoriesDTO.getId())
            .map(existingTestCatogories -> {
                testCatogoriesMapper.partialUpdate(existingTestCatogories, testCatogoriesDTO);

                return existingTestCatogories;
            })
            .map(testCatogoriesRepository::save)
            .map(testCatogoriesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TestCatogoriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TestCatogories");
        return testCatogoriesRepository.findAll(pageable).map(testCatogoriesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TestCatogoriesDTO> findOne(Long id) {
        log.debug("Request to get TestCatogories : {}", id);
        return testCatogoriesRepository.findById(id).map(testCatogoriesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TestCatogories : {}", id);
        testCatogoriesRepository.deleteById(id);
    }
}
