package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.EmpService;
import com.radiology.health.care.domain.Unit;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.service.dto.EmpServiceDTO;
import com.radiology.health.care.service.dto.UnitDTO;
import com.radiology.health.care.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Unit} and its DTO {@link UnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface UnitMapper extends EntityMapper<UnitDTO, Unit> {
    @Mapping(target = "empService", source = "empService", qualifiedByName = "empServiceId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    UnitDTO toDto(Unit s);

    @Named("empServiceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpServiceDTO toDtoEmpServiceId(EmpService empService);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
