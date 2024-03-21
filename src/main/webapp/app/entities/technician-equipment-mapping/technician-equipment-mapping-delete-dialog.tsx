import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './technician-equipment-mapping.reducer';

export const TechnicianEquipmentMappingDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const technicianEquipmentMappingEntity = useAppSelector(state => state.technicianEquipmentMapping.entity);
  const updateSuccess = useAppSelector(state => state.technicianEquipmentMapping.updateSuccess);

  const handleClose = () => {
    navigate('/technician-equipment-mapping');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(technicianEquipmentMappingEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="technicianEquipmentMappingDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="radiologyHealthCareApp.technicianEquipmentMapping.delete.question">
        Are you sure you want to delete Technician Equipment Mapping {technicianEquipmentMappingEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button
          id="jhi-confirm-delete-technicianEquipmentMapping"
          data-cy="entityConfirmDeleteButton"
          color="danger"
          onClick={confirmDelete}
        >
          <FontAwesomeIcon icon="trash" />
          &nbsp; Delete
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default TechnicianEquipmentMappingDeleteDialog;
