package com.radiology.health.care.service.impl;

import com.radiology.health.care.domain.EmpService;
import com.radiology.health.care.repository.EmpServiceRepository;
import com.radiology.health.care.repository.UserRepository;
import com.radiology.health.care.security.SecurityUtils;
import com.radiology.health.care.service.EmpServiceService;
import com.radiology.health.care.service.dto.AdminUserDTO;
import com.radiology.health.care.service.dto.EmpServiceDTO;
import com.radiology.health.care.service.mapper.EmpServiceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.care.domain.EmpService}.
 */
@Service
@Transactional
public class EmpServiceServiceImpl implements EmpServiceService {

    private final Logger log = LoggerFactory.getLogger(EmpServiceServiceImpl.class);

    private final EmpServiceRepository empServiceRepository;

    private final EmpServiceMapper empServiceMapper;

    private final UserRepository userRepository;

    public EmpServiceServiceImpl(
        EmpServiceRepository empServiceRepository,
        EmpServiceMapper empServiceMapper,
        UserRepository userRepository
    ) {
        this.empServiceRepository = empServiceRepository;
        this.empServiceMapper = empServiceMapper;
        this.userRepository = userRepository;
    }

    @Override
    public EmpServiceDTO save(EmpServiceDTO empServiceDTO) {
        log.debug("Request to save EmpService : {}", empServiceDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        empServiceDTO.setUserId(adminUser.getId());
        empServiceDTO.setLogin(adminUser.getLogin());
        EmpService empService = empServiceMapper.toEntity(empServiceDTO);
        empService = empServiceRepository.save(empService);
        return empServiceMapper.toDto(empService);
    }

    @Override
    public EmpServiceDTO update(EmpServiceDTO empServiceDTO) {
        log.debug("Request to update EmpService : {}", empServiceDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        empServiceDTO.setUserId(adminUser.getId());
        empServiceDTO.setLogin(adminUser.getLogin());
        EmpService empService = empServiceMapper.toEntity(empServiceDTO);
        empService = empServiceRepository.save(empService);
        return empServiceMapper.toDto(empService);
    }

    @Override
    public Optional<EmpServiceDTO> partialUpdate(EmpServiceDTO empServiceDTO) {
        log.debug("Request to partially update EmpService : {}", empServiceDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        empServiceDTO.setUserId(adminUser.getId());
        empServiceDTO.setLogin(adminUser.getLogin());

        return empServiceRepository
            .findById(empServiceDTO.getId())
            .map(existingEmpService -> {
                empServiceMapper.partialUpdate(existingEmpService, empServiceDTO);

                return existingEmpService;
            })
            .map(empServiceRepository::save)
            .map(empServiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmpServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmpServices");
        return empServiceRepository.findAll(pageable).map(empServiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmpServiceDTO> findOne(Long id) {
        log.debug("Request to get EmpService : {}", id);
        return empServiceRepository.findById(id).map(empServiceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmpService : {}", id);
        empServiceRepository.deleteById(id);
    }
}
