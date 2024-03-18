package com.radiology.health.app.domain;

import static com.radiology.health.app.domain.EmployeeTestSamples.*;
import static com.radiology.health.app.domain.PatientInfoTestSamples.*;
import static com.radiology.health.app.domain.PatientTestInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
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
    void employeeTest() throws Exception {
        PatientInfo patientInfo = getPatientInfoRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        patientInfo.setEmployee(employeeBack);
        assertThat(patientInfo.getEmployee()).isEqualTo(employeeBack);

        patientInfo.employee(null);
        assertThat(patientInfo.getEmployee()).isNull();
    }

    @Test
    void patientTestInfoTest() throws Exception {
        PatientInfo patientInfo = getPatientInfoRandomSampleGenerator();
        PatientTestInfo patientTestInfoBack = getPatientTestInfoRandomSampleGenerator();

        patientInfo.addPatientTestInfo(patientTestInfoBack);
        assertThat(patientInfo.getPatientTestInfos()).containsOnly(patientTestInfoBack);
        assertThat(patientTestInfoBack.getPatientInfo()).isEqualTo(patientInfo);

        patientInfo.removePatientTestInfo(patientTestInfoBack);
        assertThat(patientInfo.getPatientTestInfos()).doesNotContain(patientTestInfoBack);
        assertThat(patientTestInfoBack.getPatientInfo()).isNull();

        patientInfo.patientTestInfos(new HashSet<>(Set.of(patientTestInfoBack)));
        assertThat(patientInfo.getPatientTestInfos()).containsOnly(patientTestInfoBack);
        assertThat(patientTestInfoBack.getPatientInfo()).isEqualTo(patientInfo);

        patientInfo.setPatientTestInfos(new HashSet<>());
        assertThat(patientInfo.getPatientTestInfos()).doesNotContain(patientTestInfoBack);
        assertThat(patientTestInfoBack.getPatientInfo()).isNull();
    }
}
