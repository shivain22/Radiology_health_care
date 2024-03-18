package com.radiology.health.app.domain;

import static com.radiology.health.app.domain.EmployeeTestSamples.*;
import static com.radiology.health.app.domain.UnitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Unit.class);
        Unit unit1 = getUnitSample1();
        Unit unit2 = new Unit();
        assertThat(unit1).isNotEqualTo(unit2);

        unit2.setId(unit1.getId());
        assertThat(unit1).isEqualTo(unit2);

        unit2 = getUnitSample2();
        assertThat(unit1).isNotEqualTo(unit2);
    }

    @Test
    void employeeTest() throws Exception {
        Unit unit = getUnitRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        unit.addEmployee(employeeBack);
        assertThat(unit.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getUnit()).isEqualTo(unit);

        unit.removeEmployee(employeeBack);
        assertThat(unit.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getUnit()).isNull();

        unit.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(unit.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getUnit()).isEqualTo(unit);

        unit.setEmployees(new HashSet<>());
        assertThat(unit.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getUnit()).isNull();
    }
}
