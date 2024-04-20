import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IOfficeTimings } from 'app/shared/model/office-timings.model';
import { getEntity, updateEntity, createEntity, reset } from './office-timings.reducer';

export const OfficeTimingsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const officeTimingsEntity = useAppSelector(state => state.officeTimings.entity);
  const loading = useAppSelector(state => state.officeTimings.loading);
  const updating = useAppSelector(state => state.officeTimings.updating);
  const updateSuccess = useAppSelector(state => state.officeTimings.updateSuccess);

  const handleClose = () => {
    navigate('/office-timings');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

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
    values.shiftStart = convertDateTimeToServer(values.shiftStart);
    values.shiftEnd = convertDateTimeToServer(values.shiftEnd);

    const entity = {
      ...officeTimingsEntity,
      ...values,
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
          shiftStart: displayDefaultDateTime(),
          shiftEnd: displayDefaultDateTime(),
        }
      : {
          ...officeTimingsEntity,
          shiftStart: convertDateTimeFromServer(officeTimingsEntity.shiftStart),
          shiftEnd: convertDateTimeFromServer(officeTimingsEntity.shiftEnd),
          user: officeTimingsEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="radiologyHealthCareApp.officeTimings.home.createOrEditLabel" data-cy="OfficeTimingsCreateUpdateHeading">
            Create or edit a Office Timings
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
                <ValidatedField name="id" required readOnly id="office-timings-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Date" id="office-timings-date" name="date" data-cy="date" type="date" />
              <ValidatedField
                label="Shift Start"
                id="office-timings-shiftStart"
                name="shiftStart"
                data-cy="shiftStart"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Shift End"
                id="office-timings-shiftEnd"
                name="shiftEnd"
                data-cy="shiftEnd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Default Timings"
                id="office-timings-defaultTimings"
                name="defaultTimings"
                data-cy="defaultTimings"
                check
                type="checkbox"
              />
              <ValidatedField id="office-timings-user" name="user" data-cy="user" label="User" type="select" required>
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/office-timings" replace color="info">
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

export default OfficeTimingsUpdate;
