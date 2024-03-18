import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './test-timings.reducer';

export const TestTimingsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const testTimingsEntity = useAppSelector(state => state.testTimings.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="testTimingsDetailsHeading">Test Timings</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{testTimingsEntity.id}</dd>
          <dt>
            <span id="timings">Timings</span>
          </dt>
          <dd>{testTimingsEntity.timings}</dd>
          <dt>Test Catogories</dt>
          <dd>{testTimingsEntity.testCatogories ? testTimingsEntity.testCatogories.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/test-timings" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/test-timings/${testTimingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TestTimingsDetail;
