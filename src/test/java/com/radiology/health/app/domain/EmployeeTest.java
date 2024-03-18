package com.radiology.health.app.domain;

import static com.radiology.health.app.domain.EmployeeTestSamples.*;
import static com.radiology.health.app.domain.PatientInfoTestSamples.*;
import static com.radiology.health.app.domain.RankTestSamples.*;
import static com.radiology.health.app.domain.ServicesTestSamples.*;
import static com.radiology.health.app.domain.TechicianEquipmentMappingTestSamples.*;
import static com.radiology.health.app.domain.UnitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = getEmployeeSample1();
        Employee employee2 = new Employee();
        assertThat(employee1).isNotEqualTo(employee2);

        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);

        employee2 = getEmployeeSample2();
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    void unitTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Unit unitBack = getUnitRandomSampleGenerator();

        employee.setUnit(unitBack);
        assertThat(employee.getUnit()).isEqualTo(unitBack);

        employee.unit(null);
        assertThat(employee.getUnit()).isNull();
    }

    @Test
    void servicesTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Services servicesBack = getServicesRandomSampleGenerator();

        employee.setServices(servicesBack);
        assertThat(employee.getServices()).isEqualTo(servicesBack);

        employee.services(null);
        assertThat(employee.getServices()).isNull();
    }

    @Test
    void rankTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Rank rankBack = getRankRandomSampleGenerator();

        employee.setRank(rankBack);
        assertThat(employee.getRank()).isEqualTo(rankBack);

        employee.rank(null);
        assertThat(employee.getRank()).isNull();
    }

    @Test
    void patientInfoTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        PatientInfo patientInfoBack = getPatientInfoRandomSampleGenerator();

        employee.addPatientInfo(patientInfoBack);
        assertThat(employee.getPatientInfos()).containsOnly(patientInfoBack);
        assertThat(patientInfoBack.getEmployee()).isEqualTo(employee);

        employee.removePatientInfo(patientInfoBack);
        assertThat(employee.getPatientInfos()).doesNotContain(patientInfoBack);
        assertThat(patientInfoBack.getEmployee()).isNull();

        employee.patientInfos(new HashSet<>(Set.of(patientInfoBack)));
        assertThat(employee.getPatientInfos()).containsOnly(patientInfoBack);
        assertThat(patientInfoBack.getEmployee()).isEqualTo(employee);

        employee.setPatientInfos(new HashSet<>());
        assertThat(employee.getPatientInfos()).doesNotContain(patientInfoBack);
        assertThat(patientInfoBack.getEmployee()).isNull();
    }

    @Test
    void techicianEquipmentMappingTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        TechicianEquipmentMapping techicianEquipmentMappingBack = getTechicianEquipmentMappingRandomSampleGenerator();

        employee.addTechicianEquipmentMapping(techicianEquipmentMappingBack);
        assertThat(employee.getTechicianEquipmentMappings()).containsOnly(techicianEquipmentMappingBack);
        assertThat(techicianEquipmentMappingBack.getEmployee()).isEqualTo(employee);

        employee.removeTechicianEquipmentMapping(techicianEquipmentMappingBack);
        assertThat(employee.getTechicianEquipmentMappings()).doesNotContain(techicianEquipmentMappingBack);
        assertThat(techicianEquipmentMappingBack.getEmployee()).isNull();

        employee.techicianEquipmentMappings(new HashSet<>(Set.of(techicianEquipmentMappingBack)));
        assertThat(employee.getTechicianEquipmentMappings()).containsOnly(techicianEquipmentMappingBack);
        assertThat(techicianEquipmentMappingBack.getEmployee()).isEqualTo(employee);

        employee.setTechicianEquipmentMappings(new HashSet<>());
        assertThat(employee.getTechicianEquipmentMappings()).doesNotContain(techicianEquipmentMappingBack);
        assertThat(techicianEquipmentMappingBack.getEmployee()).isNull();
    }
}
