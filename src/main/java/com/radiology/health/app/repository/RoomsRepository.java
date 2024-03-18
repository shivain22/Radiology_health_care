package com.radiology.health.app.repository;

import com.radiology.health.app.domain.Rooms;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rooms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Long>, JpaSpecificationExecutor<Rooms> {}
