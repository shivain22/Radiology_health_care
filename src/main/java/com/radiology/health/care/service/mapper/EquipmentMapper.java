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
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "userId", source = "user.id")
    EquipmentDTO toDto(Equipment s);

    @Mapping(target = "room.id", source = "roomId")
    @Mapping(target = "user.id", source = "userId")
    default Equipment toEntity(EquipmentDTO dto) {
        if (dto == null) {
            return null;
        }

        Equipment equipment = new Equipment();
        Room room = new Room();
        User user = new User();

        room.setId(dto.getRoomId());
        user.setId(dto.getUserId());

        equipment.setCreatedBy(dto.getCreatedBy());
        equipment.setCreatedDate(dto.getCreatedDate());
        equipment.setLastModifiedBy(dto.getLastModifiedBy());
        equipment.setLastModifiedDate(dto.getLastModifiedDate());
        equipment.setId(dto.getId());
        equipment.setName(dto.getName());
        equipment.room(room);
        equipment.user(user);
        equipment.setLogin(dto.getLogin());

        return equipment;
    }

    @Named("roomId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoomDTO toDtoRoomId(Room room);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
