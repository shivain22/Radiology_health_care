package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.Employee;
import com.radiology.health.care.domain.PatientInfo;
import com.radiology.health.care.service.dto.EmployeeDTO;
import com.radiology.health.care.service.dto.PatientInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PatientInfo} and its DTO {@link PatientInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PatientInfoMapper extends EntityMapper<PatientInfoDTO, PatientInfo> {
    @Mapping(target = "employeeId", source = "employeeId", qualifiedByName = "employeeId")
    @Mapping(target = "employeeHis", source = "employeeHis", qualifiedByName = "employeeHis")
    @Mapping(target = "employeeServiceNo", source = "employeeServiceNo", qualifiedByName = "employeeServiceNo")
    PatientInfoDTO toDto(PatientInfo s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("employeeHis")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "his", source = "his")
    EmployeeDTO toDtoEmployeeHis(Employee employee);

    @Named("employeeServiceNo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "serviceNo", source = "serviceNo")
    EmployeeDTO toDtoEmployeeServiceNo(Employee employee);
}
