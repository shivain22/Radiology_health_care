package com.radiology.health.app.service.mapper;

import com.radiology.health.app.domain.Rooms;
import com.radiology.health.app.service.dto.RoomsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rooms} and its DTO {@link RoomsDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoomsMapper extends EntityMapper<RoomsDTO, Rooms> {}
