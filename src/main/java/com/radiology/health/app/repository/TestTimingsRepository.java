package com.radiology.health.app.repository;

import com.radiology.health.app.domain.TestTimings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TestTimings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestTimingsRepository extends JpaRepository<TestTimings, Long>, JpaSpecificationExecutor<TestTimings> {}
