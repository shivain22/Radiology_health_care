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
    @Mapping(target = "equipmentId", source = "equipment.id")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "userId", source = "user.id")
    TechnicianEquipmentMappingDTO toDto(TechnicianEquipmentMapping s);

    @Mapping(target = "equipment.id", source = "equipmentId")
    @Mapping(target = "employee.id", source = "employeeId")
    @Mapping(target = "user.id", source = "userId")
    default TechnicianEquipmentMapping toEntity(TechnicianEquipmentMappingDTO dto) {
        if (dto == null) {
            return null;
        }

        TechnicianEquipmentMapping technicianEquipmentMapping = new TechnicianEquipmentMapping();
        Equipment equipment = new Equipment();
        Employee employee = new Employee();
        User user = new User();

        equipment.setId(dto.getEquipmentId());
        employee.setId(dto.getEmployeeId());
        user.setId(dto.getUserId());

        technicianEquipmentMapping.setCreatedBy(dto.getCreatedBy());
        technicianEquipmentMapping.setCreatedDate(dto.getCreatedDate());
        technicianEquipmentMapping.setLastModifiedBy(dto.getLastModifiedBy());
        technicianEquipmentMapping.setLastModifiedDate(dto.getLastModifiedDate());
        technicianEquipmentMapping.setId(dto.getId());
        technicianEquipmentMapping.setDateTime(dto.getDateTime());
        technicianEquipmentMapping.equipment(equipment);
        technicianEquipmentMapping.employee(employee);
        technicianEquipmentMapping.user(user);
        technicianEquipmentMapping.setLogin(dto.getLogin());

        return technicianEquipmentMapping;
    }

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
