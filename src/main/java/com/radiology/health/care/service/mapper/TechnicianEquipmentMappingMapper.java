package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.Employee;
import com.radiology.health.care.domain.Equipment;
import com.radiology.health.care.domain.TechnicianEquipmentMapping;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.service.dto.EmployeeDTO;
import com.radiology.health.care.service.dto.EquipmentDTO;
import com.radiology.health.care.service.dto.TechnicianEquipmentMappingDTO;
import com.radiology.health.care.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TechnicianEquipmentMapping} and its DTO {@link TechnicianEquipmentMappingDTO}.
 */
@Mapper(componentModel = "spring")
public interface TechnicianEquipmentMappingMapper extends EntityMapper<TechnicianEquipmentMappingDTO, TechnicianEquipmentMapping> {
    @Mapping(target = "equipment", source = "equipment", qualifiedByName = "equipmentId")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    TechnicianEquipmentMappingDTO toDto(TechnicianEquipmentMapping s);

    @Named("equipmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EquipmentDTO toDtoEquipmentId(Equipment equipment);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
