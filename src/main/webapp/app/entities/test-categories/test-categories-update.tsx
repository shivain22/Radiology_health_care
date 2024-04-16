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
import { getEntities as getTestCategories } from 'app/entities/test-categories/test-categories.reducer';

import { ITestCategories } from 'app/shared/model/test-categories.model';
import { getEntity, updateEntity, createEntity, reset } from './test-categories.reducer';

export const TestCategoriesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const equipment = useAppSelector(state => state.equipment.entities);
  const testCategories = useAppSelector(state => state.testCategories.entities);

  const testCategoriesEntity = useAppSelector(state => state.testCategories.entity);
  const loading = useAppSelector(state => state.testCategories.loading);
  const updating = useAppSelector(state => state.testCategories.updating);
  const updateSuccess = useAppSelector(state => state.testCategories.updateSuccess);

  const handleClose = () => {
    navigate('/test-categories');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getEquipment({}));
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
    if (values.testDuration !== undefined && typeof values.testDuration !== 'number') {
      values.testDuration = Number(values.testDuration);
    }

    const entity = {
      ...testCategoriesEntity,
      ...values,
      equipment: equipment.find(it => it.id.toString() === values.equipment.toString()),
      parentTestCategory: testCategories.find(it => it.id.toString() === values.parentTestCategory.toString()),
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
          ...testCategoriesEntity,
          equipment: testCategoriesEntity?.equipment?.id,
          parentTestCategory: testCategoriesEntity?.parentTestCategory?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="radiologyHealthCareApp.testCategories.home.createOrEditLabel" data-cy="TestCategoriesCreateUpdateHeading">
            Create or edit a Test Categories
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
                <ValidatedField name="id" required readOnly id="test-categories-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Test Name"
                id="test-categories-testName"
                name="testName"
                data-cy="testName"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Test Duration"
                id="test-categories-testDuration"
                name="testDuration"
                data-cy="testDuration"
                type="text"
                placeholder={'in minutes'}
              />
              <ValidatedField id="test-categories-equipment" name="equipment" data-cy="equipment" label="Equipment" type="select" required>
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
                id="test-categories-parentTestCategory"
                name="parentTestCategory"
                data-cy="parentTestCategory"
                label="Parent Test Category"
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
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/test-categories" replace color="info">
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

export default TestCategoriesUpdate;
