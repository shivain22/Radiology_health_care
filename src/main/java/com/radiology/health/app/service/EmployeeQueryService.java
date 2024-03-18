package com.radiology.health.app.service;

import com.radiology.health.app.domain.*; // for static metamodels
import com.radiology.health.app.domain.Employee;
import com.radiology.health.app.repository.EmployeeRepository;
import com.radiology.health.app.service.criteria.EmployeeCriteria;
import com.radiology.health.app.service.dto.EmployeeDTO;
import com.radiology.health.app.service.mapper.EmployeeMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Employee} entities in the database.
 * The main input is a {@link EmployeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeDTO} or a {@link Page} of {@link EmployeeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeQueryService extends QueryService<Employee> {

    private final Logger log = LoggerFactory.getLogger(EmployeeQueryService.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeQueryService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findByCriteria(EmployeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeMapper.toDto(employeeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findByCriteria(EmployeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.findAll(specification, page).map(employeeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Employee> createSpecification(EmployeeCriteria criteria) {
        Specification<Employee> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Employee_.id));
            }
            if (criteria.getHis() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHis(), Employee_.his));
            }
            if (criteria.getServiceNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceNo(), Employee_.serviceNo));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Employee_.name));
            }
            if (criteria.getTechnician() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTechnician(), Employee_.technician));
            }
            if (criteria.getUnitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUnitId(), root -> root.join(Employee_.unit, JoinType.LEFT).get(Unit_.id))
                    );
            }
            if (criteria.getServicesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getServicesId(), root -> root.join(Employee_.services, JoinType.LEFT).get(Services_.id))
                    );
            }
            if (criteria.getRankId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRankId(), root -> root.join(Employee_.rank, JoinType.LEFT).get(Rank_.id))
                    );
            }
            if (criteria.getPatientInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPatientInfoId(),
                            root -> root.join(Employee_.patientInfos, JoinType.LEFT).get(PatientInfo_.id)
                        )
                    );
            }
            if (criteria.getTechicianEquipmentMappingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTechicianEquipmentMappingId(),
                            root -> root.join(Employee_.techicianEquipmentMappings, JoinType.LEFT).get(TechicianEquipmentMapping_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
