package com.radiology.health.care.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmpServiceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpServiceDTO.class);
        EmpServiceDTO empServiceDTO1 = new EmpServiceDTO();
        empServiceDTO1.setId(1L);
        EmpServiceDTO empServiceDTO2 = new EmpServiceDTO();
        assertThat(empServiceDTO1).isNotEqualTo(empServiceDTO2);
        empServiceDTO2.setId(empServiceDTO1.getId());
        assertThat(empServiceDTO1).isEqualTo(empServiceDTO2);
        empServiceDTO2.setId(2L);
        assertThat(empServiceDTO1).isNotEqualTo(empServiceDTO2);
        empServiceDTO1.setId(null);
        assertThat(empServiceDTO1).isNotEqualTo(empServiceDTO2);
    }
}
