package com.radiology.health.care.repository;

import com.radiology.health.care.domain.PatientTestTimings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PatientTestTimings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientTestTimingsRepository
    extends JpaRepository<PatientTestTimings, Long>, JpaSpecificationExecutor<PatientTestTimings> {}
