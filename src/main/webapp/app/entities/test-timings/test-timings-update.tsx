import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITestCatogories } from 'app/shared/model/test-catogories.model';
import { getEntities as getTestCatogories } from 'app/entities/test-catogories/test-catogories.reducer';
import { ITestTimings } from 'app/shared/model/test-timings.model';
import { getEntity, updateEntity, createEntity, reset } from './test-timings.reducer';

export const TestTimingsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const testCatogories = useAppSelector(state => state.testCatogories.entities);
  const testTimingsEntity = useAppSelector(state => state.testTimings.entity);
  const loading = useAppSelector(state => state.testTimings.loading);
  const updating = useAppSelector(state => state.testTimings.updating);
  const updateSuccess = useAppSelector(state => state.testTimings.updateSuccess);

  const handleClose = () => {
    navigate('/test-timings');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getTestCatogories({}));
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
      ...testTimingsEntity,
      ...values,
      testCatogories: testCatogories.find(it => it.id.toString() === values.testCatogories.toString()),
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
          ...testTimingsEntity,
          testCatogories: testTimingsEntity?.testCatogories?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="radiologyHealthCareApp.testTimings.home.createOrEditLabel" data-cy="TestTimingsCreateUpdateHeading">
            Create or edit a Test Timings
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="test-timings-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Timings"
                id="test-timings-timings"
                name="timings"
                data-cy="timings"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                id="test-timings-testCatogories"
                name="testCatogories"
                data-cy="testCatogories"
                label="Test Catogories"
                type="select"
                required
              >
                <option value="" key="0" />
                {testCatogories
                  ? testCatogories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/test-timings" replace color="info">
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

export default TestTimingsUpdate;
