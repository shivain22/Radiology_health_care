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
    @Mapping(target = "equipmentId", source = "equipment.id")
    @Mapping(target = "parentTestCategoryId", source = "parentTestCategory.id")
    @Mapping(target = "userId", source = "user.id")
    TestCategoriesDTO toDto(TestCategories s);

    @Mapping(target = "equipment.id", source = "equipmentId")
    @Mapping(target = "parentTestCategory.id", source = "parentTestCategoryId")
    @Mapping(target = "user.id", source = "userId")
    default TestCategories toEntity(TestCategoriesDTO dto) {
        if (dto == null) {
            return null;
        }

        TestCategories testCategories = new TestCategories();

        Equipment equipment = new Equipment();
        TestCategories parentTestCategory = null;
        User user = new User();

        equipment.setId(dto.getEquipmentId());
        if (dto.getParentTestCategoryId() != 0 && dto.getParentTestCategoryId() != null) {
            parentTestCategory = new TestCategories();
            parentTestCategory.setId(dto.getParentTestCategoryId());
        }
        user.setId(dto.getUserId());

        testCategories.setId(dto.getId());
        testCategories.setTestName(dto.getTestName());
        testCategories.setTestDuration(dto.getTestDuration());
        if (dto.getPatientReport() != null) {
            testCategories.setPatientReport(dto.getPatientReport());
        }
        testCategories.setLogin(dto.getLogin());
        testCategories.setCreatedBy(dto.getCreatedBy());
        testCategories.setCreatedDate(dto.getCreatedDate());
        testCategories.setLastModifiedBy(dto.getLastModifiedBy());
        testCategories.setLastModifiedDate(dto.getLastModifiedDate());
        testCategories.equipment(equipment);
        testCategories.parentTestCategory(parentTestCategory);
        testCategories.user(user);

        return testCategories;
    }

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
