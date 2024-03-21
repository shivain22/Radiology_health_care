package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.EmpService;
import com.radiology.health.care.domain.Employee;
import com.radiology.health.care.domain.Rank;
import com.radiology.health.care.domain.Unit;
import com.radiology.health.care.service.dto.EmpServiceDTO;
import com.radiology.health.care.service.dto.EmployeeDTO;
import com.radiology.health.care.service.dto.RankDTO;
import com.radiology.health.care.service.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "rank", source = "rank", qualifiedByName = "rankId")
    @Mapping(target = "empService", source = "empService", qualifiedByName = "empServiceId")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitId")
    EmployeeDTO toDto(Employee s);

    @Named("rankId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RankDTO toDtoRankId(Rank rank);

    @Named("empServiceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpServiceDTO toDtoEmpServiceId(EmpService empService);

    @Named("unitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UnitDTO toDtoUnitId(Unit unit);
}
