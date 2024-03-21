import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee.reducer';

export const EmployeeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeEntity = useAppSelector(state => state.employee.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeDetailsHeading">Employee</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{employeeEntity.name}</dd>
          <dt>
            <span id="technician">Technician</span>
          </dt>
          <dd>{employeeEntity.technician ? 'true' : 'false'}</dd>
          <dt>
            <span id="his">His</span>
          </dt>
          <dd>{employeeEntity.his}</dd>
          <dt>
            <span id="serviceNo">Service No</span>
          </dt>
          <dd>{employeeEntity.serviceNo}</dd>
          <dt>Rank</dt>
          <dd>{employeeEntity.rank ? employeeEntity.rank.id : ''}</dd>
          <dt>Emp Service</dt>
          <dd>{employeeEntity.empService ? employeeEntity.empService.id : ''}</dd>
          <dt>Unit</dt>
          <dd>{employeeEntity.unit ? employeeEntity.unit.id : ''}</dd>
          <dt>User</dt>
          <dd>{employeeEntity.user ? employeeEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeDetail;
