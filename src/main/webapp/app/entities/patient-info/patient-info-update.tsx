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
import { IPatientInfo } from 'app/shared/model/patient-info.model';
import { getEntity, updateEntity, createEntity, reset } from './patient-info.reducer';

export const PatientInfoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employee.entities);
  const patientInfoEntity = useAppSelector(state => state.patientInfo.entity);
  const loading = useAppSelector(state => state.patientInfo.loading);
  const updating = useAppSelector(state => state.patientInfo.updating);
  const updateSuccess = useAppSelector(state => state.patientInfo.updateSuccess);

  const handleClose = () => {
    navigate('/patient-info');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
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
    if (values.age !== undefined && typeof values.age !== 'number') {
      values.age = Number(values.age);
    }
    if (values.mobile !== undefined && typeof values.mobile !== 'number') {
      values.mobile = Number(values.mobile);
    }

    const entity = {
      ...patientInfoEntity,
      ...values,
      employeeId: employees.find(it => it.id.toString() === values.employeeId.toString()),
      employeeHis: employees.find(it => it.id.toString() === values.employeeHis.toString()),
      employeeServiceNo: employees.find(it => it.id.toString() === values.employeeServiceNo.toString()),
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
          ...patientInfoEntity,
          employeeId: patientInfoEntity?.employeeId?.id,
          employeeHis: patientInfoEntity?.employeeHis?.id,
          employeeServiceNo: patientInfoEntity?.employeeServiceNo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="radiologyHealthCareApp.patientInfo.home.createOrEditLabel" data-cy="PatientInfoCreateUpdateHeading">
            Create or edit a Patient Info
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="patient-info-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Name" id="patient-info-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Age" id="patient-info-age" name="age" data-cy="age" type="text" />
              <ValidatedField label="Gender" id="patient-info-gender" name="gender" data-cy="gender" type="text" />
              <ValidatedField label="Date Of Birth" id="patient-info-dateOfBirth" name="dateOfBirth" data-cy="dateOfBirth" type="text" />
              <ValidatedField label="Mobile" id="patient-info-mobile" name="mobile" data-cy="mobile" type="text" />
              <ValidatedField label="Relation" id="patient-info-relation" name="relation" data-cy="relation" type="text" />
              <ValidatedField id="patient-info-employeeId" name="employeeId" data-cy="employeeId" label="Employee Id" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="patient-info-employeeHis" name="employeeHis" data-cy="employeeHis" label="Employee His" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.his}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="patient-info-employeeServiceNo"
                name="employeeServiceNo"
                data-cy="employeeServiceNo"
                label="Employee Service No"
                type="select"
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.serviceNo}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/patient-info" replace color="info">
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

export default PatientInfoUpdate;
