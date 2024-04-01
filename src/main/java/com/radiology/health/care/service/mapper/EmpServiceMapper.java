package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.EmpService;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.service.dto.EmpServiceDTO;
import com.radiology.health.care.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmpService} and its DTO {@link EmpServiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmpServiceMapper extends EntityMapper<EmpServiceDTO, EmpService> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    EmpServiceDTO toDto(EmpService s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
