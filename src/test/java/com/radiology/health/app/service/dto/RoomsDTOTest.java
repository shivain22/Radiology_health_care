package com.radiology.health.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoomsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomsDTO.class);
        RoomsDTO roomsDTO1 = new RoomsDTO();
        roomsDTO1.setId(1L);
        RoomsDTO roomsDTO2 = new RoomsDTO();
        assertThat(roomsDTO1).isNotEqualTo(roomsDTO2);
        roomsDTO2.setId(roomsDTO1.getId());
        assertThat(roomsDTO1).isEqualTo(roomsDTO2);
        roomsDTO2.setId(2L);
        assertThat(roomsDTO1).isNotEqualTo(roomsDTO2);
        roomsDTO1.setId(null);
        assertThat(roomsDTO1).isNotEqualTo(roomsDTO2);
    }
}
