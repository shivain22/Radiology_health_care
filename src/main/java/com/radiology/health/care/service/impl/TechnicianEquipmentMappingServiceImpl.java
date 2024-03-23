package com.radiology.health.care.service.impl;

import com.radiology.health.care.domain.TechnicianEquipmentMapping;
import com.radiology.health.care.repository.TechnicianEquipmentMappingRepository;
import com.radiology.health.care.repository.UserRepository;
import com.radiology.health.care.security.SecurityUtils;
import com.radiology.health.care.service.TechnicianEquipmentMappingService;
import com.radiology.health.care.service.dto.AdminUserDTO;
import com.radiology.health.care.service.dto.TechnicianEquipmentMappingDTO;
import com.radiology.health.care.service.mapper.TechnicianEquipmentMappingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.care.domain.TechnicianEquipmentMapping}.
 */
@Service
@Transactional
public class TechnicianEquipmentMappingServiceImpl implements TechnicianEquipmentMappingService {

    private final Logger log = LoggerFactory.getLogger(TechnicianEquipmentMappingServiceImpl.class);

    private final TechnicianEquipmentMappingRepository technicianEquipmentMappingRepository;

    private final TechnicianEquipmentMappingMapper technicianEquipmentMappingMapper;

    private final UserRepository userRepository;

    public TechnicianEquipmentMappingServiceImpl(
        TechnicianEquipmentMappingRepository technicianEquipmentMappingRepository,
        TechnicianEquipmentMappingMapper technicianEquipmentMappingMapper,
        UserRepository userRepository
    ) {
        this.technicianEquipmentMappingRepository = technicianEquipmentMappingRepository;
        this.technicianEquipmentMappingMapper = technicianEquipmentMappingMapper;
        this.userRepository = userRepository;
    }

    @Override
    public TechnicianEquipmentMappingDTO save(TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO) {
        log.debug("Request to save TechnicianEquipmentMapping : {}", technicianEquipmentMappingDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        technicianEquipmentMappingDTO.setUserId(adminUser.getId());
        technicianEquipmentMappingDTO.setLogin(adminUser.getLogin());
        TechnicianEquipmentMapping technicianEquipmentMapping = technicianEquipmentMappingMapper.toEntity(technicianEquipmentMappingDTO);
        technicianEquipmentMapping = technicianEquipmentMappingRepository.save(technicianEquipmentMapping);
        return technicianEquipmentMappingMapper.toDto(technicianEquipmentMapping);
    }

    @Override
    public TechnicianEquipmentMappingDTO update(TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO) {
        log.debug("Request to update TechnicianEquipmentMapping : {}", technicianEquipmentMappingDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        technicianEquipmentMappingDTO.setUserId(adminUser.getId());
        technicianEquipmentMappingDTO.setLogin(adminUser.getLogin());
        TechnicianEquipmentMapping technicianEquipmentMapping = technicianEquipmentMappingMapper.toEntity(technicianEquipmentMappingDTO);
        technicianEquipmentMapping = technicianEquipmentMappingRepository.save(technicianEquipmentMapping);
        return technicianEquipmentMappingMapper.toDto(technicianEquipmentMapping);
    }

    @Override
    public Optional<TechnicianEquipmentMappingDTO> partialUpdate(TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO) {
        log.debug("Request to partially update TechnicianEquipmentMapping : {}", technicianEquipmentMappingDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        technicianEquipmentMappingDTO.setUserId(adminUser.getId());
        technicianEquipmentMappingDTO.setLogin(adminUser.getLogin());

        return technicianEquipmentMappingRepository
            .findById(technicianEquipmentMappingDTO.getId())
            .map(existingTechnicianEquipmentMapping -> {
                technicianEquipmentMappingMapper.partialUpdate(existingTechnicianEquipmentMapping, technicianEquipmentMappingDTO);

                return existingTechnicianEquipmentMapping;
            })
            .map(technicianEquipmentMappingRepository::save)
            .map(technicianEquipmentMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TechnicianEquipmentMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TechnicianEquipmentMappings");
        return technicianEquipmentMappingRepository.findAll(pageable).map(technicianEquipmentMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TechnicianEquipmentMappingDTO> findOne(Long id) {
        log.debug("Request to get TechnicianEquipmentMapping : {}", id);
        return technicianEquipmentMappingRepository.findById(id).map(technicianEquipmentMappingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TechnicianEquipmentMapping : {}", id);
        technicianEquipmentMappingRepository.deleteById(id);
    }
}
