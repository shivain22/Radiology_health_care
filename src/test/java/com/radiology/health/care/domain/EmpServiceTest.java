package com.radiology.health.care.domain;

import static com.radiology.health.care.domain.EmpServiceTestSamples.*;
import static com.radiology.health.care.domain.EmployeeTestSamples.*;
import static com.radiology.health.care.domain.RankTestSamples.*;
import static com.radiology.health.care.domain.UnitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmpServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpService.class);
        EmpService empService1 = getEmpServiceSample1();
        EmpService empService2 = new EmpService();
        assertThat(empService1).isNotEqualTo(empService2);

        empService2.setId(empService1.getId());
        assertThat(empService1).isEqualTo(empService2);

        empService2 = getEmpServiceSample2();
        assertThat(empService1).isNotEqualTo(empService2);
    }

    @Test
    void rankTest() throws Exception {
        EmpService empService = getEmpServiceRandomSampleGenerator();
        Rank rankBack = getRankRandomSampleGenerator();

        empService.addRank(rankBack);
        assertThat(empService.getRanks()).containsOnly(rankBack);
        assertThat(rankBack.getEmpService()).isEqualTo(empService);

        empService.removeRank(rankBack);
        assertThat(empService.getRanks()).doesNotContain(rankBack);
        assertThat(rankBack.getEmpService()).isNull();

        empService.ranks(new HashSet<>(Set.of(rankBack)));
        assertThat(empService.getRanks()).containsOnly(rankBack);
        assertThat(rankBack.getEmpService()).isEqualTo(empService);

        empService.setRanks(new HashSet<>());
        assertThat(empService.getRanks()).doesNotContain(rankBack);
        assertThat(rankBack.getEmpService()).isNull();
    }

    @Test
    void unitTest() throws Exception {
        EmpService empService = getEmpServiceRandomSampleGenerator();
        Unit unitBack = getUnitRandomSampleGenerator();

        empService.addUnit(unitBack);
        assertThat(empService.getUnits()).containsOnly(unitBack);
        assertThat(unitBack.getEmpService()).isEqualTo(empService);

        empService.removeUnit(unitBack);
        assertThat(empService.getUnits()).doesNotContain(unitBack);
        assertThat(unitBack.getEmpService()).isNull();

        empService.units(new HashSet<>(Set.of(unitBack)));
        assertThat(empService.getUnits()).containsOnly(unitBack);
        assertThat(unitBack.getEmpService()).isEqualTo(empService);

        empService.setUnits(new HashSet<>());
        assertThat(empService.getUnits()).doesNotContain(unitBack);
        assertThat(unitBack.getEmpService()).isNull();
    }

    @Test
    void employeeTest() throws Exception {
        EmpService empService = getEmpServiceRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        empService.addEmployee(employeeBack);
        assertThat(empService.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getEmpService()).isEqualTo(empService);

        empService.removeEmployee(employeeBack);
        assertThat(empService.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getEmpService()).isNull();

        empService.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(empService.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getEmpService()).isEqualTo(empService);

        empService.setEmployees(new HashSet<>());
        assertThat(empService.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getEmpService()).isNull();
    }
}
