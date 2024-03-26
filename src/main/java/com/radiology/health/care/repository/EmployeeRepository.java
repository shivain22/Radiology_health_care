package com.radiology.health.care.repository;

import com.radiology.health.care.domain.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    @Query("select employee from Employee employee where employee.user.login = ?#{authentication.name}")
    List<Employee> findByUserIsCurrentUser();
}
