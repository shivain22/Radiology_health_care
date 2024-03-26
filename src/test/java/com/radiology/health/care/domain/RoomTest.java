package com.radiology.health.care.domain;

import static com.radiology.health.care.domain.EquipmentTestSamples.*;
import static com.radiology.health.care.domain.RoomTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RoomTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Room.class);
        Room room1 = getRoomSample1();
        Room room2 = new Room();
        assertThat(room1).isNotEqualTo(room2);

        room2.setId(room1.getId());
        assertThat(room1).isEqualTo(room2);

        room2 = getRoomSample2();
        assertThat(room1).isNotEqualTo(room2);
    }

    @Test
    void equipmentTest() throws Exception {
        Room room = getRoomRandomSampleGenerator();
        Equipment equipmentBack = getEquipmentRandomSampleGenerator();

        room.addEquipment(equipmentBack);
        assertThat(room.getEquipment()).containsOnly(equipmentBack);
        assertThat(equipmentBack.getRoom()).isEqualTo(room);

        room.removeEquipment(equipmentBack);
        assertThat(room.getEquipment()).doesNotContain(equipmentBack);
        assertThat(equipmentBack.getRoom()).isNull();

        room.equipment(new HashSet<>(Set.of(equipmentBack)));
        assertThat(room.getEquipment()).containsOnly(equipmentBack);
        assertThat(equipmentBack.getRoom()).isEqualTo(room);

        room.setEquipment(new HashSet<>());
        assertThat(room.getEquipment()).doesNotContain(equipmentBack);
        assertThat(equipmentBack.getRoom()).isNull();
    }
}
