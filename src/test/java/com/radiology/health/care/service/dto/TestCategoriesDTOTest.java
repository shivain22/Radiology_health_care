package com.radiology.health.care.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TestCategoriesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestCategoriesDTO.class);
        TestCategoriesDTO testCategoriesDTO1 = new TestCategoriesDTO();
        testCategoriesDTO1.setId(1L);
        TestCategoriesDTO testCategoriesDTO2 = new TestCategoriesDTO();
        assertThat(testCategoriesDTO1).isNotEqualTo(testCategoriesDTO2);
        testCategoriesDTO2.setId(testCategoriesDTO1.getId());
        assertThat(testCategoriesDTO1).isEqualTo(testCategoriesDTO2);
        testCategoriesDTO2.setId(2L);
        assertThat(testCategoriesDTO1).isNotEqualTo(testCategoriesDTO2);
        testCategoriesDTO1.setId(null);
        assertThat(testCategoriesDTO1).isNotEqualTo(testCategoriesDTO2);
    }
}
