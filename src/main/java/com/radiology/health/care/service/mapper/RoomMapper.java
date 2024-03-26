package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.Room;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.service.dto.RoomDTO;
import com.radiology.health.care.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Room} and its DTO {@link RoomDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoomMapper extends EntityMapper<RoomDTO, Room> {
    @Mapping(target = "userId", source = "user.id")
    RoomDTO toDto(Room s);

    @Mapping(target = "user.id", source = "userId")
    default Room toEntity(RoomDTO dto) {
        if (dto == null) {
            return null;
        }

        Room room = new Room();
        User user = new User();
        user.setId(dto.getUserId());

        room.setCreatedBy(dto.getCreatedBy());
        room.setCreatedDate(dto.getCreatedDate());
        room.setLastModifiedBy(dto.getLastModifiedBy());
        room.setLastModifiedDate(dto.getLastModifiedDate());
        room.setId(dto.getId());
        room.setRoomNo(dto.getRoomNo());
        room.user(user);
        room.setLogin(dto.getLogin());
        return room;
    }

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
