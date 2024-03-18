package com.radiology.health.app.service.impl;

import com.radiology.health.app.domain.PatientTestInfo;
import com.radiology.health.app.repository.PatientTestInfoRepository;
import com.radiology.health.app.service.PatientTestInfoService;
import com.radiology.health.app.service.dto.PatientTestInfoDTO;
import com.radiology.health.app.service.mapper.PatientTestInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.app.domain.PatientTestInfo}.
 */
@Service
@Transactional
public class PatientTestInfoServiceImpl implements PatientTestInfoService {

    private final Logger log = LoggerFactory.getLogger(PatientTestInfoServiceImpl.class);

    private final PatientTestInfoRepository patientTestInfoRepository;

    private final PatientTestInfoMapper patientTestInfoMapper;

    public PatientTestInfoServiceImpl(PatientTestInfoRepository patientTestInfoRepository, PatientTestInfoMapper patientTestInfoMapper) {
        this.patientTestInfoRepository = patientTestInfoRepository;
        this.patientTestInfoMapper = patientTestInfoMapper;
    }

    @Override
    public PatientTestInfoDTO save(PatientTestInfoDTO patientTestInfoDTO) {
        log.debug("Request to save PatientTestInfo : {}", patientTestInfoDTO);
        PatientTestInfo patientTestInfo = patientTestInfoMapper.toEntity(patientTestInfoDTO);
        patientTestInfo = patientTestInfoRepository.save(patientTestInfo);
        return patientTestInfoMapper.toDto(patientTestInfo);
    }

    @Override
    public PatientTestInfoDTO update(PatientTestInfoDTO patientTestInfoDTO) {
        log.debug("Request to update PatientTestInfo : {}", patientTestInfoDTO);
        PatientTestInfo patientTestInfo = patientTestInfoMapper.toEntity(patientTestInfoDTO);
        patientTestInfo = patientTestInfoRepository.save(patientTestInfo);
        return patientTestInfoMapper.toDto(patientTestInfo);
    }

    @Override
    public Optional<PatientTestInfoDTO> partialUpdate(PatientTestInfoDTO patientTestInfoDTO) {
        log.debug("Request to partially update PatientTestInfo : {}", patientTestInfoDTO);

        return patientTestInfoRepository
            .findById(patientTestInfoDTO.getId())
            .map(existingPatientTestInfo -> {
                patientTestInfoMapper.partialUpdate(existingPatientTestInfo, patientTestInfoDTO);

                return existingPatientTestInfo;
            })
            .map(patientTestInfoRepository::save)
            .map(patientTestInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientTestInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PatientTestInfos");
        return patientTestInfoRepository.findAll(pageable).map(patientTestInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientTestInfoDTO> findOne(Long id) {
        log.debug("Request to get PatientTestInfo : {}", id);
        return patientTestInfoRepository.findById(id).map(patientTestInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PatientTestInfo : {}", id);
        patientTestInfoRepository.deleteById(id);
    }
}
