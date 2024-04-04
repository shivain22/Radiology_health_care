import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmpService } from 'app/shared/model/emp-service.model';
import { getEntities as getEmpServices } from 'app/entities/emp-service/emp-service.reducer';

import { IRank } from 'app/shared/model/rank.model';
import { rankDivisions } from 'app/shared/model/enumerations/rank-divisions.model';
import { getEntity, updateEntity, createEntity, reset } from './rank.reducer';

export const RankUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const empServices = useAppSelector(state => state.empService.entities);

  const rankEntity = useAppSelector(state => state.rank.entity);
  const loading = useAppSelector(state => state.rank.loading);
  const updating = useAppSelector(state => state.rank.updating);
  const updateSuccess = useAppSelector(state => state.rank.updateSuccess);
  const rankDivisionsValues = Object.keys(rankDivisions);

  const handleClose = () => {
    navigate('/rank');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getEmpServices({}));
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
      ...rankEntity,
      ...values,
      empService: empServices.find(it => it.id.toString() === values.empService.toString()),
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
          division: 'OTHERS',
          ...rankEntity,
          empService: rankEntity?.empService?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="radiologyHealthCareApp.rank.home.createOrEditLabel" data-cy="RankCreateUpdateHeading">
            Create or edit a Rank
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="rank-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="rank-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Short Name" id="rank-shortName" name="shortName" data-cy="shortName" type="text" />
              <ValidatedField label="Division" id="rank-division" name="division" data-cy="division" type="select">
                {rankDivisionsValues.map(rankDivisions => (
                  <option value={rankDivisions} key={rankDivisions}>
                    {rankDivisions}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="rank-empService" name="empService" data-cy="empService" label="Emp Service" type="select" required>
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
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rank" replace color="info">
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

export default RankUpdate;
