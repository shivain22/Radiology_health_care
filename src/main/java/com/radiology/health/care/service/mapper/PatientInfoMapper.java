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
    @Mapping(target = "employeeIdId", source = "employeeId.id")
    @Mapping(target = "employeeHisNoId", source = "employeeHis.his")
    @Mapping(target = "employeeServiceNoId", source = "employeeServiceNo.serviceNo")
    PatientInfoDTO toDto(PatientInfo s);

    @Mapping(target = "employeeId.id", source = "employeeIdId")
    @Mapping(target = "employeeHis.his", source = "employeeHisNoId")
    @Mapping(target = "employeeServiceNo.serviceNo", source = "employeeServiceNoId")
    default PatientInfo toEntity(PatientInfoDTO dto) {
        if (dto == null) {
            return null;
        }

        PatientInfo patientInfo = new PatientInfo();
        Employee employeeId = new Employee();
        Employee employeeHis = new Employee();
        Employee employeeServiceNo = new Employee();

        employeeId.setId(dto.getEmployeeIdId());
        employeeHis.setId(dto.getEmployeeIdId());
        employeeHis.setHis(dto.getEmployeeHisNoId());
        employeeServiceNo.setId(dto.getEmployeeIdId());
        employeeServiceNo.serviceNo(dto.getEmployeeServiceNoId());

        patientInfo.setId(dto.getId());
        patientInfo.setName(dto.getName());
        patientInfo.setAge(dto.getAge());
        patientInfo.setGender(dto.getGender());
        patientInfo.setDateOfBirth(dto.getDateOfBirth());
        patientInfo.setMobile(dto.getMobile());
        patientInfo.setRelation(dto.getRelation());
        patientInfo.employeeId(employeeId);
        patientInfo.employeeHis(employeeHis);
        patientInfo.employeeServiceNo(employeeServiceNo);

        return patientInfo;
    }

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
