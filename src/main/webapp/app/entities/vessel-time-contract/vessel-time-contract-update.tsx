import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './vessel-time-contract.reducer';
import { IVesselTimeContract } from 'app/shared/model/vessel-time-contract.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VesselTimeContractUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const vesselTimeContractEntity = useAppSelector(state => state.vesselTimeContract.entity);
  const loading = useAppSelector(state => state.vesselTimeContract.loading);
  const updating = useAppSelector(state => state.vesselTimeContract.updating);
  const updateSuccess = useAppSelector(state => state.vesselTimeContract.updateSuccess);
  const handleClose = () => {
    props.history.push('/vessel-time-contract');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...vesselTimeContractEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...vesselTimeContractEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="pocadmApp.vesselTimeContract.home.createOrEditLabel" data-cy="VesselTimeContractCreateUpdateHeading">
            <Translate contentKey="pocadmApp.vesselTimeContract.home.createOrEditLabel">Create or edit a VesselTimeContract</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="vessel-time-contract-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('pocadmApp.vesselTimeContract.holds')}
                id="vessel-time-contract-holds"
                name="holds"
                data-cy="holds"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.vesselTimeContract.holdCapacity')}
                id="vessel-time-contract-holdCapacity"
                name="holdCapacity"
                data-cy="holdCapacity"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.vesselTimeContract.period')}
                id="vessel-time-contract-period"
                name="period"
                data-cy="period"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.vesselTimeContract.costPerDay')}
                id="vessel-time-contract-costPerDay"
                name="costPerDay"
                data-cy="costPerDay"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vessel-time-contract" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default VesselTimeContractUpdate;
