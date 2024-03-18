import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEquipments } from 'app/shared/model/equipments.model';
import { getEntities as getEquipments } from 'app/entities/equipments/equipments.reducer';
import { getEntities as getTestCatogories } from 'app/entities/test-catogories/test-catogories.reducer';
import { ITestCatogories } from 'app/shared/model/test-catogories.model';
import { getEntity, updateEntity, createEntity, reset } from './test-catogories.reducer';

export const TestCatogoriesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const equipments = useAppSelector(state => state.equipments.entities);
  const testCatogories = useAppSelector(state => state.testCatogories.entities);
  const testCatogoriesEntity = useAppSelector(state => state.testCatogories.entity);
  const loading = useAppSelector(state => state.testCatogories.loading);
  const updating = useAppSelector(state => state.testCatogories.updating);
  const updateSuccess = useAppSelector(state => state.testCatogories.updateSuccess);

  const handleClose = () => {
    navigate('/test-catogories');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getEquipments({}));
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
      ...testCatogoriesEntity,
      ...values,
      equipments: equipments.find(it => it.id.toString() === values.equipments.toString()),
      testCatogories_parent: testCatogories.find(it => it.id.toString() === values.testCatogories_parent.toString()),
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
          ...testCatogoriesEntity,
          equipments: testCatogoriesEntity?.equipments?.id,
          testCatogories_parent: testCatogoriesEntity?.testCatogories_parent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="radiologyHealthCareApp.testCatogories.home.createOrEditLabel" data-cy="TestCatogoriesCreateUpdateHeading">
            Create or edit a Test Catogories
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
                <ValidatedField name="id" required readOnly id="test-catogories-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Name"
                id="test-catogories-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                id="test-catogories-equipments"
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
              <ValidatedField
                id="test-catogories-testCatogories_parent"
                name="testCatogories_parent"
                data-cy="testCatogories_parent"
                label="Test Catogories Parent"
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/test-catogories" replace color="info">
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

export default TestCatogoriesUpdate;
