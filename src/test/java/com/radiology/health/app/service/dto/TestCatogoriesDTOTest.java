package com.radiology.health.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TestCatogoriesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestCatogoriesDTO.class);
        TestCatogoriesDTO testCatogoriesDTO1 = new TestCatogoriesDTO();
        testCatogoriesDTO1.setId(1L);
        TestCatogoriesDTO testCatogoriesDTO2 = new TestCatogoriesDTO();
        assertThat(testCatogoriesDTO1).isNotEqualTo(testCatogoriesDTO2);
        testCatogoriesDTO2.setId(testCatogoriesDTO1.getId());
        assertThat(testCatogoriesDTO1).isEqualTo(testCatogoriesDTO2);
        testCatogoriesDTO2.setId(2L);
        assertThat(testCatogoriesDTO1).isNotEqualTo(testCatogoriesDTO2);
        testCatogoriesDTO1.setId(null);
        assertThat(testCatogoriesDTO1).isNotEqualTo(testCatogoriesDTO2);
    }
}
