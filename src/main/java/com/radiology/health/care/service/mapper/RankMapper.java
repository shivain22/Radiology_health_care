package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.EmpService;
import com.radiology.health.care.domain.Rank;
import com.radiology.health.care.service.dto.EmpServiceDTO;
import com.radiology.health.care.service.dto.RankDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rank} and its DTO {@link RankDTO}.
 */
@Mapper(componentModel = "spring")
public interface RankMapper extends EntityMapper<RankDTO, Rank> {
    @Mapping(target = "empService", source = "empService", qualifiedByName = "empServiceId")
    RankDTO toDto(Rank s);

    @Named("empServiceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpServiceDTO toDtoEmpServiceId(EmpService empService);
}
