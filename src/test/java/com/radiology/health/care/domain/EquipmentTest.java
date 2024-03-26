package com.radiology.health.care.domain;

import static com.radiology.health.care.domain.EquipmentTestSamples.*;
import static com.radiology.health.care.domain.RoomTestSamples.*;
import static com.radiology.health.care.domain.TechnicianEquipmentMappingTestSamples.*;
import static com.radiology.health.care.domain.TestCategoriesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EquipmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipment.class);
        Equipment equipment1 = getEquipmentSample1();
        Equipment equipment2 = new Equipment();
        assertThat(equipment1).isNotEqualTo(equipment2);

        equipment2.setId(equipment1.getId());
        assertThat(equipment1).isEqualTo(equipment2);

        equipment2 = getEquipmentSample2();
        assertThat(equipment1).isNotEqualTo(equipment2);
    }

    @Test
    void roomTest() throws Exception {
        Equipment equipment = getEquipmentRandomSampleGenerator();
        Room roomBack = getRoomRandomSampleGenerator();

        equipment.setRoom(roomBack);
        assertThat(equipment.getRoom()).isEqualTo(roomBack);

        equipment.room(null);
        assertThat(equipment.getRoom()).isNull();
    }

    @Test
    void technicianEquipmentMappingTest() throws Exception {
        Equipment equipment = getEquipmentRandomSampleGenerator();
        TechnicianEquipmentMapping technicianEquipmentMappingBack = getTechnicianEquipmentMappingRandomSampleGenerator();

        equipment.addTechnicianEquipmentMapping(technicianEquipmentMappingBack);
        assertThat(equipment.getTechnicianEquipmentMappings()).containsOnly(technicianEquipmentMappingBack);
        assertThat(technicianEquipmentMappingBack.getEquipment()).isEqualTo(equipment);

        equipment.removeTechnicianEquipmentMapping(technicianEquipmentMappingBack);
        assertThat(equipment.getTechnicianEquipmentMappings()).doesNotContain(technicianEquipmentMappingBack);
        assertThat(technicianEquipmentMappingBack.getEquipment()).isNull();

        equipment.technicianEquipmentMappings(new HashSet<>(Set.of(technicianEquipmentMappingBack)));
        assertThat(equipment.getTechnicianEquipmentMappings()).containsOnly(technicianEquipmentMappingBack);
        assertThat(technicianEquipmentMappingBack.getEquipment()).isEqualTo(equipment);

        equipment.setTechnicianEquipmentMappings(new HashSet<>());
        assertThat(equipment.getTechnicianEquipmentMappings()).doesNotContain(technicianEquipmentMappingBack);
        assertThat(technicianEquipmentMappingBack.getEquipment()).isNull();
    }

    @Test
    void testCategoriesTest() throws Exception {
        Equipment equipment = getEquipmentRandomSampleGenerator();
        TestCategories testCategoriesBack = getTestCategoriesRandomSampleGenerator();

        equipment.addTestCategories(testCategoriesBack);
        assertThat(equipment.getTestCategories()).containsOnly(testCategoriesBack);
        assertThat(testCategoriesBack.getEquipment()).isEqualTo(equipment);

        equipment.removeTestCategories(testCategoriesBack);
        assertThat(equipment.getTestCategories()).doesNotContain(testCategoriesBack);
        assertThat(testCategoriesBack.getEquipment()).isNull();

        equipment.testCategories(new HashSet<>(Set.of(testCategoriesBack)));
        assertThat(equipment.getTestCategories()).containsOnly(testCategoriesBack);
        assertThat(testCategoriesBack.getEquipment()).isEqualTo(equipment);

        equipment.setTestCategories(new HashSet<>());
        assertThat(equipment.getTestCategories()).doesNotContain(testCategoriesBack);
        assertThat(testCategoriesBack.getEquipment()).isNull();
    }
}
