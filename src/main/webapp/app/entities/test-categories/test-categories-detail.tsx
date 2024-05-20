import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './test-categories.reducer';

export const TestCategoriesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const testCategoriesEntity = useAppSelector(state => state.testCategories.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="testCategoriesDetailsHeading">Test Categories</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{testCategoriesEntity.id}</dd>
          <dt>
            <span id="testName">Test Name</span>
          </dt>
          <dd>{testCategoriesEntity.testName}</dd>
          <dt>
            <span id="testDuration">Test Duration</span>
          </dt>
          <dd>{testCategoriesEntity.testDuration}</dd>
          <dt>
            <span id="patientReport">Patient Report</span>
          </dt>
          <dd>{testCategoriesEntity.patientReport}</dd>
          <dt>Equipment</dt>
          <dd>{testCategoriesEntity.equipment ? testCategoriesEntity.equipment.id : ''}</dd>
          <dt>Parent Test Category</dt>
          <dd>{testCategoriesEntity.parentTestCategory ? testCategoriesEntity.parentTestCategory.id : ''}</dd>
          <dt>User</dt>
          <dd>{testCategoriesEntity.user ? testCategoriesEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/test-categories" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/test-categories/${testCategoriesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TestCategoriesDetail;
