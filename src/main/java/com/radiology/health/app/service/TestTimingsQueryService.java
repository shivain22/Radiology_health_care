package com.radiology.health.app.service;

import com.radiology.health.app.domain.*; // for static metamodels
import com.radiology.health.app.domain.TestTimings;
import com.radiology.health.app.repository.TestTimingsRepository;
import com.radiology.health.app.service.criteria.TestTimingsCriteria;
import com.radiology.health.app.service.dto.TestTimingsDTO;
import com.radiology.health.app.service.mapper.TestTimingsMapper;
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
 * Service for executing complex queries for {@link TestTimings} entities in the database.
 * The main input is a {@link TestTimingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TestTimingsDTO} or a {@link Page} of {@link TestTimingsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TestTimingsQueryService extends QueryService<TestTimings> {

    private final Logger log = LoggerFactory.getLogger(TestTimingsQueryService.class);

    private final TestTimingsRepository testTimingsRepository;

    private final TestTimingsMapper testTimingsMapper;

    public TestTimingsQueryService(TestTimingsRepository testTimingsRepository, TestTimingsMapper testTimingsMapper) {
        this.testTimingsRepository = testTimingsRepository;
        this.testTimingsMapper = testTimingsMapper;
    }

    /**
     * Return a {@link List} of {@link TestTimingsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TestTimingsDTO> findByCriteria(TestTimingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TestTimings> specification = createSpecification(criteria);
        return testTimingsMapper.toDto(testTimingsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TestTimingsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TestTimingsDTO> findByCriteria(TestTimingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TestTimings> specification = createSpecification(criteria);
        return testTimingsRepository.findAll(specification, page).map(testTimingsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TestTimingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TestTimings> specification = createSpecification(criteria);
        return testTimingsRepository.count(specification);
    }

    /**
     * Function to convert {@link TestTimingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TestTimings> createSpecification(TestTimingsCriteria criteria) {
        Specification<TestTimings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TestTimings_.id));
            }
            if (criteria.getTimings() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTimings(), TestTimings_.timings));
            }
            if (criteria.getTestCatogoriesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTestCatogoriesId(),
                            root -> root.join(TestTimings_.testCatogories, JoinType.LEFT).get(TestCatogories_.id)
                        )
                    );
            }
            if (criteria.getPatientTestInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPatientTestInfoId(),
                            root -> root.join(TestTimings_.patientTestInfos, JoinType.LEFT).get(PatientTestInfo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
