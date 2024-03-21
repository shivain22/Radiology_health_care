package com.radiology.health.care.repository;

import com.radiology.health.care.domain.TechnicianEquipmentMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TechnicianEquipmentMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechnicianEquipmentMappingRepository
    extends JpaRepository<TechnicianEquipmentMapping, Long>, JpaSpecificationExecutor<TechnicianEquipmentMapping> {}
