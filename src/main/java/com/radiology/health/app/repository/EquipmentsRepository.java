package com.radiology.health.app.repository;

import com.radiology.health.app.domain.Equipments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Equipments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipmentsRepository extends JpaRepository<Equipments, Long>, JpaSpecificationExecutor<Equipments> {}
