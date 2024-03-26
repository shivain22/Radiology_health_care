package com.radiology.health.care.service;

import com.radiology.health.care.domain.*; // for static metamodels
import com.radiology.health.care.domain.TechnicianEquipmentMapping;
import com.radiology.health.care.repository.TechnicianEquipmentMappingRepository;
import com.radiology.health.care.service.criteria.TechnicianEquipmentMappingCriteria;
import com.radiology.health.care.service.dto.TechnicianEquipmentMappingDTO;
import com.radiology.health.care.service.mapper.TechnicianEquipmentMappingMapper;
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
 * Service for executing complex queries for {@link TechnicianEquipmentMapping} entities in the database.
 * The main input is a {@link TechnicianEquipmentMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TechnicianEquipmentMappingDTO} or a {@link Page} of {@link TechnicianEquipmentMappingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TechnicianEquipmentMappingQueryService extends QueryService<TechnicianEquipmentMapping> {

    private final Logger log = LoggerFactory.getLogger(TechnicianEquipmentMappingQueryService.class);

    private final TechnicianEquipmentMappingRepository technicianEquipmentMappingRepository;

    private final TechnicianEquipmentMappingMapper technicianEquipmentMappingMapper;

    public TechnicianEquipmentMappingQueryService(
        TechnicianEquipmentMappingRepository technicianEquipmentMappingRepository,
        TechnicianEquipmentMappingMapper technicianEquipmentMappingMapper
    ) {
        this.technicianEquipmentMappingRepository = technicianEquipmentMappingRepository;
        this.technicianEquipmentMappingMapper = technicianEquipmentMappingMapper;
    }

    /**
     * Return a {@link List} of {@link TechnicianEquipmentMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TechnicianEquipmentMappingDTO> findByCriteria(TechnicianEquipmentMappingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TechnicianEquipmentMapping> specification = createSpecification(criteria);
        return technicianEquipmentMappingMapper.toDto(technicianEquipmentMappingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TechnicianEquipmentMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TechnicianEquipmentMappingDTO> findByCriteria(TechnicianEquipmentMappingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TechnicianEquipmentMapping> specification = createSpecification(criteria);
        return technicianEquipmentMappingRepository.findAll(specification, page).map(technicianEquipmentMappingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TechnicianEquipmentMappingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TechnicianEquipmentMapping> specification = createSpecification(criteria);
        return technicianEquipmentMappingRepository.count(specification);
    }

    /**
     * Function to convert {@link TechnicianEquipmentMappingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TechnicianEquipmentMapping> createSpecification(TechnicianEquipmentMappingCriteria criteria) {
        Specification<TechnicianEquipmentMapping> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TechnicianEquipmentMapping_.id));
            }
            if (criteria.getDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTime(), TechnicianEquipmentMapping_.dateTime));
            }
            if (criteria.getEquipmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEquipmentId(),
                            root -> root.join(TechnicianEquipmentMapping_.equipment, JoinType.LEFT).get(Equipment_.id)
                        )
                    );
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(TechnicianEquipmentMapping_.employee, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserId(),
                            root -> root.join(TechnicianEquipmentMapping_.user, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
