package com.radiology.health.care.service;

import com.radiology.health.care.domain.*; // for static metamodels
import com.radiology.health.care.domain.PatientInfo;
import com.radiology.health.care.repository.PatientInfoRepository;
import com.radiology.health.care.service.criteria.PatientInfoCriteria;
import com.radiology.health.care.service.dto.PatientInfoDTO;
import com.radiology.health.care.service.mapper.PatientInfoMapper;
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
 * Service for executing complex queries for {@link PatientInfo} entities in the database.
 * The main input is a {@link PatientInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PatientInfoDTO} or a {@link Page} of {@link PatientInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PatientInfoQueryService extends QueryService<PatientInfo> {

    private final Logger log = LoggerFactory.getLogger(PatientInfoQueryService.class);

    private final PatientInfoRepository patientInfoRepository;

    private final PatientInfoMapper patientInfoMapper;

    public PatientInfoQueryService(PatientInfoRepository patientInfoRepository, PatientInfoMapper patientInfoMapper) {
        this.patientInfoRepository = patientInfoRepository;
        this.patientInfoMapper = patientInfoMapper;
    }

    /**
     * Return a {@link List} of {@link PatientInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PatientInfoDTO> findByCriteria(PatientInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PatientInfo> specification = createSpecification(criteria);
        return patientInfoMapper.toDto(patientInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PatientInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PatientInfoDTO> findByCriteria(PatientInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PatientInfo> specification = createSpecification(criteria);
        return patientInfoRepository.findAll(specification, page).map(patientInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PatientInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PatientInfo> specification = createSpecification(criteria);
        return patientInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link PatientInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PatientInfo> createSpecification(PatientInfoCriteria criteria) {
        Specification<PatientInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PatientInfo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PatientInfo_.name));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), PatientInfo_.age));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), PatientInfo_.gender));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateOfBirth(), PatientInfo_.dateOfBirth));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMobile(), PatientInfo_.mobile));
            }
            if (criteria.getRelation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelation(), PatientInfo_.relation));
            }
            if (criteria.getEmployeeIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeIdId(),
                            root -> root.join(PatientInfo_.employeeId, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
            if (criteria.getEmployeeHisId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeHisId(),
                            root -> root.join(PatientInfo_.employeeHis, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
            if (criteria.getEmployeeServiceNoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeServiceNoId(),
                            root -> root.join(PatientInfo_.employeeServiceNo, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
            if (criteria.getPatientTestTimingsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPatientTestTimingsId(),
                            root -> root.join(PatientInfo_.patientTestTimings, JoinType.LEFT).get(PatientTestTimings_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
