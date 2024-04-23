package com.radiology.health.care.repository;

import com.radiology.health.care.domain.OfficeTimings;
import com.radiology.health.care.service.dto.DefaultOfficeTimingsDTO;
import com.radiology.health.care.service.dto.OfficeTimingsDTO;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OfficeTimings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficeTimingsRepository extends JpaRepository<OfficeTimings, Long>, JpaSpecificationExecutor<OfficeTimings> {
    @Query("select officeTimings from OfficeTimings officeTimings where officeTimings.user.login = ?#{authentication.name}")
    List<OfficeTimings> findByUserIsCurrentUser();

    void deleteByDateIsNull();

    List<OfficeTimings> findByDate(LocalDate date);
}
