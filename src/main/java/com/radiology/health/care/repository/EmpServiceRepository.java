package com.radiology.health.care.repository;

import com.radiology.health.care.domain.EmpService;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmpService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpServiceRepository extends JpaRepository<EmpService, Long>, JpaSpecificationExecutor<EmpService> {
    @Query("select empService from EmpService empService where empService.user.login = ?#{authentication.name}")
    List<EmpService> findByUserIsCurrentUser();
}
