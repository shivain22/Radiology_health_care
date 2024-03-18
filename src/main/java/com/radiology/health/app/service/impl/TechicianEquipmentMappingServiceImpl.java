package com.radiology.health.app.service.impl;

import com.radiology.health.app.domain.TechicianEquipmentMapping;
import com.radiology.health.app.repository.TechicianEquipmentMappingRepository;
import com.radiology.health.app.service.TechicianEquipmentMappingService;
import com.radiology.health.app.service.dto.TechicianEquipmentMappingDTO;
import com.radiology.health.app.service.mapper.TechicianEquipmentMappingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.app.domain.TechicianEquipmentMapping}.
 */
@Service
@Transactional
public class TechicianEquipmentMappingServiceImpl implements TechicianEquipmentMappingService {

    private final Logger log = LoggerFactory.getLogger(TechicianEquipmentMappingServiceImpl.class);

    private final TechicianEquipmentMappingRepository techicianEquipmentMappingRepository;

    private final TechicianEquipmentMappingMapper techicianEquipmentMappingMapper;

    public TechicianEquipmentMappingServiceImpl(
        TechicianEquipmentMappingRepository techicianEquipmentMappingRepository,
        TechicianEquipmentMappingMapper techicianEquipmentMappingMapper
    ) {
        this.techicianEquipmentMappingRepository = techicianEquipmentMappingRepository;
        this.techicianEquipmentMappingMapper = techicianEquipmentMappingMapper;
    }

    @Override
    public TechicianEquipmentMappingDTO save(TechicianEquipmentMappingDTO techicianEquipmentMappingDTO) {
        log.debug("Request to save TechicianEquipmentMapping : {}", techicianEquipmentMappingDTO);
        TechicianEquipmentMapping techicianEquipmentMapping = techicianEquipmentMappingMapper.toEntity(techicianEquipmentMappingDTO);
        techicianEquipmentMapping = techicianEquipmentMappingRepository.save(techicianEquipmentMapping);
        return techicianEquipmentMappingMapper.toDto(techicianEquipmentMapping);
    }

    @Override
    public TechicianEquipmentMappingDTO update(TechicianEquipmentMappingDTO techicianEquipmentMappingDTO) {
        log.debug("Request to update TechicianEquipmentMapping : {}", techicianEquipmentMappingDTO);
        TechicianEquipmentMapping techicianEquipmentMapping = techicianEquipmentMappingMapper.toEntity(techicianEquipmentMappingDTO);
        techicianEquipmentMapping = techicianEquipmentMappingRepository.save(techicianEquipmentMapping);
        return techicianEquipmentMappingMapper.toDto(techicianEquipmentMapping);
    }

    @Override
    public Optional<TechicianEquipmentMappingDTO> partialUpdate(TechicianEquipmentMappingDTO techicianEquipmentMappingDTO) {
        log.debug("Request to partially update TechicianEquipmentMapping : {}", techicianEquipmentMappingDTO);

        return techicianEquipmentMappingRepository
            .findById(techicianEquipmentMappingDTO.getId())
            .map(existingTechicianEquipmentMapping -> {
                techicianEquipmentMappingMapper.partialUpdate(existingTechicianEquipmentMapping, techicianEquipmentMappingDTO);

                return existingTechicianEquipmentMapping;
            })
            .map(techicianEquipmentMappingRepository::save)
            .map(techicianEquipmentMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TechicianEquipmentMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TechicianEquipmentMappings");
        return techicianEquipmentMappingRepository.findAll(pageable).map(techicianEquipmentMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TechicianEquipmentMappingDTO> findOne(Long id) {
        log.debug("Request to get TechicianEquipmentMapping : {}", id);
        return techicianEquipmentMappingRepository.findById(id).map(techicianEquipmentMappingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TechicianEquipmentMapping : {}", id);
        techicianEquipmentMappingRepository.deleteById(id);
    }
}
