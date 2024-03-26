package com.radiology.health.care.repository;

import com.radiology.health.care.domain.Equipment;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Equipment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long>, JpaSpecificationExecutor<Equipment> {
    @Query("select equipment from Equipment equipment where equipment.user.login = ?#{authentication.name}")
    List<Equipment> findByUserIsCurrentUser();
}
