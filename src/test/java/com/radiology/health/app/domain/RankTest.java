package com.radiology.health.app.domain;

import static com.radiology.health.app.domain.EmployeeTestSamples.*;
import static com.radiology.health.app.domain.RankTestSamples.*;
import static com.radiology.health.app.domain.ServicesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
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
    void servicesTest() throws Exception {
        Rank rank = getRankRandomSampleGenerator();
        Services servicesBack = getServicesRandomSampleGenerator();

        rank.setServices(servicesBack);
        assertThat(rank.getServices()).isEqualTo(servicesBack);

        rank.services(null);
        assertThat(rank.getServices()).isNull();
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
