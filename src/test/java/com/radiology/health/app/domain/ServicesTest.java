package com.radiology.health.app.domain;

import static com.radiology.health.app.domain.EmployeeTestSamples.*;
import static com.radiology.health.app.domain.RankTestSamples.*;
import static com.radiology.health.app.domain.ServicesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServicesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Services.class);
        Services services1 = getServicesSample1();
        Services services2 = new Services();
        assertThat(services1).isNotEqualTo(services2);

        services2.setId(services1.getId());
        assertThat(services1).isEqualTo(services2);

        services2 = getServicesSample2();
        assertThat(services1).isNotEqualTo(services2);
    }

    @Test
    void employeeTest() throws Exception {
        Services services = getServicesRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        services.addEmployee(employeeBack);
        assertThat(services.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getServices()).isEqualTo(services);

        services.removeEmployee(employeeBack);
        assertThat(services.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getServices()).isNull();

        services.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(services.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getServices()).isEqualTo(services);

        services.setEmployees(new HashSet<>());
        assertThat(services.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getServices()).isNull();
    }

    @Test
    void rankTest() throws Exception {
        Services services = getServicesRandomSampleGenerator();
        Rank rankBack = getRankRandomSampleGenerator();

        services.addRank(rankBack);
        assertThat(services.getRanks()).containsOnly(rankBack);
        assertThat(rankBack.getServices()).isEqualTo(services);

        services.removeRank(rankBack);
        assertThat(services.getRanks()).doesNotContain(rankBack);
        assertThat(rankBack.getServices()).isNull();

        services.ranks(new HashSet<>(Set.of(rankBack)));
        assertThat(services.getRanks()).containsOnly(rankBack);
        assertThat(rankBack.getServices()).isEqualTo(services);

        services.setRanks(new HashSet<>());
        assertThat(services.getRanks()).doesNotContain(rankBack);
        assertThat(rankBack.getServices()).isNull();
    }
}
