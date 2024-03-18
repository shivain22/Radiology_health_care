package com.radiology.health.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.app.domain.Employee} entity. This class is used
 * in {@link com.radiology.health.app.web.rest.EmployeeResource} to receive all the possible filtering options from
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

    private StringFilter his;

    private StringFilter serviceNo;

    private StringFilter name;

    private IntegerFilter technician;

    private LongFilter unitId;

    private LongFilter servicesId;

    private LongFilter rankId;

    private LongFilter patientInfoId;

    private LongFilter techicianEquipmentMappingId;

    private Boolean distinct;

    public EmployeeCriteria() {}

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.his = other.his == null ? null : other.his.copy();
        this.serviceNo = other.serviceNo == null ? null : other.serviceNo.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.technician = other.technician == null ? null : other.technician.copy();
        this.unitId = other.unitId == null ? null : other.unitId.copy();
        this.servicesId = other.servicesId == null ? null : other.servicesId.copy();
        this.rankId = other.rankId == null ? null : other.rankId.copy();
        this.patientInfoId = other.patientInfoId == null ? null : other.patientInfoId.copy();
        this.techicianEquipmentMappingId = other.techicianEquipmentMappingId == null ? null : other.techicianEquipmentMappingId.copy();
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

    public IntegerFilter getTechnician() {
        return technician;
    }

    public IntegerFilter technician() {
        if (technician == null) {
            technician = new IntegerFilter();
        }
        return technician;
    }

    public void setTechnician(IntegerFilter technician) {
        this.technician = technician;
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

    public LongFilter getServicesId() {
        return servicesId;
    }

    public LongFilter servicesId() {
        if (servicesId == null) {
            servicesId = new LongFilter();
        }
        return servicesId;
    }

    public void setServicesId(LongFilter servicesId) {
        this.servicesId = servicesId;
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

    public LongFilter getPatientInfoId() {
        return patientInfoId;
    }

    public LongFilter patientInfoId() {
        if (patientInfoId == null) {
            patientInfoId = new LongFilter();
        }
        return patientInfoId;
    }

    public void setPatientInfoId(LongFilter patientInfoId) {
        this.patientInfoId = patientInfoId;
    }

    public LongFilter getTechicianEquipmentMappingId() {
        return techicianEquipmentMappingId;
    }

    public LongFilter techicianEquipmentMappingId() {
        if (techicianEquipmentMappingId == null) {
            techicianEquipmentMappingId = new LongFilter();
        }
        return techicianEquipmentMappingId;
    }

    public void setTechicianEquipmentMappingId(LongFilter techicianEquipmentMappingId) {
        this.techicianEquipmentMappingId = techicianEquipmentMappingId;
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
            Objects.equals(his, that.his) &&
            Objects.equals(serviceNo, that.serviceNo) &&
            Objects.equals(name, that.name) &&
            Objects.equals(technician, that.technician) &&
            Objects.equals(unitId, that.unitId) &&
            Objects.equals(servicesId, that.servicesId) &&
            Objects.equals(rankId, that.rankId) &&
            Objects.equals(patientInfoId, that.patientInfoId) &&
            Objects.equals(techicianEquipmentMappingId, that.techicianEquipmentMappingId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            his,
            serviceNo,
            name,
            technician,
            unitId,
            servicesId,
            rankId,
            patientInfoId,
            techicianEquipmentMappingId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (his != null ? "his=" + his + ", " : "") +
            (serviceNo != null ? "serviceNo=" + serviceNo + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (technician != null ? "technician=" + technician + ", " : "") +
            (unitId != null ? "unitId=" + unitId + ", " : "") +
            (servicesId != null ? "servicesId=" + servicesId + ", " : "") +
            (rankId != null ? "rankId=" + rankId + ", " : "") +
            (patientInfoId != null ? "patientInfoId=" + patientInfoId + ", " : "") +
            (techicianEquipmentMappingId != null ? "techicianEquipmentMappingId=" + techicianEquipmentMappingId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
