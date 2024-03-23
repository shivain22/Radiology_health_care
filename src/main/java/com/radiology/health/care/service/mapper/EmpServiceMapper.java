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
    @Mapping(target = "userId", source = "user.id")
    EmpServiceDTO toDto(EmpService s);

    @Mapping(target = "user.id", source = "userId")
    default EmpService toEntity(EmpServiceDTO dto) {
        if (dto == null) {
            return null;
        }

        EmpService empService = new EmpService();
        User user = new User();

        user.setId(dto.getUserId());

        empService.setCreatedBy(dto.getCreatedBy());
        empService.setCreatedDate(dto.getCreatedDate());
        empService.setLastModifiedBy(dto.getLastModifiedBy());
        empService.setLastModifiedDate(dto.getLastModifiedDate());
        empService.setId(dto.getId());
        empService.setName(dto.getName());
        empService.user(user);
        empService.setLogin(dto.getLogin());

        return empService;
    }

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
