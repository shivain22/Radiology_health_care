package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.Equipment;
import com.radiology.health.care.domain.TestCategories;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.service.dto.EquipmentDTO;
import com.radiology.health.care.service.dto.TestCategoriesDTO;
import com.radiology.health.care.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TestCategories} and its DTO {@link TestCategoriesDTO}.
 */
@Mapper(componentModel = "spring")
public interface TestCategoriesMapper extends EntityMapper<TestCategoriesDTO, TestCategories> {
    @Mapping(target = "equipment", source = "equipment", qualifiedByName = "equipmentId")
    @Mapping(target = "parentTestCategory", source = "parentTestCategory", qualifiedByName = "testCategoriesId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    TestCategoriesDTO toDto(TestCategories s);

    @Named("equipmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EquipmentDTO toDtoEquipmentId(Equipment equipment);

    @Named("testCategoriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TestCategoriesDTO toDtoTestCategoriesId(TestCategories testCategories);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
