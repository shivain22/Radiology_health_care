package com.radiology.health.care.repository;

import com.radiology.health.care.domain.Rank;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RankRepository extends JpaRepository<Rank, Long>, JpaSpecificationExecutor<Rank> {}
