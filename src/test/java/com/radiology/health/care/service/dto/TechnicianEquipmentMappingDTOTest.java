package com.radiology.health.care.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TechnicianEquipmentMappingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechnicianEquipmentMappingDTO.class);
        TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO1 = new TechnicianEquipmentMappingDTO();
        technicianEquipmentMappingDTO1.setId(1L);
        TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO2 = new TechnicianEquipmentMappingDTO();
        assertThat(technicianEquipmentMappingDTO1).isNotEqualTo(technicianEquipmentMappingDTO2);
        technicianEquipmentMappingDTO2.setId(technicianEquipmentMappingDTO1.getId());
        assertThat(technicianEquipmentMappingDTO1).isEqualTo(technicianEquipmentMappingDTO2);
        technicianEquipmentMappingDTO2.setId(2L);
        assertThat(technicianEquipmentMappingDTO1).isNotEqualTo(technicianEquipmentMappingDTO2);
        technicianEquipmentMappingDTO1.setId(null);
        assertThat(technicianEquipmentMappingDTO1).isNotEqualTo(technicianEquipmentMappingDTO2);
    }
}
