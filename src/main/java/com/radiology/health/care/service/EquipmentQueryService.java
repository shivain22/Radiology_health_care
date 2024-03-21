package com.radiology.health.care.service;

import com.radiology.health.care.domain.*; // for static metamodels
import com.radiology.health.care.domain.Equipment;
import com.radiology.health.care.repository.EquipmentRepository;
import com.radiology.health.care.service.criteria.EquipmentCriteria;
import com.radiology.health.care.service.dto.EquipmentDTO;
import com.radiology.health.care.service.mapper.EquipmentMapper;
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
 * Service for executing complex queries for {@link Equipment} entities in the database.
 * The main input is a {@link EquipmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EquipmentDTO} or a {@link Page} of {@link EquipmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EquipmentQueryService extends QueryService<Equipment> {

    private final Logger log = LoggerFactory.getLogger(EquipmentQueryService.class);

    private final EquipmentRepository equipmentRepository;

    private final EquipmentMapper equipmentMapper;

    public EquipmentQueryService(EquipmentRepository equipmentRepository, EquipmentMapper equipmentMapper) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentMapper = equipmentMapper;
    }

    /**
     * Return a {@link List} of {@link EquipmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EquipmentDTO> findByCriteria(EquipmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Equipment> specification = createSpecification(criteria);
        return equipmentMapper.toDto(equipmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EquipmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EquipmentDTO> findByCriteria(EquipmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Equipment> specification = createSpecification(criteria);
        return equipmentRepository.findAll(specification, page).map(equipmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EquipmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Equipment> specification = createSpecification(criteria);
        return equipmentRepository.count(specification);
    }

    /**
     * Function to convert {@link EquipmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Equipment> createSpecification(EquipmentCriteria criteria) {
        Specification<Equipment> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Equipment_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Equipment_.name));
            }
            if (criteria.getRoomId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRoomId(), root -> root.join(Equipment_.room, JoinType.LEFT).get(Room_.id))
                    );
            }
            if (criteria.getTechnicianEquipmentMappingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTechnicianEquipmentMappingId(),
                            root -> root.join(Equipment_.technicianEquipmentMappings, JoinType.LEFT).get(TechnicianEquipmentMapping_.id)
                        )
                    );
            }
            if (criteria.getTestCategoriesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTestCategoriesId(),
                            root -> root.join(Equipment_.testCategories, JoinType.LEFT).get(TestCategories_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
