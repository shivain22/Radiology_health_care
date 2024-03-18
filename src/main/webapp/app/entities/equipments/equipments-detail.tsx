import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './equipments.reducer';

export const EquipmentsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const equipmentsEntity = useAppSelector(state => state.equipments.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="equipmentsDetailsHeading">Equipments</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{equipmentsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{equipmentsEntity.name}</dd>
          <dt>Rooms</dt>
          <dd>{equipmentsEntity.rooms ? equipmentsEntity.rooms.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/equipments" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/equipments/${equipmentsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EquipmentsDetail;
