import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IEquipments } from 'app/shared/model/equipments.model';
import { getEntities as getEquipments } from 'app/entities/equipments/equipments.reducer';
import { ITechicianEquipmentMapping } from 'app/shared/model/techician-equipment-mapping.model';
import { getEntity, updateEntity, createEntity, reset } from './techician-equipment-mapping.reducer';

export const TechicianEquipmentMappingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employee.entities);
  const equipments = useAppSelector(state => state.equipments.entities);
  const techicianEquipmentMappingEntity = useAppSelector(state => state.techicianEquipmentMapping.entity);
  const loading = useAppSelector(state => state.techicianEquipmentMapping.loading);
  const updating = useAppSelector(state => state.techicianEquipmentMapping.updating);
  const updateSuccess = useAppSelector(state => state.techicianEquipmentMapping.updateSuccess);

  const handleClose = () => {
    navigate('/techician-equipment-mapping');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
    dispatch(getEquipments({}));
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

    const entity = {
      ...techicianEquipmentMappingEntity,
      ...values,
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
      equipments: equipments.find(it => it.id.toString() === values.equipments.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...techicianEquipmentMappingEntity,
          employee: techicianEquipmentMappingEntity?.employee?.id,
          equipments: techicianEquipmentMappingEntity?.equipments?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="radiologyHealthCareApp.techicianEquipmentMapping.home.createOrEditLabel"
            data-cy="TechicianEquipmentMappingCreateUpdateHeading"
          >
            Create or edit a Techician Equipment Mapping
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
                <ValidatedField name="id" required readOnly id="techician-equipment-mapping-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Date Time"
                id="techician-equipment-mapping-dateTime"
                name="dateTime"
                data-cy="dateTime"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                id="techician-equipment-mapping-employee"
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
              <ValidatedField
                id="techician-equipment-mapping-equipments"
                name="equipments"
                data-cy="equipments"
                label="Equipments"
                type="select"
                required
              >
                <option value="" key="0" />
                {equipments
                  ? equipments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/techician-equipment-mapping" replace color="info">
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

export default TechicianEquipmentMappingUpdate;
