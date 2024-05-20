import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPatientInfo } from 'app/shared/model/patient-info.model';
import { getEntities as getPatientInfos } from 'app/entities/patient-info/patient-info.reducer';
import { ITestCategories } from 'app/shared/model/test-categories.model';
import { getEntities as getTestCategories } from 'app/entities/test-categories/test-categories.reducer';
import { IPatientTestTimings } from 'app/shared/model/patient-test-timings.model';
import { getEntity, updateEntity, createEntity, reset } from './patient-test-timings.reducer';

export const PatientTestTimingsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const patientInfos = useAppSelector(state => state.patientInfo.entities);
  const testCategories = useAppSelector(state => state.testCategories.entities);
  const patientTestTimingsEntity = useAppSelector(state => state.patientTestTimings.entity);
  const loading = useAppSelector(state => state.patientTestTimings.loading);
  const updating = useAppSelector(state => state.patientTestTimings.updating);
  const updateSuccess = useAppSelector(state => state.patientTestTimings.updateSuccess);

  const handleClose = () => {
    navigate('/patient-test-timings');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPatientInfos({}));
    dispatch(getTestCategories({}));
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
    values.endTime = convertDateTimeToServer(values.endTime);
    values.startTime = convertDateTimeToServer(values.startTime);

    const entity = {
      ...patientTestTimingsEntity,
      ...values,
      patientInfo: patientInfos.find(it => it.id.toString() === values.patientInfo.toString()),
      testCategories: testCategories.find(it => it.id.toString() === values.testCategories.toString()),
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
          endTime: displayDefaultDateTime(),
          startTime: displayDefaultDateTime(),
        }
      : {
          ...patientTestTimingsEntity,
          endTime: convertDateTimeFromServer(patientTestTimingsEntity.endTime),
          startTime: convertDateTimeFromServer(patientTestTimingsEntity.startTime),
          patientInfo: patientTestTimingsEntity?.patientInfo?.id,
          testCategories: patientTestTimingsEntity?.testCategories?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="radiologyHealthCareApp.patientTestTimings.home.createOrEditLabel" data-cy="PatientTestTimingsCreateUpdateHeading">
            Create or edit a Patient Test Timings
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
                <ValidatedField name="id" required readOnly id="patient-test-timings-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Priority" id="patient-test-timings-priority" name="priority" data-cy="priority" type="text" />
              <ValidatedField
                label="Clinical Note"
                id="patient-test-timings-clinicalNote"
                name="clinicalNote"
                data-cy="clinicalNote"
                type="text"
              />
              <ValidatedField
                label="Spcl Instruction"
                id="patient-test-timings-spclInstruction"
                name="spclInstruction"
                data-cy="spclInstruction"
                type="text"
              />
              <ValidatedField label="Status" id="patient-test-timings-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="End Time"
                id="patient-test-timings-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Start Time"
                id="patient-test-timings-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Recommended Doctor"
                id="patient-test-timings-recommendedDoctor"
                name="recommendedDoctor"
                data-cy="recommendedDoctor"
                type="text"
              />
              <ValidatedField
                id="patient-test-timings-patientInfo"
                name="patientInfo"
                data-cy="patientInfo"
                label="Patient Info"
                type="select"
                required
              >
                <option value="" key="0" />
                {patientInfos
                  ? patientInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="patient-test-timings-testCategories"
                name="testCategories"
                data-cy="testCategories"
                label="Test Categories"
                type="select"
              >
                <option value="" key="0" />
                {testCategories
                  ? testCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/patient-test-timings" replace color="info">
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

export default PatientTestTimingsUpdate;
