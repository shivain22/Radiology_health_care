package com.radiology.health.care.repository;

import com.radiology.health.care.domain.PatientInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PatientInfo entity.
 */
@Repository
public interface PatientInfoRepository extends JpaRepository<PatientInfo, Long>, JpaSpecificationExecutor<PatientInfo> {
    default Optional<PatientInfo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PatientInfo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PatientInfo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select patientInfo from PatientInfo patientInfo left join fetch patientInfo.employeeHis left join fetch patientInfo.employeeServiceNo",
        countQuery = "select count(patientInfo) from PatientInfo patientInfo"
    )
    Page<PatientInfo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select patientInfo from PatientInfo patientInfo left join fetch patientInfo.employeeHis left join fetch patientInfo.employeeServiceNo"
    )
    List<PatientInfo> findAllWithToOneRelationships();

    @Query(
        "select patientInfo from PatientInfo patientInfo left join fetch patientInfo.employeeHis left join fetch patientInfo.employeeServiceNo where patientInfo.id =:id"
    )
    Optional<PatientInfo> findOneWithToOneRelationships(@Param("id") Long id);
}
