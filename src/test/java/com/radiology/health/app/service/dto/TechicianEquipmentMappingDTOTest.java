package com.radiology.health.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TechicianEquipmentMappingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechicianEquipmentMappingDTO.class);
        TechicianEquipmentMappingDTO techicianEquipmentMappingDTO1 = new TechicianEquipmentMappingDTO();
        techicianEquipmentMappingDTO1.setId(1L);
        TechicianEquipmentMappingDTO techicianEquipmentMappingDTO2 = new TechicianEquipmentMappingDTO();
        assertThat(techicianEquipmentMappingDTO1).isNotEqualTo(techicianEquipmentMappingDTO2);
        techicianEquipmentMappingDTO2.setId(techicianEquipmentMappingDTO1.getId());
        assertThat(techicianEquipmentMappingDTO1).isEqualTo(techicianEquipmentMappingDTO2);
        techicianEquipmentMappingDTO2.setId(2L);
        assertThat(techicianEquipmentMappingDTO1).isNotEqualTo(techicianEquipmentMappingDTO2);
        techicianEquipmentMappingDTO1.setId(null);
        assertThat(techicianEquipmentMappingDTO1).isNotEqualTo(techicianEquipmentMappingDTO2);
    }
}
