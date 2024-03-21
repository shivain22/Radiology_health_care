import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './unit.reducer';

export const UnitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const unitEntity = useAppSelector(state => state.unit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="unitDetailsHeading">Unit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{unitEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{unitEntity.name}</dd>
          <dt>Emp Service</dt>
          <dd>{unitEntity.empService ? unitEntity.empService.id : ''}</dd>
          <dt>User</dt>
          <dd>{unitEntity.user ? unitEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/unit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/unit/${unitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UnitDetail;
