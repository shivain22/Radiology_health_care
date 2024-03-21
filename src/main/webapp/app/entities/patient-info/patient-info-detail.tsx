import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './patient-info.reducer';

export const PatientInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const patientInfoEntity = useAppSelector(state => state.patientInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="patientInfoDetailsHeading">Patient Info</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{patientInfoEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{patientInfoEntity.name}</dd>
          <dt>
            <span id="age">Age</span>
          </dt>
          <dd>{patientInfoEntity.age}</dd>
          <dt>
            <span id="gender">Gender</span>
          </dt>
          <dd>{patientInfoEntity.gender}</dd>
          <dt>
            <span id="dateOfBirth">Date Of Birth</span>
          </dt>
          <dd>{patientInfoEntity.dateOfBirth}</dd>
          <dt>
            <span id="mobile">Mobile</span>
          </dt>
          <dd>{patientInfoEntity.mobile}</dd>
          <dt>
            <span id="relation">Relation</span>
          </dt>
          <dd>{patientInfoEntity.relation}</dd>
          <dt>Employee Id</dt>
          <dd>{patientInfoEntity.employeeId ? patientInfoEntity.employeeId.id : ''}</dd>
          <dt>Employee His</dt>
          <dd>{patientInfoEntity.employeeHis ? patientInfoEntity.employeeHis.his : ''}</dd>
          <dt>Employee Service No</dt>
          <dd>{patientInfoEntity.employeeServiceNo ? patientInfoEntity.employeeServiceNo.serviceNo : ''}</dd>
        </dl>
        <Button tag={Link} to="/patient-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/patient-info/${patientInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PatientInfoDetail;
