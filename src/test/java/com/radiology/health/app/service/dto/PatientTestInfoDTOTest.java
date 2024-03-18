package com.radiology.health.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PatientTestInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientTestInfoDTO.class);
        PatientTestInfoDTO patientTestInfoDTO1 = new PatientTestInfoDTO();
        patientTestInfoDTO1.setId(1L);
        PatientTestInfoDTO patientTestInfoDTO2 = new PatientTestInfoDTO();
        assertThat(patientTestInfoDTO1).isNotEqualTo(patientTestInfoDTO2);
        patientTestInfoDTO2.setId(patientTestInfoDTO1.getId());
        assertThat(patientTestInfoDTO1).isEqualTo(patientTestInfoDTO2);
        patientTestInfoDTO2.setId(2L);
        assertThat(patientTestInfoDTO1).isNotEqualTo(patientTestInfoDTO2);
        patientTestInfoDTO1.setId(null);
        assertThat(patientTestInfoDTO1).isNotEqualTo(patientTestInfoDTO2);
    }
}
