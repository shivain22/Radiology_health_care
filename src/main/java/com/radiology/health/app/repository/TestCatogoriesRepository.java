package com.radiology.health.app.repository;

import com.radiology.health.app.domain.TestCatogories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TestCatogories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestCatogoriesRepository extends JpaRepository<TestCatogories, Long>, JpaSpecificationExecutor<TestCatogories> {}
