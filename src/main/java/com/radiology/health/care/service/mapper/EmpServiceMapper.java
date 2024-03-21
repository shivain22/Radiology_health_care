package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.EmpService;
import com.radiology.health.care.service.dto.EmpServiceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmpService} and its DTO {@link EmpServiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmpServiceMapper extends EntityMapper<EmpServiceDTO, EmpService> {}
