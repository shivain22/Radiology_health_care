package com.radiology.health.app.service.mapper;

import com.radiology.health.app.domain.Services;
import com.radiology.health.app.service.dto.ServicesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Services} and its DTO {@link ServicesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServicesMapper extends EntityMapper<ServicesDTO, Services> {}
