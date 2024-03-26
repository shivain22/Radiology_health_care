import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './emp-service.reducer';

export const EmpServiceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const empServiceEntity = useAppSelector(state => state.empService.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="empServiceDetailsHeading">Emp Service</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{empServiceEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{empServiceEntity.name}</dd>
          <dt>User</dt>
          <dd>{empServiceEntity.user ? empServiceEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/emp-service" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emp-service/${empServiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmpServiceDetail;
