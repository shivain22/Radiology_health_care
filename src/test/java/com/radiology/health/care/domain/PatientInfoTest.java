package com.radiology.health.care.domain;

import static com.radiology.health.care.domain.EmployeeTestSamples.*;
import static com.radiology.health.care.domain.PatientInfoTestSamples.*;
import static com.radiology.health.care.domain.PatientTestTimingsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PatientInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientInfo.class);
        PatientInfo patientInfo1 = getPatientInfoSample1();
        PatientInfo patientInfo2 = new PatientInfo();
        assertThat(patientInfo1).isNotEqualTo(patientInfo2);

        patientInfo2.setId(patientInfo1.getId());
        assertThat(patientInfo1).isEqualTo(patientInfo2);

        patientInfo2 = getPatientInfoSample2();
        assertThat(patientInfo1).isNotEqualTo(patientInfo2);
    }

    @Test
    void employeeIdTest() throws Exception {
        PatientInfo patientInfo = getPatientInfoRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        patientInfo.setEmployeeId(employeeBack);
        assertThat(patientInfo.getEmployeeId()).isEqualTo(employeeBack);

        patientInfo.employeeId(null);
        assertThat(patientInfo.getEmployeeId()).isNull();
    }

    @Test
    void employeeHisTest() throws Exception {
        PatientInfo patientInfo = getPatientInfoRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        patientInfo.setEmployeeHis(employeeBack);
        assertThat(patientInfo.getEmployeeHis()).isEqualTo(employeeBack);

        patientInfo.employeeHis(null);
        assertThat(patientInfo.getEmployeeHis()).isNull();
    }

    @Test
    void employeeServiceNoTest() throws Exception {
        PatientInfo patientInfo = getPatientInfoRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        patientInfo.setEmployeeServiceNo(employeeBack);
        assertThat(patientInfo.getEmployeeServiceNo()).isEqualTo(employeeBack);

        patientInfo.employeeServiceNo(null);
        assertThat(patientInfo.getEmployeeServiceNo()).isNull();
    }

    @Test
    void patientTestTimingsTest() throws Exception {
        PatientInfo patientInfo = getPatientInfoRandomSampleGenerator();
        PatientTestTimings patientTestTimingsBack = getPatientTestTimingsRandomSampleGenerator();

        patientInfo.addPatientTestTimings(patientTestTimingsBack);
        assertThat(patientInfo.getPatientTestTimings()).containsOnly(patientTestTimingsBack);
        assertThat(patientTestTimingsBack.getPatientInfo()).isEqualTo(patientInfo);

        patientInfo.removePatientTestTimings(patientTestTimingsBack);
        assertThat(patientInfo.getPatientTestTimings()).doesNotContain(patientTestTimingsBack);
        assertThat(patientTestTimingsBack.getPatientInfo()).isNull();

        patientInfo.patientTestTimings(new HashSet<>(Set.of(patientTestTimingsBack)));
        assertThat(patientInfo.getPatientTestTimings()).containsOnly(patientTestTimingsBack);
        assertThat(patientTestTimingsBack.getPatientInfo()).isEqualTo(patientInfo);

        patientInfo.setPatientTestTimings(new HashSet<>());
        assertThat(patientInfo.getPatientTestTimings()).doesNotContain(patientTestTimingsBack);
        assertThat(patientTestTimingsBack.getPatientInfo()).isNull();
    }
}
