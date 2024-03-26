package com.radiology.health.care.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PatientTestTimingsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientTestTimingsDTO.class);
        PatientTestTimingsDTO patientTestTimingsDTO1 = new PatientTestTimingsDTO();
        patientTestTimingsDTO1.setId(1L);
        PatientTestTimingsDTO patientTestTimingsDTO2 = new PatientTestTimingsDTO();
        assertThat(patientTestTimingsDTO1).isNotEqualTo(patientTestTimingsDTO2);
        patientTestTimingsDTO2.setId(patientTestTimingsDTO1.getId());
        assertThat(patientTestTimingsDTO1).isEqualTo(patientTestTimingsDTO2);
        patientTestTimingsDTO2.setId(2L);
        assertThat(patientTestTimingsDTO1).isNotEqualTo(patientTestTimingsDTO2);
        patientTestTimingsDTO1.setId(null);
        assertThat(patientTestTimingsDTO1).isNotEqualTo(patientTestTimingsDTO2);
    }
}
