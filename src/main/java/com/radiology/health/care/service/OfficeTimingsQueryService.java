package com.radiology.health.care.service;

import com.radiology.health.care.domain.*; // for static metamodels
import com.radiology.health.care.domain.OfficeTimings;
import com.radiology.health.care.repository.OfficeTimingsRepository;
import com.radiology.health.care.service.criteria.OfficeTimingsCriteria;
import com.radiology.health.care.service.dto.OfficeTimingsDTO;
import com.radiology.health.care.service.mapper.OfficeTimingsMapper;
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
 * Service for executing complex queries for {@link OfficeTimings} entities in the database.
 * The main input is a {@link OfficeTimingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OfficeTimingsDTO} or a {@link Page} of {@link OfficeTimingsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OfficeTimingsQueryService extends QueryService<OfficeTimings> {

    private final Logger log = LoggerFactory.getLogger(OfficeTimingsQueryService.class);

    private final OfficeTimingsRepository officeTimingsRepository;

    private final OfficeTimingsMapper officeTimingsMapper;

    public OfficeTimingsQueryService(OfficeTimingsRepository officeTimingsRepository, OfficeTimingsMapper officeTimingsMapper) {
        this.officeTimingsRepository = officeTimingsRepository;
        this.officeTimingsMapper = officeTimingsMapper;
    }

    /**
     * Return a {@link List} of {@link OfficeTimingsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OfficeTimingsDTO> findByCriteria(OfficeTimingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OfficeTimings> specification = createSpecification(criteria);
        return officeTimingsMapper.toDto(officeTimingsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OfficeTimingsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OfficeTimingsDTO> findByCriteria(OfficeTimingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OfficeTimings> specification = createSpecification(criteria);
        return officeTimingsRepository.findAll(specification, page).map(officeTimingsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OfficeTimingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OfficeTimings> specification = createSpecification(criteria);
        return officeTimingsRepository.count(specification);
    }

    /**
     * Function to convert {@link OfficeTimingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OfficeTimings> createSpecification(OfficeTimingsCriteria criteria) {
        Specification<OfficeTimings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OfficeTimings_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), OfficeTimings_.date));
            }
            if (criteria.getShiftStart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShiftStart(), OfficeTimings_.shiftStart));
            }
            if (criteria.getShiftEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShiftEnd(), OfficeTimings_.shiftEnd));
            }
            if (criteria.getDefaultTimings() != null) {
                specification = specification.and(buildSpecification(criteria.getDefaultTimings(), OfficeTimings_.defaultTimings));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(OfficeTimings_.user, JoinType.LEFT).get(User_.id))
                    );
            }
        }
        return specification;
    }
}
