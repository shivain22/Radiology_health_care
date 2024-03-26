package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.EmpService;
import com.radiology.health.care.domain.Employee;
import com.radiology.health.care.domain.Rank;
import com.radiology.health.care.domain.Unit;
import com.radiology.health.care.domain.User;
import com.radiology.health.care.service.dto.EmpServiceDTO;
import com.radiology.health.care.service.dto.EmployeeDTO;
import com.radiology.health.care.service.dto.RankDTO;
import com.radiology.health.care.service.dto.UnitDTO;
import com.radiology.health.care.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "rankId", source = "rank.id")
    @Mapping(target = "empServiceId", source = "empService.id")
    @Mapping(target = "unitId", source = "unit.id")
    @Mapping(target = "userId", source = "user.id")
    EmployeeDTO toDto(Employee s);

    @Mapping(target = "rank.id", source = "rankId")
    @Mapping(target = "empService.id", source = "empServiceId")
    @Mapping(target = "unit.id", source = "unitId")
    @Mapping(target = "user.id", source = "userId")
    @Override
    default Employee toEntity(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();
        Unit unit = new Unit();
        EmpService empService = new EmpService();
        Rank rank = new Rank();
        User user = new User();

        unit.setId(dto.getUnitId());
        rank.setId(dto.getRankId());
        user.setId(dto.getUserId());
        empService.setId(dto.getEmpServiceId());

        employee.setCreatedBy(dto.getCreatedBy());
        employee.setLogin(dto.getLogin());
        employee.setCreatedDate(dto.getCreatedDate());
        employee.setLastModifiedBy(dto.getLastModifiedBy());
        employee.setLastModifiedDate(dto.getLastModifiedDate());
        employee.setId(dto.getId());
        employee.setHis(dto.getHis());
        employee.setServiceNo(dto.getServiceNo());
        employee.setName(dto.getName());
        employee.setTechnician(dto.getTechnician());
        employee.unit(unit);
        employee.empService(empService);
        employee.rank(rank);
        employee.user(user);

        return employee;
    }

    @Named("rankId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RankDTO toDtoRankId(Rank rank);

    @Named("empServiceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpServiceDTO toDtoEmpServiceId(EmpService empService);

    @Named("unitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UnitDTO toDtoUnitId(Unit unit);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
