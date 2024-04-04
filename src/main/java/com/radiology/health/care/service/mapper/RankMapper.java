package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.EmpService;
import com.radiology.health.care.domain.Rank;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.service.dto.EmpServiceDTO;
import com.radiology.health.care.service.dto.RankDTO;
import com.radiology.health.care.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rank} and its DTO {@link RankDTO}.
 */
@Mapper(componentModel = "spring")
public interface RankMapper extends EntityMapper<RankDTO, Rank> {
    @Mapping(target = "empServiceId", source = "empService.id")
    @Mapping(target = "userId", source = "user.id")
    RankDTO toDto(Rank s);

    @Mapping(target = "empService.id", source = "empServiceId")
    @Mapping(target = "user.id", source = "userId")
    default Rank toEntity(RankDTO dto) {
        if (dto == null) {
            return null;
        }

        Rank rank = new Rank();
        EmpService empService = new EmpService();
        User user = new User();

        empService.setId(dto.getEmpServiceId());
        user.setId(dto.getUserId());

        rank.setId(dto.getId());
        rank.setName(dto.getName());
        rank.setShortName(dto.getShortName());
        rank.setDivision(dto.getDivision());

        rank.setCreatedBy(dto.getCreatedBy());
        rank.setCreatedDate(dto.getCreatedDate());
        rank.setLogin(dto.getLogin());
        rank.setLastModifiedBy(dto.getLastModifiedBy());
        rank.setLastModifiedDate(dto.getLastModifiedDate());
        rank.empService(empService);
        rank.user(user);

        return rank;
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
