package com.radiology.health.app.service.mapper;

import com.radiology.health.app.domain.Equipments;
import com.radiology.health.app.domain.TestCatogories;
import com.radiology.health.app.service.dto.EquipmentsDTO;
import com.radiology.health.app.service.dto.TestCatogoriesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TestCatogories} and its DTO {@link TestCatogoriesDTO}.
 */
@Mapper(componentModel = "spring")
public interface TestCatogoriesMapper extends EntityMapper<TestCatogoriesDTO, TestCatogories> {
    @Mapping(target = "equipments", source = "equipments", qualifiedByName = "equipmentsId")
    @Mapping(target = "testCatogories_parent", source = "testCatogories_parent", qualifiedByName = "testCatogoriesId")
    TestCatogoriesDTO toDto(TestCatogories s);

    @Named("equipmentsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EquipmentsDTO toDtoEquipmentsId(Equipments equipments);

    @Named("testCatogoriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TestCatogoriesDTO toDtoTestCatogoriesId(TestCatogories testCatogories);
}
