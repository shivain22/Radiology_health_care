package com.radiology.health.app.service.mapper;

import com.radiology.health.app.domain.Equipments;
import com.radiology.health.app.domain.Rooms;
import com.radiology.health.app.service.dto.EquipmentsDTO;
import com.radiology.health.app.service.dto.RoomsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Equipments} and its DTO {@link EquipmentsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EquipmentsMapper extends EntityMapper<EquipmentsDTO, Equipments> {
    @Mapping(target = "rooms", source = "rooms", qualifiedByName = "roomsId")
    EquipmentsDTO toDto(Equipments s);

    @Named("roomsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoomsDTO toDtoRoomsId(Rooms rooms);
}
