package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.OfficeTimings;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.service.dto.OfficeTimingsDTO;
import com.radiology.health.care.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OfficeTimings} and its DTO {@link OfficeTimingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface OfficeTimingsMapper extends EntityMapper<OfficeTimingsDTO, OfficeTimings> {
    @Mapping(target = "userId", source = "user.id")
    OfficeTimingsDTO toDto(OfficeTimings s);

    @Mapping(target = "user.id", source = "userId")
    default OfficeTimings toEntity(OfficeTimingsDTO dto) {
        if (dto == null) {
            return null;
        }

        OfficeTimings officeTimings = new OfficeTimings();
        User user = new User();

        user.setId(dto.getUserId());

        officeTimings.setId(dto.getId());
        officeTimings.setDate(dto.getDate());
        officeTimings.setShiftStart(dto.getShiftStart());
        officeTimings.setShiftEnd(dto.getShiftEnd());
        officeTimings.setDefaultTimings(dto.getDefaultTimings());
        officeTimings.user(user);

        return officeTimings;
    }
}
