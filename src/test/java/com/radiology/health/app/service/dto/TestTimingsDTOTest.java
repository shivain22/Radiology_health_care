package com.radiology.health.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TestTimingsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestTimingsDTO.class);
        TestTimingsDTO testTimingsDTO1 = new TestTimingsDTO();
        testTimingsDTO1.setId(1L);
        TestTimingsDTO testTimingsDTO2 = new TestTimingsDTO();
        assertThat(testTimingsDTO1).isNotEqualTo(testTimingsDTO2);
        testTimingsDTO2.setId(testTimingsDTO1.getId());
        assertThat(testTimingsDTO1).isEqualTo(testTimingsDTO2);
        testTimingsDTO2.setId(2L);
        assertThat(testTimingsDTO1).isNotEqualTo(testTimingsDTO2);
        testTimingsDTO1.setId(null);
        assertThat(testTimingsDTO1).isNotEqualTo(testTimingsDTO2);
    }
}
