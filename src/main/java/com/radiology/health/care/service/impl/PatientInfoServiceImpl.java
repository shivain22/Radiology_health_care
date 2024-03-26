package com.radiology.health.care.service.impl;

import com.radiology.health.care.domain.PatientInfo;
import com.radiology.health.care.repository.PatientInfoRepository;
import com.radiology.health.care.service.PatientInfoService;
import com.radiology.health.care.service.dto.PatientInfoDTO;
import com.radiology.health.care.service.mapper.PatientInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.care.domain.PatientInfo}.
 */
@Service
@Transactional
public class PatientInfoServiceImpl implements PatientInfoService {

    private final Logger log = LoggerFactory.getLogger(PatientInfoServiceImpl.class);

    private final PatientInfoRepository patientInfoRepository;

    private final PatientInfoMapper patientInfoMapper;

    public PatientInfoServiceImpl(PatientInfoRepository patientInfoRepository, PatientInfoMapper patientInfoMapper) {
        this.patientInfoRepository = patientInfoRepository;
        this.patientInfoMapper = patientInfoMapper;
    }

    @Override
    public PatientInfoDTO save(PatientInfoDTO patientInfoDTO) {
        log.debug("Request to save PatientInfo : {}", patientInfoDTO);
        PatientInfo patientInfo = patientInfoMapper.toEntity(patientInfoDTO);
        patientInfo = patientInfoRepository.save(patientInfo);
        return patientInfoMapper.toDto(patientInfo);
    }

    @Override
    public PatientInfoDTO update(PatientInfoDTO patientInfoDTO) {
        log.debug("Request to update PatientInfo : {}", patientInfoDTO);
        PatientInfo patientInfo = patientInfoMapper.toEntity(patientInfoDTO);
        patientInfo = patientInfoRepository.save(patientInfo);
        return patientInfoMapper.toDto(patientInfo);
    }

    @Override
    public Optional<PatientInfoDTO> partialUpdate(PatientInfoDTO patientInfoDTO) {
        log.debug("Request to partially update PatientInfo : {}", patientInfoDTO);

        return patientInfoRepository
            .findById(patientInfoDTO.getId())
            .map(existingPatientInfo -> {
                patientInfoMapper.partialUpdate(existingPatientInfo, patientInfoDTO);

                return existingPatientInfo;
            })
            .map(patientInfoRepository::save)
            .map(patientInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PatientInfos");
        return patientInfoRepository.findAll(pageable).map(patientInfoMapper::toDto);
    }

    public Page<PatientInfoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return patientInfoRepository.findAllWithEagerRelationships(pageable).map(patientInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientInfoDTO> findOne(Long id) {
        log.debug("Request to get PatientInfo : {}", id);
        return patientInfoRepository.findOneWithEagerRelationships(id).map(patientInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PatientInfo : {}", id);
        patientInfoRepository.deleteById(id);
    }
}
