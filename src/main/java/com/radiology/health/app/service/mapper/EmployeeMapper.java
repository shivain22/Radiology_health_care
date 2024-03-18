package com.radiology.health.app.service.mapper;

import com.radiology.health.app.domain.Employee;
import com.radiology.health.app.domain.Rank;
import com.radiology.health.app.domain.Services;
import com.radiology.health.app.domain.Unit;
import com.radiology.health.app.service.dto.EmployeeDTO;
import com.radiology.health.app.service.dto.RankDTO;
import com.radiology.health.app.service.dto.ServicesDTO;
import com.radiology.health.app.service.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitId")
    @Mapping(target = "services", source = "services", qualifiedByName = "servicesId")
    @Mapping(target = "rank", source = "rank", qualifiedByName = "rankId")
    EmployeeDTO toDto(Employee s);

    @Named("unitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UnitDTO toDtoUnitId(Unit unit);

    @Named("servicesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServicesDTO toDtoServicesId(Services services);

    @Named("rankId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RankDTO toDtoRankId(Rank rank);
}
