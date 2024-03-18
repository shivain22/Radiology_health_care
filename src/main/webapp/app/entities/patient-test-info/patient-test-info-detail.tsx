import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './patient-test-info.reducer';

export const PatientTestInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const patientTestInfoEntity = useAppSelector(state => state.patientTestInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="patientTestInfoDetailsHeading">Patient Test Info</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{patientTestInfoEntity.id}</dd>
          <dt>Patient Info</dt>
          <dd>{patientTestInfoEntity.patientInfo ? patientTestInfoEntity.patientInfo.id : ''}</dd>
          <dt>Test Timings</dt>
          <dd>{patientTestInfoEntity.testTimings ? patientTestInfoEntity.testTimings.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/patient-test-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/patient-test-info/${patientTestInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PatientTestInfoDetail;
