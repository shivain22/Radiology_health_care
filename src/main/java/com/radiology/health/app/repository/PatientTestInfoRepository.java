package com.radiology.health.app.repository;

import com.radiology.health.app.domain.PatientTestInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PatientTestInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientTestInfoRepository extends JpaRepository<PatientTestInfo, Long>, JpaSpecificationExecutor<PatientTestInfo> {}
