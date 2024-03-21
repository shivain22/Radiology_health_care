package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.Equipment;
import com.radiology.health.care.domain.Room;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.service.dto.EquipmentDTO;
import com.radiology.health.care.service.dto.RoomDTO;
import com.radiology.health.care.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Equipment} and its DTO {@link EquipmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface EquipmentMapper extends EntityMapper<EquipmentDTO, Equipment> {
    @Mapping(target = "room", source = "room", qualifiedByName = "roomId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    EquipmentDTO toDto(Equipment s);

    @Named("roomId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoomDTO toDtoRoomId(Room room);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
