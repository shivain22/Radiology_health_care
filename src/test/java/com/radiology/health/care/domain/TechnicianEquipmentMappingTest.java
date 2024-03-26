package com.radiology.health.care.domain;

import static com.radiology.health.care.domain.EmployeeTestSamples.*;
import static com.radiology.health.care.domain.EquipmentTestSamples.*;
import static com.radiology.health.care.domain.TechnicianEquipmentMappingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.radiology.health.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TechnicianEquipmentMappingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechnicianEquipmentMapping.class);
        TechnicianEquipmentMapping technicianEquipmentMapping1 = getTechnicianEquipmentMappingSample1();
        TechnicianEquipmentMapping technicianEquipmentMapping2 = new TechnicianEquipmentMapping();
        assertThat(technicianEquipmentMapping1).isNotEqualTo(technicianEquipmentMapping2);

        technicianEquipmentMapping2.setId(technicianEquipmentMapping1.getId());
        assertThat(technicianEquipmentMapping1).isEqualTo(technicianEquipmentMapping2);

        technicianEquipmentMapping2 = getTechnicianEquipmentMappingSample2();
        assertThat(technicianEquipmentMapping1).isNotEqualTo(technicianEquipmentMapping2);
    }

    @Test
    void equipmentTest() throws Exception {
        TechnicianEquipmentMapping technicianEquipmentMapping = getTechnicianEquipmentMappingRandomSampleGenerator();
        Equipment equipmentBack = getEquipmentRandomSampleGenerator();

        technicianEquipmentMapping.setEquipment(equipmentBack);
        assertThat(technicianEquipmentMapping.getEquipment()).isEqualTo(equipmentBack);

        technicianEquipmentMapping.equipment(null);
        assertThat(technicianEquipmentMapping.getEquipment()).isNull();
    }

    @Test
    void employeeTest() throws Exception {
        TechnicianEquipmentMapping technicianEquipmentMapping = getTechnicianEquipmentMappingRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        technicianEquipmentMapping.setEmployee(employeeBack);
        assertThat(technicianEquipmentMapping.getEmployee()).isEqualTo(employeeBack);

        technicianEquipmentMapping.employee(null);
        assertThat(technicianEquipmentMapping.getEmployee()).isNull();
    }
}
