package com.radiology.health.app.domain;

import static com.radiology.health.app.domain.EquipmentsTestSamples.*;
import static com.radiology.health.app.domain.RoomsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RoomsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rooms.class);
        Rooms rooms1 = getRoomsSample1();
        Rooms rooms2 = new Rooms();
        assertThat(rooms1).isNotEqualTo(rooms2);

        rooms2.setId(rooms1.getId());
        assertThat(rooms1).isEqualTo(rooms2);

        rooms2 = getRoomsSample2();
        assertThat(rooms1).isNotEqualTo(rooms2);
    }

    @Test
    void equipmentsTest() throws Exception {
        Rooms rooms = getRoomsRandomSampleGenerator();
        Equipments equipmentsBack = getEquipmentsRandomSampleGenerator();

        rooms.addEquipments(equipmentsBack);
        assertThat(rooms.getEquipments()).containsOnly(equipmentsBack);
        assertThat(equipmentsBack.getRooms()).isEqualTo(rooms);

        rooms.removeEquipments(equipmentsBack);
        assertThat(rooms.getEquipments()).doesNotContain(equipmentsBack);
        assertThat(equipmentsBack.getRooms()).isNull();

        rooms.equipments(new HashSet<>(Set.of(equipmentsBack)));
        assertThat(rooms.getEquipments()).containsOnly(equipmentsBack);
        assertThat(equipmentsBack.getRooms()).isEqualTo(rooms);

        rooms.setEquipments(new HashSet<>());
        assertThat(rooms.getEquipments()).doesNotContain(equipmentsBack);
        assertThat(equipmentsBack.getRooms()).isNull();
    }
}
