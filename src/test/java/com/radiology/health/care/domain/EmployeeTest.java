package com.radiology.health.care.domain;

import static com.radiology.health.care.domain.EmpServiceTestSamples.*;
import static com.radiology.health.care.domain.EmployeeTestSamples.*;
import static com.radiology.health.care.domain.PatientInfoTestSamples.*;
import static com.radiology.health.care.domain.RankTestSamples.*;
import static com.radiology.health.care.domain.TechnicianEquipmentMappingTestSamples.*;
import static com.radiology.health.care.domain.UnitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
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
    void rankTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Rank rankBack = getRankRandomSampleGenerator();

        employee.setRank(rankBack);
        assertThat(employee.getRank()).isEqualTo(rankBack);

        employee.rank(null);
        assertThat(employee.getRank()).isNull();
    }

    @Test
    void empServiceTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        EmpService empServiceBack = getEmpServiceRandomSampleGenerator();

        employee.setEmpService(empServiceBack);
        assertThat(employee.getEmpService()).isEqualTo(empServiceBack);

        employee.empService(null);
        assertThat(employee.getEmpService()).isNull();
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
    void technicianEquipmentMappingTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        TechnicianEquipmentMapping technicianEquipmentMappingBack = getTechnicianEquipmentMappingRandomSampleGenerator();

        employee.addTechnicianEquipmentMapping(technicianEquipmentMappingBack);
        assertThat(employee.getTechnicianEquipmentMappings()).containsOnly(technicianEquipmentMappingBack);
        assertThat(technicianEquipmentMappingBack.getEmployee()).isEqualTo(employee);

        employee.removeTechnicianEquipmentMapping(technicianEquipmentMappingBack);
        assertThat(employee.getTechnicianEquipmentMappings()).doesNotContain(technicianEquipmentMappingBack);
        assertThat(technicianEquipmentMappingBack.getEmployee()).isNull();

        employee.technicianEquipmentMappings(new HashSet<>(Set.of(technicianEquipmentMappingBack)));
        assertThat(employee.getTechnicianEquipmentMappings()).containsOnly(technicianEquipmentMappingBack);
        assertThat(technicianEquipmentMappingBack.getEmployee()).isEqualTo(employee);

        employee.setTechnicianEquipmentMappings(new HashSet<>());
        assertThat(employee.getTechnicianEquipmentMappings()).doesNotContain(technicianEquipmentMappingBack);
        assertThat(technicianEquipmentMappingBack.getEmployee()).isNull();
    }

    @Test
    void patientInfoEmployeeIdTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        PatientInfo patientInfoBack = getPatientInfoRandomSampleGenerator();

        employee.addPatientInfoEmployeeId(patientInfoBack);
        assertThat(employee.getPatientInfoEmployeeIds()).containsOnly(patientInfoBack);
        assertThat(patientInfoBack.getEmployeeId()).isEqualTo(employee);

        employee.removePatientInfoEmployeeId(patientInfoBack);
        assertThat(employee.getPatientInfoEmployeeIds()).doesNotContain(patientInfoBack);
        assertThat(patientInfoBack.getEmployeeId()).isNull();

        employee.patientInfoEmployeeIds(new HashSet<>(Set.of(patientInfoBack)));
        assertThat(employee.getPatientInfoEmployeeIds()).containsOnly(patientInfoBack);
        assertThat(patientInfoBack.getEmployeeId()).isEqualTo(employee);

        employee.setPatientInfoEmployeeIds(new HashSet<>());
        assertThat(employee.getPatientInfoEmployeeIds()).doesNotContain(patientInfoBack);
        assertThat(patientInfoBack.getEmployeeId()).isNull();
    }

    @Test
    void patientInfoEmployeeHisTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        PatientInfo patientInfoBack = getPatientInfoRandomSampleGenerator();

        employee.addPatientInfoEmployeeHis(patientInfoBack);
        assertThat(employee.getPatientInfoEmployeeHis()).containsOnly(patientInfoBack);
        assertThat(patientInfoBack.getEmployeeHis()).isEqualTo(employee);

        employee.removePatientInfoEmployeeHis(patientInfoBack);
        assertThat(employee.getPatientInfoEmployeeHis()).doesNotContain(patientInfoBack);
        assertThat(patientInfoBack.getEmployeeHis()).isNull();

        employee.patientInfoEmployeeHis(new HashSet<>(Set.of(patientInfoBack)));
        assertThat(employee.getPatientInfoEmployeeHis()).containsOnly(patientInfoBack);
        assertThat(patientInfoBack.getEmployeeHis()).isEqualTo(employee);

        employee.setPatientInfoEmployeeHis(new HashSet<>());
        assertThat(employee.getPatientInfoEmployeeHis()).doesNotContain(patientInfoBack);
        assertThat(patientInfoBack.getEmployeeHis()).isNull();
    }

    @Test
    void patientInfoEmployeeServiceNoTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        PatientInfo patientInfoBack = getPatientInfoRandomSampleGenerator();

        employee.addPatientInfoEmployeeServiceNo(patientInfoBack);
        assertThat(employee.getPatientInfoEmployeeServiceNos()).containsOnly(patientInfoBack);
        assertThat(patientInfoBack.getEmployeeServiceNo()).isEqualTo(employee);

        employee.removePatientInfoEmployeeServiceNo(patientInfoBack);
        assertThat(employee.getPatientInfoEmployeeServiceNos()).doesNotContain(patientInfoBack);
        assertThat(patientInfoBack.getEmployeeServiceNo()).isNull();

        employee.patientInfoEmployeeServiceNos(new HashSet<>(Set.of(patientInfoBack)));
        assertThat(employee.getPatientInfoEmployeeServiceNos()).containsOnly(patientInfoBack);
        assertThat(patientInfoBack.getEmployeeServiceNo()).isEqualTo(employee);

        employee.setPatientInfoEmployeeServiceNos(new HashSet<>());
        assertThat(employee.getPatientInfoEmployeeServiceNos()).doesNotContain(patientInfoBack);
        assertThat(patientInfoBack.getEmployeeServiceNo()).isNull();
    }
}
