package com.radiology.health.care.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.care.domain.Employee} entity. This class is used
 * in {@link com.radiology.health.care.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter technician;

    private StringFilter his;

    private StringFilter serviceNo;

    private LongFilter rankId;

    private LongFilter empServiceId;

    private LongFilter unitId;

    private LongFilter technicianEquipmentMappingId;

    private LongFilter patientInfoEmployeeIdId;

    private LongFilter patientInfoEmployeeHisId;

    private LongFilter patientInfoEmployeeServiceNoId;

    private Boolean distinct;

    public EmployeeCriteria() {}

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.technician = other.technician == null ? null : other.technician.copy();
        this.his = other.his == null ? null : other.his.copy();
        this.serviceNo = other.serviceNo == null ? null : other.serviceNo.copy();
        this.rankId = other.rankId == null ? null : other.rankId.copy();
        this.empServiceId = other.empServiceId == null ? null : other.empServiceId.copy();
        this.unitId = other.unitId == null ? null : other.unitId.copy();
        this.technicianEquipmentMappingId = other.technicianEquipmentMappingId == null ? null : other.technicianEquipmentMappingId.copy();
        this.patientInfoEmployeeIdId = other.patientInfoEmployeeIdId == null ? null : other.patientInfoEmployeeIdId.copy();
        this.patientInfoEmployeeHisId = other.patientInfoEmployeeHisId == null ? null : other.patientInfoEmployeeHisId.copy();
        this.patientInfoEmployeeServiceNoId =
            other.patientInfoEmployeeServiceNoId == null ? null : other.patientInfoEmployeeServiceNoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getTechnician() {
        return technician;
    }

    public BooleanFilter technician() {
        if (technician == null) {
            technician = new BooleanFilter();
        }
        return technician;
    }

    public void setTechnician(BooleanFilter technician) {
        this.technician = technician;
    }

    public StringFilter getHis() {
        return his;
    }

    public StringFilter his() {
        if (his == null) {
            his = new StringFilter();
        }
        return his;
    }

    public void setHis(StringFilter his) {
        this.his = his;
    }

    public StringFilter getServiceNo() {
        return serviceNo;
    }

    public StringFilter serviceNo() {
        if (serviceNo == null) {
            serviceNo = new StringFilter();
        }
        return serviceNo;
    }

    public void setServiceNo(StringFilter serviceNo) {
        this.serviceNo = serviceNo;
    }

    public LongFilter getRankId() {
        return rankId;
    }

    public LongFilter rankId() {
        if (rankId == null) {
            rankId = new LongFilter();
        }
        return rankId;
    }

    public void setRankId(LongFilter rankId) {
        this.rankId = rankId;
    }

    public LongFilter getEmpServiceId() {
        return empServiceId;
    }

    public LongFilter empServiceId() {
        if (empServiceId == null) {
            empServiceId = new LongFilter();
        }
        return empServiceId;
    }

    public void setEmpServiceId(LongFilter empServiceId) {
        this.empServiceId = empServiceId;
    }

    public LongFilter getUnitId() {
        return unitId;
    }

    public LongFilter unitId() {
        if (unitId == null) {
            unitId = new LongFilter();
        }
        return unitId;
    }

    public void setUnitId(LongFilter unitId) {
        this.unitId = unitId;
    }

    public LongFilter getTechnicianEquipmentMappingId() {
        return technicianEquipmentMappingId;
    }

    public LongFilter technicianEquipmentMappingId() {
        if (technicianEquipmentMappingId == null) {
            technicianEquipmentMappingId = new LongFilter();
        }
        return technicianEquipmentMappingId;
    }

    public void setTechnicianEquipmentMappingId(LongFilter technicianEquipmentMappingId) {
        this.technicianEquipmentMappingId = technicianEquipmentMappingId;
    }

    public LongFilter getPatientInfoEmployeeIdId() {
        return patientInfoEmployeeIdId;
    }

    public LongFilter patientInfoEmployeeIdId() {
        if (patientInfoEmployeeIdId == null) {
            patientInfoEmployeeIdId = new LongFilter();
        }
        return patientInfoEmployeeIdId;
    }

    public void setPatientInfoEmployeeIdId(LongFilter patientInfoEmployeeIdId) {
        this.patientInfoEmployeeIdId = patientInfoEmployeeIdId;
    }

    public LongFilter getPatientInfoEmployeeHisId() {
        return patientInfoEmployeeHisId;
    }

    public LongFilter patientInfoEmployeeHisId() {
        if (patientInfoEmployeeHisId == null) {
            patientInfoEmployeeHisId = new LongFilter();
        }
        return patientInfoEmployeeHisId;
    }

    public void setPatientInfoEmployeeHisId(LongFilter patientInfoEmployeeHisId) {
        this.patientInfoEmployeeHisId = patientInfoEmployeeHisId;
    }

    public LongFilter getPatientInfoEmployeeServiceNoId() {
        return patientInfoEmployeeServiceNoId;
    }

    public LongFilter patientInfoEmployeeServiceNoId() {
        if (patientInfoEmployeeServiceNoId == null) {
            patientInfoEmployeeServiceNoId = new LongFilter();
        }
        return patientInfoEmployeeServiceNoId;
    }

    public void setPatientInfoEmployeeServiceNoId(LongFilter patientInfoEmployeeServiceNoId) {
        this.patientInfoEmployeeServiceNoId = patientInfoEmployeeServiceNoId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(technician, that.technician) &&
            Objects.equals(his, that.his) &&
            Objects.equals(serviceNo, that.serviceNo) &&
            Objects.equals(rankId, that.rankId) &&
            Objects.equals(empServiceId, that.empServiceId) &&
            Objects.equals(unitId, that.unitId) &&
            Objects.equals(technicianEquipmentMappingId, that.technicianEquipmentMappingId) &&
            Objects.equals(patientInfoEmployeeIdId, that.patientInfoEmployeeIdId) &&
            Objects.equals(patientInfoEmployeeHisId, that.patientInfoEmployeeHisId) &&
            Objects.equals(patientInfoEmployeeServiceNoId, that.patientInfoEmployeeServiceNoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            technician,
            his,
            serviceNo,
            rankId,
            empServiceId,
            unitId,
            technicianEquipmentMappingId,
            patientInfoEmployeeIdId,
            patientInfoEmployeeHisId,
            patientInfoEmployeeServiceNoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (technician != null ? "technician=" + technician + ", " : "") +
            (his != null ? "his=" + his + ", " : "") +
            (serviceNo != null ? "serviceNo=" + serviceNo + ", " : "") +
            (rankId != null ? "rankId=" + rankId + ", " : "") +
            (empServiceId != null ? "empServiceId=" + empServiceId + ", " : "") +
            (unitId != null ? "unitId=" + unitId + ", " : "") +
            (technicianEquipmentMappingId != null ? "technicianEquipmentMappingId=" + technicianEquipmentMappingId + ", " : "") +
            (patientInfoEmployeeIdId != null ? "patientInfoEmployeeIdId=" + patientInfoEmployeeIdId + ", " : "") +
            (patientInfoEmployeeHisId != null ? "patientInfoEmployeeHisId=" + patientInfoEmployeeHisId + ", " : "") +
            (patientInfoEmployeeServiceNoId != null ? "patientInfoEmployeeServiceNoId=" + patientInfoEmployeeServiceNoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
