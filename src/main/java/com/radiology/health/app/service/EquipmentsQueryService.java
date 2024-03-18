package com.radiology.health.app.service;

import com.radiology.health.app.domain.*; // for static metamodels
import com.radiology.health.app.domain.Equipments;
import com.radiology.health.app.repository.EquipmentsRepository;
import com.radiology.health.app.service.criteria.EquipmentsCriteria;
import com.radiology.health.app.service.dto.EquipmentsDTO;
import com.radiology.health.app.service.mapper.EquipmentsMapper;
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
 * Service for executing complex queries for {@link Equipments} entities in the database.
 * The main input is a {@link EquipmentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EquipmentsDTO} or a {@link Page} of {@link EquipmentsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EquipmentsQueryService extends QueryService<Equipments> {

    private final Logger log = LoggerFactory.getLogger(EquipmentsQueryService.class);

    private final EquipmentsRepository equipmentsRepository;

    private final EquipmentsMapper equipmentsMapper;

    public EquipmentsQueryService(EquipmentsRepository equipmentsRepository, EquipmentsMapper equipmentsMapper) {
        this.equipmentsRepository = equipmentsRepository;
        this.equipmentsMapper = equipmentsMapper;
    }

    /**
     * Return a {@link List} of {@link EquipmentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EquipmentsDTO> findByCriteria(EquipmentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Equipments> specification = createSpecification(criteria);
        return equipmentsMapper.toDto(equipmentsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EquipmentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EquipmentsDTO> findByCriteria(EquipmentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Equipments> specification = createSpecification(criteria);
        return equipmentsRepository.findAll(specification, page).map(equipmentsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EquipmentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Equipments> specification = createSpecification(criteria);
        return equipmentsRepository.count(specification);
    }

    /**
     * Function to convert {@link EquipmentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Equipments> createSpecification(EquipmentsCriteria criteria) {
        Specification<Equipments> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Equipments_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Equipments_.name));
            }
            if (criteria.getRoomsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRoomsId(), root -> root.join(Equipments_.rooms, JoinType.LEFT).get(Rooms_.id))
                    );
            }
            if (criteria.getTestCatogoriesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTestCatogoriesId(),
                            root -> root.join(Equipments_.testCatogories, JoinType.LEFT).get(TestCatogories_.id)
                        )
                    );
            }
            if (criteria.getTechicianEquipmentMappingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTechicianEquipmentMappingId(),
                            root -> root.join(Equipments_.techicianEquipmentMappings, JoinType.LEFT).get(TechicianEquipmentMapping_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
