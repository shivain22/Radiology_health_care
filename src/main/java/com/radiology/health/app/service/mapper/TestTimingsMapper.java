package com.radiology.health.app.service.mapper;

import com.radiology.health.app.domain.TestCatogories;
import com.radiology.health.app.domain.TestTimings;
import com.radiology.health.app.service.dto.TestCatogoriesDTO;
import com.radiology.health.app.service.dto.TestTimingsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TestTimings} and its DTO {@link TestTimingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface TestTimingsMapper extends EntityMapper<TestTimingsDTO, TestTimings> {
    @Mapping(target = "testCatogories", source = "testCatogories", qualifiedByName = "testCatogoriesId")
    TestTimingsDTO toDto(TestTimings s);

    @Named("testCatogoriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TestCatogoriesDTO toDtoTestCatogoriesId(TestCatogories testCatogories);
}
