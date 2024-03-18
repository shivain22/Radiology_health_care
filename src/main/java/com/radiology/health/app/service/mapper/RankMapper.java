package com.radiology.health.app.service.mapper;

import com.radiology.health.app.domain.Rank;
import com.radiology.health.app.domain.Services;
import com.radiology.health.app.service.dto.RankDTO;
import com.radiology.health.app.service.dto.ServicesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rank} and its DTO {@link RankDTO}.
 */
@Mapper(componentModel = "spring")
public interface RankMapper extends EntityMapper<RankDTO, Rank> {
    @Mapping(target = "services", source = "services", qualifiedByName = "servicesId")
    RankDTO toDto(Rank s);

    @Named("servicesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServicesDTO toDtoServicesId(Services services);
}
