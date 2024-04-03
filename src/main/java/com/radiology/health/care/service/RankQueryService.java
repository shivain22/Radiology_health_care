package com.radiology.health.care.service;

import com.radiology.health.care.domain.*; // for static metamodels
import com.radiology.health.care.domain.Rank;
import com.radiology.health.care.repository.RankRepository;
import com.radiology.health.care.service.criteria.RankCriteria;
import com.radiology.health.care.service.dto.RankDTO;
import com.radiology.health.care.service.mapper.RankMapper;
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
 * Service for executing complex queries for {@link Rank} entities in the database.
 * The main input is a {@link RankCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RankDTO} or a {@link Page} of {@link RankDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RankQueryService extends QueryService<Rank> {

    private final Logger log = LoggerFactory.getLogger(RankQueryService.class);

    private final RankRepository rankRepository;

    private final RankMapper rankMapper;

    public RankQueryService(RankRepository rankRepository, RankMapper rankMapper) {
        this.rankRepository = rankRepository;
        this.rankMapper = rankMapper;
    }

    /**
     * Return a {@link List} of {@link RankDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RankDTO> findByCriteria(RankCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Rank> specification = createSpecification(criteria);
        return rankMapper.toDto(rankRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RankDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RankDTO> findByCriteria(RankCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Rank> specification = createSpecification(criteria);
        return rankRepository.findAll(specification, page).map(rankMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RankCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Rank> specification = createSpecification(criteria);
        return rankRepository.count(specification);
    }

    /**
     * Function to convert {@link RankCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Rank> createSpecification(RankCriteria criteria) {
        Specification<Rank> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Rank_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Rank_.name));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortName(), Rank_.shortName));
            }
            if (criteria.getDivision() != null) {
                specification = specification.and(buildSpecification(criteria.getDivision(), Rank_.division));
            }
            if (criteria.getEmpServiceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmpServiceId(),
                            root -> root.join(Rank_.empService, JoinType.LEFT).get(EmpService_.id)
                        )
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getUserId(), root -> root.join(Rank_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmployeeId(), root -> root.join(Rank_.employees, JoinType.LEFT).get(Employee_.id))
                    );
            }
        }
        return specification;
    }
}
