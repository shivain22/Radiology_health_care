package com.radiology.health.app.service.mapper;

import com.radiology.health.app.domain.Employee;
import com.radiology.health.app.domain.PatientInfo;
import com.radiology.health.app.service.dto.EmployeeDTO;
import com.radiology.health.app.service.dto.PatientInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PatientInfo} and its DTO {@link PatientInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PatientInfoMapper extends EntityMapper<PatientInfoDTO, PatientInfo> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    PatientInfoDTO toDto(PatientInfo s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
