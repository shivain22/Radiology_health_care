import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rank.reducer';

export const RankDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rankEntity = useAppSelector(state => state.rank.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rankDetailsHeading">Rank</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{rankEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{rankEntity.name}</dd>
          <dt>Emp Service</dt>
          <dd>{rankEntity.empService ? rankEntity.empService.id : ''}</dd>
          <dt>User</dt>
          <dd>{rankEntity.user ? rankEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rank" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rank/${rankEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RankDetail;
