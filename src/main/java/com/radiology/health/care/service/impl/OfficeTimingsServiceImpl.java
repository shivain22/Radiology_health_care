package com.radiology.health.care.service.impl;

import com.radiology.health.care.domain.OfficeTimings;
import com.radiology.health.care.repository.OfficeTimingsRepository;
import com.radiology.health.care.repository.UserRepository;
import com.radiology.health.care.security.SecurityUtils;
import com.radiology.health.care.service.OfficeTimingsService;
import com.radiology.health.care.service.dto.AdminUserDTO;
import com.radiology.health.care.service.dto.DefaultOfficeTimingsDTO;
import com.radiology.health.care.service.dto.OfficeTimingsDTO;
import com.radiology.health.care.service.mapper.OfficeTimingsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.care.domain.OfficeTimings}.
 */
@Service
@Transactional
public class OfficeTimingsServiceImpl implements OfficeTimingsService {

    private final Logger log = LoggerFactory.getLogger(OfficeTimingsServiceImpl.class);

    private final OfficeTimingsRepository officeTimingsRepository;

    private final OfficeTimingsMapper officeTimingsMapper;

    private final UserRepository userRepository;

    public OfficeTimingsServiceImpl(
        OfficeTimingsRepository officeTimingsRepository,
        OfficeTimingsMapper officeTimingsMapper,
        UserRepository userRepository
    ) {
        this.officeTimingsRepository = officeTimingsRepository;
        this.officeTimingsMapper = officeTimingsMapper;
        this.userRepository = userRepository;
    }

    @Override
    public OfficeTimingsDTO save(OfficeTimingsDTO officeTimingsDTO) {
        log.debug("Request to save OfficeTimings : {}", officeTimingsDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        officeTimingsDTO.setUserId(adminUser.getId());
        OfficeTimings officeTimings = officeTimingsMapper.toEntity(officeTimingsDTO);

        officeTimings = officeTimingsRepository.save(officeTimings);
        return officeTimingsMapper.toDto(officeTimings);
    }

    @Override
    public OfficeTimingsDTO update(OfficeTimingsDTO officeTimingsDTO) {
        log.debug("Request to update OfficeTimings : {}", officeTimingsDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        officeTimingsDTO.setUserId(adminUser.getId());
        OfficeTimings officeTimings = officeTimingsMapper.toEntity(officeTimingsDTO);
        officeTimings = officeTimingsRepository.save(officeTimings);
        return officeTimingsMapper.toDto(officeTimings);
    }

    @Override
    public Optional<OfficeTimingsDTO> partialUpdate(OfficeTimingsDTO officeTimingsDTO) {
        log.debug("Request to partially update OfficeTimings : {}", officeTimingsDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        officeTimingsDTO.setUserId(adminUser.getId());
        return officeTimingsRepository
            .findById(officeTimingsDTO.getId())
            .map(existingOfficeTimings -> {
                officeTimingsMapper.partialUpdate(existingOfficeTimings, officeTimingsDTO);

                return existingOfficeTimings;
            })
            .map(officeTimingsRepository::save)
            .map(officeTimingsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OfficeTimingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OfficeTimings");
        return officeTimingsRepository.findAll(pageable).map(officeTimingsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OfficeTimingsDTO> findOne(Long id) {
        log.debug("Request to get OfficeTimings : {}", id);
        return officeTimingsRepository.findById(id).map(officeTimingsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OfficeTimings : {}", id);
        officeTimingsRepository.deleteById(id);
    }
}
