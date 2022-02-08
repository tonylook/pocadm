import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './vessel-voyage-contract.reducer';

export const VesselVoyageContractDeleteDialog = (props: RouteComponentProps<{ id: string }>) => {
  const [loadModal, setLoadModal] = useState(false);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
    setLoadModal(true);
  }, []);

  const vesselVoyageContractEntity = useAppSelector(state => state.vesselVoyageContract.entity);
  const updateSuccess = useAppSelector(state => state.vesselVoyageContract.updateSuccess);

  const handleClose = () => {
    props.history.push('/vessel-voyage-contract');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(vesselVoyageContractEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="vesselVoyageContractDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="pocadmApp.vesselVoyageContract.delete.question">
        <Translate contentKey="pocadmApp.vesselVoyageContract.delete.question" interpolate={{ id: vesselVoyageContractEntity.id }}>
          Are you sure you want to delete this VesselVoyageContract?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-vesselVoyageContract" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default VesselVoyageContractDeleteDialog;
