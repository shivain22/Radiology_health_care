package com.radiology.health.app.domain;

import static com.radiology.health.app.domain.EquipmentsTestSamples.*;
import static com.radiology.health.app.domain.RoomsTestSamples.*;
import static com.radiology.health.app.domain.TechicianEquipmentMappingTestSamples.*;
import static com.radiology.health.app.domain.TestCatogoriesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EquipmentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipments.class);
        Equipments equipments1 = getEquipmentsSample1();
        Equipments equipments2 = new Equipments();
        assertThat(equipments1).isNotEqualTo(equipments2);

        equipments2.setId(equipments1.getId());
        assertThat(equipments1).isEqualTo(equipments2);

        equipments2 = getEquipmentsSample2();
        assertThat(equipments1).isNotEqualTo(equipments2);
    }

    @Test
    void roomsTest() throws Exception {
        Equipments equipments = getEquipmentsRandomSampleGenerator();
        Rooms roomsBack = getRoomsRandomSampleGenerator();

        equipments.setRooms(roomsBack);
        assertThat(equipments.getRooms()).isEqualTo(roomsBack);

        equipments.rooms(null);
        assertThat(equipments.getRooms()).isNull();
    }

    @Test
    void testCatogoriesTest() throws Exception {
        Equipments equipments = getEquipmentsRandomSampleGenerator();
        TestCatogories testCatogoriesBack = getTestCatogoriesRandomSampleGenerator();

        equipments.addTestCatogories(testCatogoriesBack);
        assertThat(equipments.getTestCatogories()).containsOnly(testCatogoriesBack);
        assertThat(testCatogoriesBack.getEquipments()).isEqualTo(equipments);

        equipments.removeTestCatogories(testCatogoriesBack);
        assertThat(equipments.getTestCatogories()).doesNotContain(testCatogoriesBack);
        assertThat(testCatogoriesBack.getEquipments()).isNull();

        equipments.testCatogories(new HashSet<>(Set.of(testCatogoriesBack)));
        assertThat(equipments.getTestCatogories()).containsOnly(testCatogoriesBack);
        assertThat(testCatogoriesBack.getEquipments()).isEqualTo(equipments);

        equipments.setTestCatogories(new HashSet<>());
        assertThat(equipments.getTestCatogories()).doesNotContain(testCatogoriesBack);
        assertThat(testCatogoriesBack.getEquipments()).isNull();
    }

    @Test
    void techicianEquipmentMappingTest() throws Exception {
        Equipments equipments = getEquipmentsRandomSampleGenerator();
        TechicianEquipmentMapping techicianEquipmentMappingBack = getTechicianEquipmentMappingRandomSampleGenerator();

        equipments.addTechicianEquipmentMapping(techicianEquipmentMappingBack);
        assertThat(equipments.getTechicianEquipmentMappings()).containsOnly(techicianEquipmentMappingBack);
        assertThat(techicianEquipmentMappingBack.getEquipments()).isEqualTo(equipments);

        equipments.removeTechicianEquipmentMapping(techicianEquipmentMappingBack);
        assertThat(equipments.getTechicianEquipmentMappings()).doesNotContain(techicianEquipmentMappingBack);
        assertThat(techicianEquipmentMappingBack.getEquipments()).isNull();

        equipments.techicianEquipmentMappings(new HashSet<>(Set.of(techicianEquipmentMappingBack)));
        assertThat(equipments.getTechicianEquipmentMappings()).containsOnly(techicianEquipmentMappingBack);
        assertThat(techicianEquipmentMappingBack.getEquipments()).isEqualTo(equipments);

        equipments.setTechicianEquipmentMappings(new HashSet<>());
        assertThat(equipments.getTechicianEquipmentMappings()).doesNotContain(techicianEquipmentMappingBack);
        assertThat(techicianEquipmentMappingBack.getEquipments()).isNull();
    }
}
