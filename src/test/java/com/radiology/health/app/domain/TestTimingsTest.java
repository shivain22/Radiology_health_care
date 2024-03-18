package com.radiology.health.app.domain;

import static com.radiology.health.app.domain.PatientTestInfoTestSamples.*;
import static com.radiology.health.app.domain.TestCatogoriesTestSamples.*;
import static com.radiology.health.app.domain.TestTimingsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TestTimingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestTimings.class);
        TestTimings testTimings1 = getTestTimingsSample1();
        TestTimings testTimings2 = new TestTimings();
        assertThat(testTimings1).isNotEqualTo(testTimings2);

        testTimings2.setId(testTimings1.getId());
        assertThat(testTimings1).isEqualTo(testTimings2);

        testTimings2 = getTestTimingsSample2();
        assertThat(testTimings1).isNotEqualTo(testTimings2);
    }

    @Test
    void testCatogoriesTest() throws Exception {
        TestTimings testTimings = getTestTimingsRandomSampleGenerator();
        TestCatogories testCatogoriesBack = getTestCatogoriesRandomSampleGenerator();

        testTimings.setTestCatogories(testCatogoriesBack);
        assertThat(testTimings.getTestCatogories()).isEqualTo(testCatogoriesBack);

        testTimings.testCatogories(null);
        assertThat(testTimings.getTestCatogories()).isNull();
    }

    @Test
    void patientTestInfoTest() throws Exception {
        TestTimings testTimings = getTestTimingsRandomSampleGenerator();
        PatientTestInfo patientTestInfoBack = getPatientTestInfoRandomSampleGenerator();

        testTimings.addPatientTestInfo(patientTestInfoBack);
        assertThat(testTimings.getPatientTestInfos()).containsOnly(patientTestInfoBack);
        assertThat(patientTestInfoBack.getTestTimings()).isEqualTo(testTimings);

        testTimings.removePatientTestInfo(patientTestInfoBack);
        assertThat(testTimings.getPatientTestInfos()).doesNotContain(patientTestInfoBack);
        assertThat(patientTestInfoBack.getTestTimings()).isNull();

        testTimings.patientTestInfos(new HashSet<>(Set.of(patientTestInfoBack)));
        assertThat(testTimings.getPatientTestInfos()).containsOnly(patientTestInfoBack);
        assertThat(patientTestInfoBack.getTestTimings()).isEqualTo(testTimings);

        testTimings.setPatientTestInfos(new HashSet<>());
        assertThat(testTimings.getPatientTestInfos()).doesNotContain(patientTestInfoBack);
        assertThat(patientTestInfoBack.getTestTimings()).isNull();
    }
}
