import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './techician-equipment-mapping.reducer';

export const TechicianEquipmentMappingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const techicianEquipmentMappingEntity = useAppSelector(state => state.techicianEquipmentMapping.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="techicianEquipmentMappingDetailsHeading">Techician Equipment Mapping</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{techicianEquipmentMappingEntity.id}</dd>
          <dt>
            <span id="dateTime">Date Time</span>
          </dt>
          <dd>{techicianEquipmentMappingEntity.dateTime}</dd>
          <dt>Employee</dt>
          <dd>{techicianEquipmentMappingEntity.employee ? techicianEquipmentMappingEntity.employee.id : ''}</dd>
          <dt>Equipments</dt>
          <dd>{techicianEquipmentMappingEntity.equipments ? techicianEquipmentMappingEntity.equipments.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/techician-equipment-mapping" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/techician-equipment-mapping/${techicianEquipmentMappingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TechicianEquipmentMappingDetail;
