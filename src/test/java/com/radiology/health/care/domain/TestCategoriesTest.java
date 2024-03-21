package com.radiology.health.care.domain;

import static com.radiology.health.care.domain.EquipmentTestSamples.*;
import static com.radiology.health.care.domain.PatientTestTimingsTestSamples.*;
import static com.radiology.health.care.domain.TestCategoriesTestSamples.*;
import static com.radiology.health.care.domain.TestCategoriesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TestCategoriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestCategories.class);
        TestCategories testCategories1 = getTestCategoriesSample1();
        TestCategories testCategories2 = new TestCategories();
        assertThat(testCategories1).isNotEqualTo(testCategories2);

        testCategories2.setId(testCategories1.getId());
        assertThat(testCategories1).isEqualTo(testCategories2);

        testCategories2 = getTestCategoriesSample2();
        assertThat(testCategories1).isNotEqualTo(testCategories2);
    }

    @Test
    void equipmentTest() throws Exception {
        TestCategories testCategories = getTestCategoriesRandomSampleGenerator();
        Equipment equipmentBack = getEquipmentRandomSampleGenerator();

        testCategories.setEquipment(equipmentBack);
        assertThat(testCategories.getEquipment()).isEqualTo(equipmentBack);

        testCategories.equipment(null);
        assertThat(testCategories.getEquipment()).isNull();
    }

    @Test
    void parentTestCategoryTest() throws Exception {
        TestCategories testCategories = getTestCategoriesRandomSampleGenerator();
        TestCategories testCategoriesBack = getTestCategoriesRandomSampleGenerator();

        testCategories.setParentTestCategory(testCategoriesBack);
        assertThat(testCategories.getParentTestCategory()).isEqualTo(testCategoriesBack);

        testCategories.parentTestCategory(null);
        assertThat(testCategories.getParentTestCategory()).isNull();
    }

    @Test
    void patientTestTimingsTest() throws Exception {
        TestCategories testCategories = getTestCategoriesRandomSampleGenerator();
        PatientTestTimings patientTestTimingsBack = getPatientTestTimingsRandomSampleGenerator();

        testCategories.addPatientTestTimings(patientTestTimingsBack);
        assertThat(testCategories.getPatientTestTimings()).containsOnly(patientTestTimingsBack);
        assertThat(patientTestTimingsBack.getTestCategories()).isEqualTo(testCategories);

        testCategories.removePatientTestTimings(patientTestTimingsBack);
        assertThat(testCategories.getPatientTestTimings()).doesNotContain(patientTestTimingsBack);
        assertThat(patientTestTimingsBack.getTestCategories()).isNull();

        testCategories.patientTestTimings(new HashSet<>(Set.of(patientTestTimingsBack)));
        assertThat(testCategories.getPatientTestTimings()).containsOnly(patientTestTimingsBack);
        assertThat(patientTestTimingsBack.getTestCategories()).isEqualTo(testCategories);

        testCategories.setPatientTestTimings(new HashSet<>());
        assertThat(testCategories.getPatientTestTimings()).doesNotContain(patientTestTimingsBack);
        assertThat(patientTestTimingsBack.getTestCategories()).isNull();
    }

    @Test
    void testCategoryParentTest() throws Exception {
        TestCategories testCategories = getTestCategoriesRandomSampleGenerator();
        TestCategories testCategoriesBack = getTestCategoriesRandomSampleGenerator();

        testCategories.addTestCategoryParent(testCategoriesBack);
        assertThat(testCategories.getTestCategoryParents()).containsOnly(testCategoriesBack);
        assertThat(testCategoriesBack.getParentTestCategory()).isEqualTo(testCategories);

        testCategories.removeTestCategoryParent(testCategoriesBack);
        assertThat(testCategories.getTestCategoryParents()).doesNotContain(testCategoriesBack);
        assertThat(testCategoriesBack.getParentTestCategory()).isNull();

        testCategories.testCategoryParents(new HashSet<>(Set.of(testCategoriesBack)));
        assertThat(testCategories.getTestCategoryParents()).containsOnly(testCategoriesBack);
        assertThat(testCategoriesBack.getParentTestCategory()).isEqualTo(testCategories);

        testCategories.setTestCategoryParents(new HashSet<>());
        assertThat(testCategories.getTestCategoryParents()).doesNotContain(testCategoriesBack);
        assertThat(testCategoriesBack.getParentTestCategory()).isNull();
    }
}
