import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './office-timings.reducer';

export const OfficeTimingsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const officeTimingsEntity = useAppSelector(state => state.officeTimings.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="officeTimingsDetailsHeading">Office Timings</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{officeTimingsEntity.id}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            {officeTimingsEntity.date ? <TextFormat value={officeTimingsEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="shiftStart">Shift Start</span>
          </dt>
          <dd>
            {officeTimingsEntity.shiftStart ? (
              <TextFormat value={officeTimingsEntity.shiftStart} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="shiftEnd">Shift End</span>
          </dt>
          <dd>
            {officeTimingsEntity.shiftEnd ? <TextFormat value={officeTimingsEntity.shiftEnd} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="defaultTimings">Default Timings</span>
          </dt>
          <dd>{officeTimingsEntity.defaultTimings ? 'true' : 'false'}</dd>
          <dt>User</dt>
          <dd>{officeTimingsEntity.user ? officeTimingsEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/office-timings" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/office-timings/${officeTimingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OfficeTimingsDetail;
