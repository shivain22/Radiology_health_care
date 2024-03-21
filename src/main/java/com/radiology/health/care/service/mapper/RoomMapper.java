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
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    RoomDTO toDto(Room s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
