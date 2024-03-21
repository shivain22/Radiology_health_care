package com.radiology.health.care.domain;

import static com.radiology.health.care.domain.PatientInfoTestSamples.*;
import static com.radiology.health.care.domain.PatientTestTimingsTestSamples.*;
import static com.radiology.health.care.domain.TestCategoriesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PatientTestTimingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientTestTimings.class);
        PatientTestTimings patientTestTimings1 = getPatientTestTimingsSample1();
        PatientTestTimings patientTestTimings2 = new PatientTestTimings();
        assertThat(patientTestTimings1).isNotEqualTo(patientTestTimings2);

        patientTestTimings2.setId(patientTestTimings1.getId());
        assertThat(patientTestTimings1).isEqualTo(patientTestTimings2);

        patientTestTimings2 = getPatientTestTimingsSample2();
        assertThat(patientTestTimings1).isNotEqualTo(patientTestTimings2);
    }

    @Test
    void patientInfoTest() throws Exception {
        PatientTestTimings patientTestTimings = getPatientTestTimingsRandomSampleGenerator();
        PatientInfo patientInfoBack = getPatientInfoRandomSampleGenerator();

        patientTestTimings.setPatientInfo(patientInfoBack);
        assertThat(patientTestTimings.getPatientInfo()).isEqualTo(patientInfoBack);

        patientTestTimings.patientInfo(null);
        assertThat(patientTestTimings.getPatientInfo()).isNull();
    }

    @Test
    void testCategoriesTest() throws Exception {
        PatientTestTimings patientTestTimings = getPatientTestTimingsRandomSampleGenerator();
        TestCategories testCategoriesBack = getTestCategoriesRandomSampleGenerator();

        patientTestTimings.setTestCategories(testCategoriesBack);
        assertThat(patientTestTimings.getTestCategories()).isEqualTo(testCategoriesBack);

        patientTestTimings.testCategories(null);
        assertThat(patientTestTimings.getTestCategories()).isNull();
    }
}
