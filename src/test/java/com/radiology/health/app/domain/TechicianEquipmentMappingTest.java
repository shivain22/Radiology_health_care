package com.radiology.health.app.domain;

import static com.radiology.health.app.domain.EmployeeTestSamples.*;
import static com.radiology.health.app.domain.EquipmentsTestSamples.*;
import static com.radiology.health.app.domain.TechicianEquipmentMappingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TechicianEquipmentMappingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechicianEquipmentMapping.class);
        TechicianEquipmentMapping techicianEquipmentMapping1 = getTechicianEquipmentMappingSample1();
        TechicianEquipmentMapping techicianEquipmentMapping2 = new TechicianEquipmentMapping();
        assertThat(techicianEquipmentMapping1).isNotEqualTo(techicianEquipmentMapping2);

        techicianEquipmentMapping2.setId(techicianEquipmentMapping1.getId());
        assertThat(techicianEquipmentMapping1).isEqualTo(techicianEquipmentMapping2);

        techicianEquipmentMapping2 = getTechicianEquipmentMappingSample2();
        assertThat(techicianEquipmentMapping1).isNotEqualTo(techicianEquipmentMapping2);
    }

    @Test
    void employeeTest() throws Exception {
        TechicianEquipmentMapping techicianEquipmentMapping = getTechicianEquipmentMappingRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        techicianEquipmentMapping.setEmployee(employeeBack);
        assertThat(techicianEquipmentMapping.getEmployee()).isEqualTo(employeeBack);

        techicianEquipmentMapping.employee(null);
        assertThat(techicianEquipmentMapping.getEmployee()).isNull();
    }

    @Test
    void equipmentsTest() throws Exception {
        TechicianEquipmentMapping techicianEquipmentMapping = getTechicianEquipmentMappingRandomSampleGenerator();
        Equipments equipmentsBack = getEquipmentsRandomSampleGenerator();

        techicianEquipmentMapping.setEquipments(equipmentsBack);
        assertThat(techicianEquipmentMapping.getEquipments()).isEqualTo(equipmentsBack);

        techicianEquipmentMapping.equipments(null);
        assertThat(techicianEquipmentMapping.getEquipments()).isNull();
    }
}
