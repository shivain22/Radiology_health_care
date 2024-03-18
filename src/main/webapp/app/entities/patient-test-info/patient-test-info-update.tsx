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
import { ITestTimings } from 'app/shared/model/test-timings.model';
import { getEntities as getTestTimings } from 'app/entities/test-timings/test-timings.reducer';
import { IPatientTestInfo } from 'app/shared/model/patient-test-info.model';
import { getEntity, updateEntity, createEntity, reset } from './patient-test-info.reducer';

export const PatientTestInfoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const patientInfos = useAppSelector(state => state.patientInfo.entities);
  const testTimings = useAppSelector(state => state.testTimings.entities);
  const patientTestInfoEntity = useAppSelector(state => state.patientTestInfo.entity);
  const loading = useAppSelector(state => state.patientTestInfo.loading);
  const updating = useAppSelector(state => state.patientTestInfo.updating);
  const updateSuccess = useAppSelector(state => state.patientTestInfo.updateSuccess);

  const handleClose = () => {
    navigate('/patient-test-info');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPatientInfos({}));
    dispatch(getTestTimings({}));
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
      ...patientTestInfoEntity,
      ...values,
      patientInfo: patientInfos.find(it => it.id.toString() === values.patientInfo.toString()),
      testTimings: testTimings.find(it => it.id.toString() === values.testTimings.toString()),
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
          ...patientTestInfoEntity,
          patientInfo: patientTestInfoEntity?.patientInfo?.id,
          testTimings: patientTestInfoEntity?.testTimings?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="radiologyHealthCareApp.patientTestInfo.home.createOrEditLabel" data-cy="PatientTestInfoCreateUpdateHeading">
            Create or edit a Patient Test Info
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
                <ValidatedField name="id" required readOnly id="patient-test-info-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                id="patient-test-info-patientInfo"
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
                id="patient-test-info-testTimings"
                name="testTimings"
                data-cy="testTimings"
                label="Test Timings"
                type="select"
                required
              >
                <option value="" key="0" />
                {testTimings
                  ? testTimings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/patient-test-info" replace color="info">
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

export default PatientTestInfoUpdate;
