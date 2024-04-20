package com.radiology.health.care.domain;

import static com.radiology.health.care.domain.OfficeTimingsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OfficeTimingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfficeTimings.class);
        OfficeTimings officeTimings1 = getOfficeTimingsSample1();
        OfficeTimings officeTimings2 = new OfficeTimings();
        assertThat(officeTimings1).isNotEqualTo(officeTimings2);

        officeTimings2.setId(officeTimings1.getId());
        assertThat(officeTimings1).isEqualTo(officeTimings2);

        officeTimings2 = getOfficeTimingsSample2();
        assertThat(officeTimings1).isNotEqualTo(officeTimings2);
    }
}
