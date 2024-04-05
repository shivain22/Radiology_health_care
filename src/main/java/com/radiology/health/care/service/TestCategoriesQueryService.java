package com.radiology.health.care.service;

import com.radiology.health.care.domain.*; // for static metamodels
import com.radiology.health.care.domain.TestCategories;
import com.radiology.health.care.repository.TestCategoriesRepository;
import com.radiology.health.care.service.criteria.TestCategoriesCriteria;
import com.radiology.health.care.service.dto.TestCategoriesDTO;
import com.radiology.health.care.service.mapper.TestCategoriesMapper;
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
 * Service for executing complex queries for {@link TestCategories} entities in the database.
 * The main input is a {@link TestCategoriesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TestCategoriesDTO} or a {@link Page} of {@link TestCategoriesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TestCategoriesQueryService extends QueryService<TestCategories> {

    private final Logger log = LoggerFactory.getLogger(TestCategoriesQueryService.class);

    private final TestCategoriesRepository testCategoriesRepository;

    private final TestCategoriesMapper testCategoriesMapper;

    public TestCategoriesQueryService(TestCategoriesRepository testCategoriesRepository, TestCategoriesMapper testCategoriesMapper) {
        this.testCategoriesRepository = testCategoriesRepository;
        this.testCategoriesMapper = testCategoriesMapper;
    }

    /**
     * Return a {@link List} of {@link TestCategoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TestCategoriesDTO> findByCriteria(TestCategoriesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TestCategories> specification = createSpecification(criteria);
        return testCategoriesMapper.toDto(testCategoriesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TestCategoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TestCategoriesDTO> findByCriteria(TestCategoriesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TestCategories> specification = createSpecification(criteria);
        return testCategoriesRepository.findAll(specification, page).map(testCategoriesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TestCategoriesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TestCategories> specification = createSpecification(criteria);
        return testCategoriesRepository.count(specification);
    }

    /**
     * Function to convert {@link TestCategoriesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TestCategories> createSpecification(TestCategoriesCriteria criteria) {
        Specification<TestCategories> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TestCategories_.id));
            }
            if (criteria.getTestName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTestName(), TestCategories_.testName));
            }
            if (criteria.getTestDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTestDuration(), TestCategories_.testDuration));
            }
            if (criteria.getEquipmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEquipmentId(),
                            root -> root.join(TestCategories_.equipment, JoinType.LEFT).get(Equipment_.id)
                        )
                    );
            }
            if (criteria.getParentTestCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParentTestCategoryId(),
                            root -> root.join(TestCategories_.parentTestCategory, JoinType.LEFT).get(TestCategories_.id)
                        )
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(TestCategories_.user, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getPatientTestTimingsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPatientTestTimingsId(),
                            root -> root.join(TestCategories_.patientTestTimings, JoinType.LEFT).get(PatientTestTimings_.id)
                        )
                    );
            }
            if (criteria.getTestCategoryParentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTestCategoryParentId(),
                            root -> root.join(TestCategories_.testCategoryParents, JoinType.LEFT).get(TestCategories_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
