package com.radiology.health.app.repository;

import com.radiology.health.app.domain.Services;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Services entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServicesRepository extends JpaRepository<Services, Long>, JpaSpecificationExecutor<Services> {}
