package com.radiology.health.app.domain;

import static com.radiology.health.app.domain.PatientInfoTestSamples.*;
import static com.radiology.health.app.domain.PatientTestInfoTestSamples.*;
import static com.radiology.health.app.domain.TestTimingsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PatientTestInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientTestInfo.class);
        PatientTestInfo patientTestInfo1 = getPatientTestInfoSample1();
        PatientTestInfo patientTestInfo2 = new PatientTestInfo();
        assertThat(patientTestInfo1).isNotEqualTo(patientTestInfo2);

        patientTestInfo2.setId(patientTestInfo1.getId());
        assertThat(patientTestInfo1).isEqualTo(patientTestInfo2);

        patientTestInfo2 = getPatientTestInfoSample2();
        assertThat(patientTestInfo1).isNotEqualTo(patientTestInfo2);
    }

    @Test
    void patientInfoTest() throws Exception {
        PatientTestInfo patientTestInfo = getPatientTestInfoRandomSampleGenerator();
        PatientInfo patientInfoBack = getPatientInfoRandomSampleGenerator();

        patientTestInfo.setPatientInfo(patientInfoBack);
        assertThat(patientTestInfo.getPatientInfo()).isEqualTo(patientInfoBack);

        patientTestInfo.patientInfo(null);
        assertThat(patientTestInfo.getPatientInfo()).isNull();
    }

    @Test
    void testTimingsTest() throws Exception {
        PatientTestInfo patientTestInfo = getPatientTestInfoRandomSampleGenerator();
        TestTimings testTimingsBack = getTestTimingsRandomSampleGenerator();

        patientTestInfo.setTestTimings(testTimingsBack);
        assertThat(patientTestInfo.getTestTimings()).isEqualTo(testTimingsBack);

        patientTestInfo.testTimings(null);
        assertThat(patientTestInfo.getTestTimings()).isNull();
    }
}
