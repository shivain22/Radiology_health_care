package com.radiology.health.care.service;

import com.radiology.health.care.domain.*; // for static metamodels
import com.radiology.health.care.domain.EmpService;
import com.radiology.health.care.repository.EmpServiceRepository;
import com.radiology.health.care.service.criteria.EmpServiceCriteria;
import com.radiology.health.care.service.dto.EmpServiceDTO;
import com.radiology.health.care.service.mapper.EmpServiceMapper;
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
 * Service for executing complex queries for {@link EmpService} entities in the database.
 * The main input is a {@link EmpServiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmpServiceDTO} or a {@link Page} of {@link EmpServiceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmpServiceQueryService extends QueryService<EmpService> {

    private final Logger log = LoggerFactory.getLogger(EmpServiceQueryService.class);

    private final EmpServiceRepository empServiceRepository;

    private final EmpServiceMapper empServiceMapper;

    public EmpServiceQueryService(EmpServiceRepository empServiceRepository, EmpServiceMapper empServiceMapper) {
        this.empServiceRepository = empServiceRepository;
        this.empServiceMapper = empServiceMapper;
    }

    /**
     * Return a {@link List} of {@link EmpServiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmpServiceDTO> findByCriteria(EmpServiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmpService> specification = createSpecification(criteria);
        return empServiceMapper.toDto(empServiceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmpServiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmpServiceDTO> findByCriteria(EmpServiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmpService> specification = createSpecification(criteria);
        return empServiceRepository.findAll(specification, page).map(empServiceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmpServiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmpService> specification = createSpecification(criteria);
        return empServiceRepository.count(specification);
    }

    /**
     * Function to convert {@link EmpServiceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmpService> createSpecification(EmpServiceCriteria criteria) {
        Specification<EmpService> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmpService_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), EmpService_.name));
            }
            if (criteria.getRankId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRankId(), root -> root.join(EmpService_.ranks, JoinType.LEFT).get(Rank_.id))
                    );
            }
            if (criteria.getUnitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUnitId(), root -> root.join(EmpService_.units, JoinType.LEFT).get(Unit_.id))
                    );
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmpService_.employees, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
