package com.radiology.health.app.service;

import com.radiology.health.app.domain.*; // for static metamodels
import com.radiology.health.app.domain.Rooms;
import com.radiology.health.app.repository.RoomsRepository;
import com.radiology.health.app.service.criteria.RoomsCriteria;
import com.radiology.health.app.service.dto.RoomsDTO;
import com.radiology.health.app.service.mapper.RoomsMapper;
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
 * Service for executing complex queries for {@link Rooms} entities in the database.
 * The main input is a {@link RoomsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RoomsDTO} or a {@link Page} of {@link RoomsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RoomsQueryService extends QueryService<Rooms> {

    private final Logger log = LoggerFactory.getLogger(RoomsQueryService.class);

    private final RoomsRepository roomsRepository;

    private final RoomsMapper roomsMapper;

    public RoomsQueryService(RoomsRepository roomsRepository, RoomsMapper roomsMapper) {
        this.roomsRepository = roomsRepository;
        this.roomsMapper = roomsMapper;
    }

    /**
     * Return a {@link List} of {@link RoomsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RoomsDTO> findByCriteria(RoomsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Rooms> specification = createSpecification(criteria);
        return roomsMapper.toDto(roomsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RoomsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RoomsDTO> findByCriteria(RoomsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Rooms> specification = createSpecification(criteria);
        return roomsRepository.findAll(specification, page).map(roomsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RoomsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Rooms> specification = createSpecification(criteria);
        return roomsRepository.count(specification);
    }

    /**
     * Function to convert {@link RoomsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Rooms> createSpecification(RoomsCriteria criteria) {
        Specification<Rooms> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Rooms_.id));
            }
            if (criteria.getRoomNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRoomNo(), Rooms_.roomNo));
            }
            if (criteria.getEquipmentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEquipmentsId(),
                            root -> root.join(Rooms_.equipments, JoinType.LEFT).get(Equipments_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
