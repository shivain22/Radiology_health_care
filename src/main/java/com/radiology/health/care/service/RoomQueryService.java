package com.radiology.health.care.service;

import com.radiology.health.care.domain.*; // for static metamodels
import com.radiology.health.care.domain.Room;
import com.radiology.health.care.repository.RoomRepository;
import com.radiology.health.care.service.criteria.RoomCriteria;
import com.radiology.health.care.service.dto.RoomDTO;
import com.radiology.health.care.service.mapper.RoomMapper;
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
 * Service for executing complex queries for {@link Room} entities in the database.
 * The main input is a {@link RoomCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RoomDTO} or a {@link Page} of {@link RoomDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RoomQueryService extends QueryService<Room> {

    private final Logger log = LoggerFactory.getLogger(RoomQueryService.class);

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    public RoomQueryService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    /**
     * Return a {@link List} of {@link RoomDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RoomDTO> findByCriteria(RoomCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Room> specification = createSpecification(criteria);
        return roomMapper.toDto(roomRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RoomDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RoomDTO> findByCriteria(RoomCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Room> specification = createSpecification(criteria);
        return roomRepository.findAll(specification, page).map(roomMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RoomCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Room> specification = createSpecification(criteria);
        return roomRepository.count(specification);
    }

    /**
     * Function to convert {@link RoomCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Room> createSpecification(RoomCriteria criteria) {
        Specification<Room> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Room_.id));
            }
            if (criteria.getRoomNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRoomNo(), Room_.roomNo));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getUserId(), root -> root.join(Room_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getEquipmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEquipmentId(), root -> root.join(Room_.equipment, JoinType.LEFT).get(Equipment_.id))
                    );
            }
        }
        return specification;
    }
}
