import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './technician-equipment-mapping.reducer';

export const TechnicianEquipmentMappingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const technicianEquipmentMappingEntity = useAppSelector(state => state.technicianEquipmentMapping.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="technicianEquipmentMappingDetailsHeading">Technician Equipment Mapping</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{technicianEquipmentMappingEntity.id}</dd>
          <dt>
            <span id="dateTime">Date Time</span>
          </dt>
          <dd>
            {technicianEquipmentMappingEntity.dateTime ? (
              <TextFormat value={technicianEquipmentMappingEntity.dateTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Equipment</dt>
          <dd>{technicianEquipmentMappingEntity.equipment ? technicianEquipmentMappingEntity.equipment.id : ''}</dd>
          <dt>Employee</dt>
          <dd>{technicianEquipmentMappingEntity.employee ? technicianEquipmentMappingEntity.employee.id : ''}</dd>
          <dt>User</dt>
          <dd>{technicianEquipmentMappingEntity.user ? technicianEquipmentMappingEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/technician-equipment-mapping" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/technician-equipment-mapping/${technicianEquipmentMappingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TechnicianEquipmentMappingDetail;
