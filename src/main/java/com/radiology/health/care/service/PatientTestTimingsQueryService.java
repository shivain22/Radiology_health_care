package com.radiology.health.care.service;

import com.radiology.health.care.domain.*; // for static metamodels
import com.radiology.health.care.domain.PatientTestTimings;
import com.radiology.health.care.repository.PatientTestTimingsRepository;
import com.radiology.health.care.service.criteria.PatientTestTimingsCriteria;
import com.radiology.health.care.service.dto.PatientTestTimingsDTO;
import com.radiology.health.care.service.mapper.PatientTestTimingsMapper;
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
 * Service for executing complex queries for {@link PatientTestTimings} entities in the database.
 * The main input is a {@link PatientTestTimingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PatientTestTimingsDTO} or a {@link Page} of {@link PatientTestTimingsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PatientTestTimingsQueryService extends QueryService<PatientTestTimings> {

    private final Logger log = LoggerFactory.getLogger(PatientTestTimingsQueryService.class);

    private final PatientTestTimingsRepository patientTestTimingsRepository;

    private final PatientTestTimingsMapper patientTestTimingsMapper;

    public PatientTestTimingsQueryService(
        PatientTestTimingsRepository patientTestTimingsRepository,
        PatientTestTimingsMapper patientTestTimingsMapper
    ) {
        this.patientTestTimingsRepository = patientTestTimingsRepository;
        this.patientTestTimingsMapper = patientTestTimingsMapper;
    }

    /**
     * Return a {@link List} of {@link PatientTestTimingsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PatientTestTimingsDTO> findByCriteria(PatientTestTimingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PatientTestTimings> specification = createSpecification(criteria);
        return patientTestTimingsMapper.toDto(patientTestTimingsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PatientTestTimingsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PatientTestTimingsDTO> findByCriteria(PatientTestTimingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PatientTestTimings> specification = createSpecification(criteria);
        return patientTestTimingsRepository.findAll(specification, page).map(patientTestTimingsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PatientTestTimingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PatientTestTimings> specification = createSpecification(criteria);
        return patientTestTimingsRepository.count(specification);
    }

    /**
     * Function to convert {@link PatientTestTimingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PatientTestTimings> createSpecification(PatientTestTimingsCriteria criteria) {
        Specification<PatientTestTimings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PatientTestTimings_.id));
            }
            if (criteria.getTestTimings() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTestTimings(), PatientTestTimings_.testTimings));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPriority(), PatientTestTimings_.priority));
            }
            if (criteria.getClinicalNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClinicalNote(), PatientTestTimings_.clinicalNote));
            }
            if (criteria.getSpclInstruction() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSpclInstruction(), PatientTestTimings_.spclInstruction));
            }
            if (criteria.getPatientInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPatientInfoId(),
                            root -> root.join(PatientTestTimings_.patientInfo, JoinType.LEFT).get(PatientInfo_.id)
                        )
                    );
            }
            if (criteria.getTestCategoriesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTestCategoriesId(),
                            root -> root.join(PatientTestTimings_.testCategories, JoinType.LEFT).get(TestCategories_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
