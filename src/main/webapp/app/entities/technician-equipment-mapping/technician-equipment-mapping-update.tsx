import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEquipment } from 'app/shared/model/equipment.model';
import { getEntities as getEquipment } from 'app/entities/equipment/equipment.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ITechnicianEquipmentMapping } from 'app/shared/model/technician-equipment-mapping.model';
import { getEntity, updateEntity, createEntity, reset } from './technician-equipment-mapping.reducer';

export const TechnicianEquipmentMappingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const equipment = useAppSelector(state => state.equipment.entities);
  const employees = useAppSelector(state => state.employee.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const technicianEquipmentMappingEntity = useAppSelector(state => state.technicianEquipmentMapping.entity);
  const loading = useAppSelector(state => state.technicianEquipmentMapping.loading);
  const updating = useAppSelector(state => state.technicianEquipmentMapping.updating);
  const updateSuccess = useAppSelector(state => state.technicianEquipmentMapping.updateSuccess);

  const handleClose = () => {
    navigate('/technician-equipment-mapping');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getEquipment({}));
    dispatch(getEmployees({}));
    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.dateTime = convertDateTimeToServer(values.dateTime);

    const entity = {
      ...technicianEquipmentMappingEntity,
      ...values,
      equipment: equipment.find(it => it.id.toString() === values.equipment.toString()),
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dateTime: displayDefaultDateTime(),
        }
      : {
          ...technicianEquipmentMappingEntity,
          dateTime: convertDateTimeFromServer(technicianEquipmentMappingEntity.dateTime),
          equipment: technicianEquipmentMappingEntity?.equipment?.id,
          employee: technicianEquipmentMappingEntity?.employee?.id,
          user: technicianEquipmentMappingEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="radiologyHealthCareApp.technicianEquipmentMapping.home.createOrEditLabel"
            data-cy="TechnicianEquipmentMappingCreateUpdateHeading"
          >
            Create or edit a Technician Equipment Mapping
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="technician-equipment-mapping-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Date Time"
                id="technician-equipment-mapping-dateTime"
                name="dateTime"
                data-cy="dateTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                id="technician-equipment-mapping-equipment"
                name="equipment"
                data-cy="equipment"
                label="Equipment"
                type="select"
                required
              >
                <option value="" key="0" />
                {equipment
                  ? equipment.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="technician-equipment-mapping-employee"
                name="employee"
                data-cy="employee"
                label="Employee"
                type="select"
                required
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="technician-equipment-mapping-user" name="user" data-cy="user" label="User" type="select" required>
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/technician-equipment-mapping"
                replace
                color="info"
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TechnicianEquipmentMappingUpdate;
