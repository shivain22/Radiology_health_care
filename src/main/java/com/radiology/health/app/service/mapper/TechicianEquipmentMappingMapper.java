package com.radiology.health.app.service.mapper;

import com.radiology.health.app.domain.Employee;
import com.radiology.health.app.domain.Equipments;
import com.radiology.health.app.domain.TechicianEquipmentMapping;
import com.radiology.health.app.service.dto.EmployeeDTO;
import com.radiology.health.app.service.dto.EquipmentsDTO;
import com.radiology.health.app.service.dto.TechicianEquipmentMappingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TechicianEquipmentMapping} and its DTO {@link TechicianEquipmentMappingDTO}.
 */
@Mapper(componentModel = "spring")
public interface TechicianEquipmentMappingMapper extends EntityMapper<TechicianEquipmentMappingDTO, TechicianEquipmentMapping> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    @Mapping(target = "equipments", source = "equipments", qualifiedByName = "equipmentsId")
    TechicianEquipmentMappingDTO toDto(TechicianEquipmentMapping s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("equipmentsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EquipmentsDTO toDtoEquipmentsId(Equipments equipments);
}
