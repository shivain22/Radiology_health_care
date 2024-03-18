import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './techician-equipment-mapping.reducer';

export const TechicianEquipmentMappingDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const techicianEquipmentMappingEntity = useAppSelector(state => state.techicianEquipmentMapping.entity);
  const updateSuccess = useAppSelector(state => state.techicianEquipmentMapping.updateSuccess);

  const handleClose = () => {
    navigate('/techician-equipment-mapping');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(techicianEquipmentMappingEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="techicianEquipmentMappingDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="radiologyHealthCareApp.techicianEquipmentMapping.delete.question">
        Are you sure you want to delete Techician Equipment Mapping {techicianEquipmentMappingEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button
          id="jhi-confirm-delete-techicianEquipmentMapping"
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

export default TechicianEquipmentMappingDeleteDialog;
