package com.radiology.health.care.repository;

import com.radiology.health.care.domain.Unit;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Unit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {
    @Query("select unit from Unit unit where unit.user.login = ?#{authentication.name}")
    List<Unit> findByUserIsCurrentUser();
}
