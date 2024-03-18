package com.radiology.health.app.service;

import com.radiology.health.app.domain.*; // for static metamodels
import com.radiology.health.app.domain.TechicianEquipmentMapping;
import com.radiology.health.app.repository.TechicianEquipmentMappingRepository;
import com.radiology.health.app.service.criteria.TechicianEquipmentMappingCriteria;
import com.radiology.health.app.service.dto.TechicianEquipmentMappingDTO;
import com.radiology.health.app.service.mapper.TechicianEquipmentMappingMapper;
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
 * Service for executing complex queries for {@link TechicianEquipmentMapping} entities in the database.
 * The main input is a {@link TechicianEquipmentMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TechicianEquipmentMappingDTO} or a {@link Page} of {@link TechicianEquipmentMappingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TechicianEquipmentMappingQueryService extends QueryService<TechicianEquipmentMapping> {

    private final Logger log = LoggerFactory.getLogger(TechicianEquipmentMappingQueryService.class);

    private final TechicianEquipmentMappingRepository techicianEquipmentMappingRepository;

    private final TechicianEquipmentMappingMapper techicianEquipmentMappingMapper;

    public TechicianEquipmentMappingQueryService(
        TechicianEquipmentMappingRepository techicianEquipmentMappingRepository,
        TechicianEquipmentMappingMapper techicianEquipmentMappingMapper
    ) {
        this.techicianEquipmentMappingRepository = techicianEquipmentMappingRepository;
        this.techicianEquipmentMappingMapper = techicianEquipmentMappingMapper;
    }

    /**
     * Return a {@link List} of {@link TechicianEquipmentMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TechicianEquipmentMappingDTO> findByCriteria(TechicianEquipmentMappingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TechicianEquipmentMapping> specification = createSpecification(criteria);
        return techicianEquipmentMappingMapper.toDto(techicianEquipmentMappingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TechicianEquipmentMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TechicianEquipmentMappingDTO> findByCriteria(TechicianEquipmentMappingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TechicianEquipmentMapping> specification = createSpecification(criteria);
        return techicianEquipmentMappingRepository.findAll(specification, page).map(techicianEquipmentMappingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TechicianEquipmentMappingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TechicianEquipmentMapping> specification = createSpecification(criteria);
        return techicianEquipmentMappingRepository.count(specification);
    }

    /**
     * Function to convert {@link TechicianEquipmentMappingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TechicianEquipmentMapping> createSpecification(TechicianEquipmentMappingCriteria criteria) {
        Specification<TechicianEquipmentMapping> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TechicianEquipmentMapping_.id));
            }
            if (criteria.getDateTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateTime(), TechicianEquipmentMapping_.dateTime));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(TechicianEquipmentMapping_.employee, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
            if (criteria.getEquipmentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEquipmentsId(),
                            root -> root.join(TechicianEquipmentMapping_.equipments, JoinType.LEFT).get(Equipments_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
