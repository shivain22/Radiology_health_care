package com.radiology.health.app.service;

import com.radiology.health.app.domain.*; // for static metamodels
import com.radiology.health.app.domain.PatientTestInfo;
import com.radiology.health.app.repository.PatientTestInfoRepository;
import com.radiology.health.app.service.criteria.PatientTestInfoCriteria;
import com.radiology.health.app.service.dto.PatientTestInfoDTO;
import com.radiology.health.app.service.mapper.PatientTestInfoMapper;
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
 * Service for executing complex queries for {@link PatientTestInfo} entities in the database.
 * The main input is a {@link PatientTestInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PatientTestInfoDTO} or a {@link Page} of {@link PatientTestInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PatientTestInfoQueryService extends QueryService<PatientTestInfo> {

    private final Logger log = LoggerFactory.getLogger(PatientTestInfoQueryService.class);

    private final PatientTestInfoRepository patientTestInfoRepository;

    private final PatientTestInfoMapper patientTestInfoMapper;

    public PatientTestInfoQueryService(PatientTestInfoRepository patientTestInfoRepository, PatientTestInfoMapper patientTestInfoMapper) {
        this.patientTestInfoRepository = patientTestInfoRepository;
        this.patientTestInfoMapper = patientTestInfoMapper;
    }

    /**
     * Return a {@link List} of {@link PatientTestInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PatientTestInfoDTO> findByCriteria(PatientTestInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PatientTestInfo> specification = createSpecification(criteria);
        return patientTestInfoMapper.toDto(patientTestInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PatientTestInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PatientTestInfoDTO> findByCriteria(PatientTestInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PatientTestInfo> specification = createSpecification(criteria);
        return patientTestInfoRepository.findAll(specification, page).map(patientTestInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PatientTestInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PatientTestInfo> specification = createSpecification(criteria);
        return patientTestInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link PatientTestInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PatientTestInfo> createSpecification(PatientTestInfoCriteria criteria) {
        Specification<PatientTestInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PatientTestInfo_.id));
            }
            if (criteria.getPatientInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPatientInfoId(),
                            root -> root.join(PatientTestInfo_.patientInfo, JoinType.LEFT).get(PatientInfo_.id)
                        )
                    );
            }
            if (criteria.getTestTimingsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTestTimingsId(),
                            root -> root.join(PatientTestInfo_.testTimings, JoinType.LEFT).get(TestTimings_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
