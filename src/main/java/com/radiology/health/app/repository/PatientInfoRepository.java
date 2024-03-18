package com.radiology.health.app.repository;

import com.radiology.health.app.domain.PatientInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PatientInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientInfoRepository extends JpaRepository<PatientInfo, Long>, JpaSpecificationExecutor<PatientInfo> {}
