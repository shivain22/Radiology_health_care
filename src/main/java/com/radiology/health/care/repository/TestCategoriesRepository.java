package com.radiology.health.care.repository;

import com.radiology.health.care.domain.TestCategories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TestCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestCategoriesRepository extends JpaRepository<TestCategories, Long>, JpaSpecificationExecutor<TestCategories> {}
