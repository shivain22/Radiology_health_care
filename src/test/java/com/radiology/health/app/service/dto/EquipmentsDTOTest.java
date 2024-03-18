package com.radiology.health.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EquipmentsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipmentsDTO.class);
        EquipmentsDTO equipmentsDTO1 = new EquipmentsDTO();
        equipmentsDTO1.setId(1L);
        EquipmentsDTO equipmentsDTO2 = new EquipmentsDTO();
        assertThat(equipmentsDTO1).isNotEqualTo(equipmentsDTO2);
        equipmentsDTO2.setId(equipmentsDTO1.getId());
        assertThat(equipmentsDTO1).isEqualTo(equipmentsDTO2);
        equipmentsDTO2.setId(2L);
        assertThat(equipmentsDTO1).isNotEqualTo(equipmentsDTO2);
        equipmentsDTO1.setId(null);
        assertThat(equipmentsDTO1).isNotEqualTo(equipmentsDTO2);
    }
}
