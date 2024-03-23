package com.radiology.health.care.service.impl;

import com.radiology.health.care.domain.Employee;
import com.radiology.health.care.repository.EmployeeRepository;
import com.radiology.health.care.repository.UserRepository;
import com.radiology.health.care.security.SecurityUtils;
import com.radiology.health.care.service.EmployeeService;
import com.radiology.health.care.service.dto.AdminUserDTO;
import com.radiology.health.care.service.dto.EmployeeDTO;
import com.radiology.health.care.service.mapper.EmployeeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.care.domain.Employee}.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final UserRepository userRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.userRepository = userRepository;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        employeeDTO.setUserId(adminUser.getId());
        employeeDTO.setLogin(adminUser.getLogin());
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO) {
        log.debug("Request to update Employee : {}", employeeDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        employeeDTO.setUserId(adminUser.getId());
        employeeDTO.setLogin(adminUser.getLogin());
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public Optional<EmployeeDTO> partialUpdate(EmployeeDTO employeeDTO) {
        log.debug("Request to partially update Employee : {}", employeeDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        employeeDTO.setUserId(adminUser.getId());
        employeeDTO.setLogin(adminUser.getLogin());

        return employeeRepository
            .findById(employeeDTO.getId())
            .map(existingEmployee -> {
                employeeMapper.partialUpdate(existingEmployee, employeeDTO);

                return existingEmployee;
            })
            .map(employeeRepository::save)
            .map(employeeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id).map(employeeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}
