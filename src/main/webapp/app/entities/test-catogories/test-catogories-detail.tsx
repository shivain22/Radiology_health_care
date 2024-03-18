import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './test-catogories.reducer';

export const TestCatogoriesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const testCatogoriesEntity = useAppSelector(state => state.testCatogories.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="testCatogoriesDetailsHeading">Test Catogories</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{testCatogoriesEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{testCatogoriesEntity.name}</dd>
          <dt>Equipments</dt>
          <dd>{testCatogoriesEntity.equipments ? testCatogoriesEntity.equipments.id : ''}</dd>
          <dt>Test Catogories Parent</dt>
          <dd>{testCatogoriesEntity.testCatogories_parent ? testCatogoriesEntity.testCatogories_parent.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/test-catogories" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/test-catogories/${testCatogoriesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TestCatogoriesDetail;
