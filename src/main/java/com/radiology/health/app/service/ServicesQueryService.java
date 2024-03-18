package com.radiology.health.app.service;

import com.radiology.health.app.domain.*; // for static metamodels
import com.radiology.health.app.domain.Services;
import com.radiology.health.app.repository.ServicesRepository;
import com.radiology.health.app.service.criteria.ServicesCriteria;
import com.radiology.health.app.service.dto.ServicesDTO;
import com.radiology.health.app.service.mapper.ServicesMapper;
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
 * Service for executing complex queries for {@link Services} entities in the database.
 * The main input is a {@link ServicesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServicesDTO} or a {@link Page} of {@link ServicesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServicesQueryService extends QueryService<Services> {

    private final Logger log = LoggerFactory.getLogger(ServicesQueryService.class);

    private final ServicesRepository servicesRepository;

    private final ServicesMapper servicesMapper;

    public ServicesQueryService(ServicesRepository servicesRepository, ServicesMapper servicesMapper) {
        this.servicesRepository = servicesRepository;
        this.servicesMapper = servicesMapper;
    }

    /**
     * Return a {@link List} of {@link ServicesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServicesDTO> findByCriteria(ServicesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Services> specification = createSpecification(criteria);
        return servicesMapper.toDto(servicesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServicesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServicesDTO> findByCriteria(ServicesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Services> specification = createSpecification(criteria);
        return servicesRepository.findAll(specification, page).map(servicesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServicesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Services> specification = createSpecification(criteria);
        return servicesRepository.count(specification);
    }

    /**
     * Function to convert {@link ServicesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Services> createSpecification(ServicesCriteria criteria) {
        Specification<Services> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Services_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Services_.name));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(Services_.employees, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
            if (criteria.getRankId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRankId(), root -> root.join(Services_.ranks, JoinType.LEFT).get(Rank_.id))
                    );
            }
        }
        return specification;
    }
}
