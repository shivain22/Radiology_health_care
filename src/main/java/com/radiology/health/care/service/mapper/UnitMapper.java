package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.EmpService;
import com.radiology.health.care.domain.Unit;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.service.dto.EmpServiceDTO;
import com.radiology.health.care.service.dto.UnitDTO;
import com.radiology.health.care.service.dto.UserDTO;
import org.mapstruct.*;
import org.mapstruct.control.MappingControl;

/**
 * Mapper for the entity {@link Unit} and its DTO {@link UnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface UnitMapper extends EntityMapper<UnitDTO, Unit> {
    @Mapping(target = "empServiceId", source = "empService.id")
    @Mapping(target = "userId", source = "user.id")
    UnitDTO toDto(Unit s);

    @Mapping(target = "empService.id", source = "empServiceId")
    @Mapping(target = "user.id", source = "userId")
    default Unit toEntity(UnitDTO dto) {
        if (dto == null) {
            return null;
        }

        Unit unit = new Unit();
        EmpService empService = new EmpService();
        User user = new User();

        empService.setId(dto.getEmpServiceId());
        user.setId(dto.getUserId());

        unit.setCreatedBy(dto.getCreatedBy());
        unit.setCreatedDate(dto.getCreatedDate());
        unit.setLastModifiedBy(dto.getLastModifiedBy());
        unit.setLastModifiedDate(dto.getLastModifiedDate());
        unit.setId(dto.getId());
        unit.setName(dto.getName());
        unit.empService(empService);
        unit.user(user);
        unit.setLogin(dto.getLogin());

        return unit;
    }

    @Named("empServiceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpServiceDTO toDtoEmpServiceId(EmpService empService);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
