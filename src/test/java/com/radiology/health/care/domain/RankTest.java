package com.radiology.health.care.domain;

import static com.radiology.health.care.domain.EmpServiceTestSamples.*;
import static com.radiology.health.care.domain.EmployeeTestSamples.*;
import static com.radiology.health.care.domain.RankTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RankTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rank.class);
        Rank rank1 = getRankSample1();
        Rank rank2 = new Rank();
        assertThat(rank1).isNotEqualTo(rank2);

        rank2.setId(rank1.getId());
        assertThat(rank1).isEqualTo(rank2);

        rank2 = getRankSample2();
        assertThat(rank1).isNotEqualTo(rank2);
    }

    @Test
    void empServiceTest() throws Exception {
        Rank rank = getRankRandomSampleGenerator();
        EmpService empServiceBack = getEmpServiceRandomSampleGenerator();

        rank.setEmpService(empServiceBack);
        assertThat(rank.getEmpService()).isEqualTo(empServiceBack);

        rank.empService(null);
        assertThat(rank.getEmpService()).isNull();
    }

    @Test
    void employeeTest() throws Exception {
        Rank rank = getRankRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        rank.addEmployee(employeeBack);
        assertThat(rank.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getRank()).isEqualTo(rank);

        rank.removeEmployee(employeeBack);
        assertThat(rank.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getRank()).isNull();

        rank.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(rank.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getRank()).isEqualTo(rank);

        rank.setEmployees(new HashSet<>());
        assertThat(rank.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getRank()).isNull();
    }
}
