package com.radiology.health.app.repository;

import com.radiology.health.app.domain.TechicianEquipmentMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TechicianEquipmentMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechicianEquipmentMappingRepository
    extends JpaRepository<TechicianEquipmentMapping, Long>, JpaSpecificationExecutor<TechicianEquipmentMapping> {}
