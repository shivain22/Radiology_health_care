package com.radiology.health.care.repository;

import com.radiology.health.care.domain.EmpService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmpService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpServiceRepository extends JpaRepository<EmpService, Long>, JpaSpecificationExecutor<EmpService> {}
