import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './patient-test-timings.reducer';

export const PatientTestTimingsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const patientTestTimingsEntity = useAppSelector(state => state.patientTestTimings.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="patientTestTimingsDetailsHeading">Patient Test Timings</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{patientTestTimingsEntity.id}</dd>
          <dt>
            <span id="priority">Priority</span>
          </dt>
          <dd>{patientTestTimingsEntity.priority}</dd>
          <dt>
            <span id="clinicalNote">Clinical Note</span>
          </dt>
          <dd>{patientTestTimingsEntity.clinicalNote}</dd>
          <dt>
            <span id="spclInstruction">Spcl Instruction</span>
          </dt>
          <dd>{patientTestTimingsEntity.spclInstruction}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{patientTestTimingsEntity.status}</dd>
          <dt>
            <span id="startTiming">Start Timing</span>
          </dt>
          <dd>
            {patientTestTimingsEntity.startTiming ? (
              <TextFormat value={patientTestTimingsEntity.startTiming} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">End Time</span>
          </dt>
          <dd>
            {patientTestTimingsEntity.endTime ? (
              <TextFormat value={patientTestTimingsEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Patient Info</dt>
          <dd>{patientTestTimingsEntity.patientInfo ? patientTestTimingsEntity.patientInfo.id : ''}</dd>
          <dt>Test Categories</dt>
          <dd>{patientTestTimingsEntity.testCategories ? patientTestTimingsEntity.testCategories.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/patient-test-timings" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/patient-test-timings/${patientTestTimingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PatientTestTimingsDetail;
