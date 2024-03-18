package com.radiology.health.app.domain;

import static com.radiology.health.app.domain.EquipmentsTestSamples.*;
import static com.radiology.health.app.domain.TestCatogoriesTestSamples.*;
import static com.radiology.health.app.domain.TestCatogoriesTestSamples.*;
import static com.radiology.health.app.domain.TestTimingsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TestCatogoriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestCatogories.class);
        TestCatogories testCatogories1 = getTestCatogoriesSample1();
        TestCatogories testCatogories2 = new TestCatogories();
        assertThat(testCatogories1).isNotEqualTo(testCatogories2);

        testCatogories2.setId(testCatogories1.getId());
        assertThat(testCatogories1).isEqualTo(testCatogories2);

        testCatogories2 = getTestCatogoriesSample2();
        assertThat(testCatogories1).isNotEqualTo(testCatogories2);
    }

    @Test
    void equipmentsTest() throws Exception {
        TestCatogories testCatogories = getTestCatogoriesRandomSampleGenerator();
        Equipments equipmentsBack = getEquipmentsRandomSampleGenerator();

        testCatogories.setEquipments(equipmentsBack);
        assertThat(testCatogories.getEquipments()).isEqualTo(equipmentsBack);

        testCatogories.equipments(null);
        assertThat(testCatogories.getEquipments()).isNull();
    }

    @Test
    void testCatogories_parentTest() throws Exception {
        TestCatogories testCatogories = getTestCatogoriesRandomSampleGenerator();
        TestCatogories testCatogoriesBack = getTestCatogoriesRandomSampleGenerator();

        testCatogories.setTestCatogories_parent(testCatogoriesBack);
        assertThat(testCatogories.getTestCatogories_parent()).isEqualTo(testCatogoriesBack);

        testCatogories.testCatogories_parent(null);
        assertThat(testCatogories.getTestCatogories_parent()).isNull();
    }

    @Test
    void test_catogories_parent_catogoryTest() throws Exception {
        TestCatogories testCatogories = getTestCatogoriesRandomSampleGenerator();
        TestCatogories testCatogoriesBack = getTestCatogoriesRandomSampleGenerator();

        testCatogories.addTest_catogories_parent_catogory(testCatogoriesBack);
        assertThat(testCatogories.getTest_catogories_parent_catogories()).containsOnly(testCatogoriesBack);
        assertThat(testCatogoriesBack.getTestCatogories_parent()).isEqualTo(testCatogories);

        testCatogories.removeTest_catogories_parent_catogory(testCatogoriesBack);
        assertThat(testCatogories.getTest_catogories_parent_catogories()).doesNotContain(testCatogoriesBack);
        assertThat(testCatogoriesBack.getTestCatogories_parent()).isNull();

        testCatogories.test_catogories_parent_catogories(new HashSet<>(Set.of(testCatogoriesBack)));
        assertThat(testCatogories.getTest_catogories_parent_catogories()).containsOnly(testCatogoriesBack);
        assertThat(testCatogoriesBack.getTestCatogories_parent()).isEqualTo(testCatogories);

        testCatogories.setTest_catogories_parent_catogories(new HashSet<>());
        assertThat(testCatogories.getTest_catogories_parent_catogories()).doesNotContain(testCatogoriesBack);
        assertThat(testCatogoriesBack.getTestCatogories_parent()).isNull();
    }

    @Test
    void testTimingsTest() throws Exception {
        TestCatogories testCatogories = getTestCatogoriesRandomSampleGenerator();
        TestTimings testTimingsBack = getTestTimingsRandomSampleGenerator();

        testCatogories.addTestTimings(testTimingsBack);
        assertThat(testCatogories.getTestTimings()).containsOnly(testTimingsBack);
        assertThat(testTimingsBack.getTestCatogories()).isEqualTo(testCatogories);

        testCatogories.removeTestTimings(testTimingsBack);
        assertThat(testCatogories.getTestTimings()).doesNotContain(testTimingsBack);
        assertThat(testTimingsBack.getTestCatogories()).isNull();

        testCatogories.testTimings(new HashSet<>(Set.of(testTimingsBack)));
        assertThat(testCatogories.getTestTimings()).containsOnly(testTimingsBack);
        assertThat(testTimingsBack.getTestCatogories()).isEqualTo(testCatogories);

        testCatogories.setTestTimings(new HashSet<>());
        assertThat(testCatogories.getTestTimings()).doesNotContain(testTimingsBack);
        assertThat(testTimingsBack.getTestCatogories()).isNull();
    }
}
