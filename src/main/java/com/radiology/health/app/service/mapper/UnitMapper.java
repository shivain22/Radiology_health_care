package com.radiology.health.app.service.mapper;

import com.radiology.health.app.domain.Unit;
import com.radiology.health.app.service.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Unit} and its DTO {@link UnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface UnitMapper extends EntityMapper<UnitDTO, Unit> {}
