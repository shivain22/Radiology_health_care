package com.radiology.health.care.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OfficeTimingsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfficeTimingsDTO.class);
        OfficeTimingsDTO officeTimingsDTO1 = new OfficeTimingsDTO();
        officeTimingsDTO1.setId(1L);
        OfficeTimingsDTO officeTimingsDTO2 = new OfficeTimingsDTO();
        assertThat(officeTimingsDTO1).isNotEqualTo(officeTimingsDTO2);
        officeTimingsDTO2.setId(officeTimingsDTO1.getId());
        assertThat(officeTimingsDTO1).isEqualTo(officeTimingsDTO2);
        officeTimingsDTO2.setId(2L);
        assertThat(officeTimingsDTO1).isNotEqualTo(officeTimingsDTO2);
        officeTimingsDTO1.setId(null);
        assertThat(officeTimingsDTO1).isNotEqualTo(officeTimingsDTO2);
    }
}
