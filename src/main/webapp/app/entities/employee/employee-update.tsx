import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRank } from 'app/shared/model/rank.model';
import { getEntities as getRanks } from 'app/entities/rank/rank.reducer';
import { IEmpService } from 'app/shared/model/emp-service.model';
import { getEntities as getEmpServices } from 'app/entities/emp-service/emp-service.reducer';
import { IUnit } from 'app/shared/model/unit.model';
import { getEntities as getUnits } from 'app/entities/unit/unit.reducer';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntity, updateEntity, createEntity, reset } from './employee.reducer';

export const EmployeeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ranks = useAppSelector(state => state.rank.entities);
  const empServices = useAppSelector(state => state.empService.entities);
  const units = useAppSelector(state => state.unit.entities);

  const employeeEntity = useAppSelector(state => state.employee.entity);
  const loading = useAppSelector(state => state.employee.loading);
  const updating = useAppSelector(state => state.employee.updating);
  const updateSuccess = useAppSelector(state => state.employee.updateSuccess);

  const handleClose = () => {
    navigate('/employee');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getRanks({}));
    dispatch(getEmpServices({}));
    dispatch(getUnits({}));
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
      ...employeeEntity,
      ...values,
      rank: ranks.find(it => it.id.toString() === values.rank.toString()),
      empService: empServices.find(it => it.id.toString() === values.empService.toString()),
      unit: units.find(it => it.id.toString() === values.unit.toString()),
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
          ...employeeEntity,
          rank: employeeEntity?.rank?.id,
          empService: employeeEntity?.empService?.id,
          unit: employeeEntity?.unit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="radiologyHealthCareApp.employee.home.createOrEditLabel" data-cy="EmployeeCreateUpdateHeading">
            Create or edit a Employee
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="employee-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Name" id="employee-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Technician" id="employee-technician" name="technician" data-cy="technician" check type="checkbox" />
              <ValidatedField label="His" id="employee-his" name="his" data-cy="his" type="text" validate={{}} />
              <ValidatedField label="Service No" id="employee-serviceNo" name="serviceNo" data-cy="serviceNo" type="text" validate={{}} />
              <ValidatedField id="employee-rank" name="rank" data-cy="rank" label="Rank" type="select" required>
                <option value="" key="0" />
                {ranks
                  ? ranks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="employee-empService" name="empService" data-cy="empService" label="Emp Service" type="select" required>
                <option value="" key="0" />
                {empServices
                  ? empServices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="employee-unit" name="unit" data-cy="unit" label="Unit" type="select" required>
                <option value="" key="0" />
                {units
                  ? units.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee" replace color="info">
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

export default EmployeeUpdate;
