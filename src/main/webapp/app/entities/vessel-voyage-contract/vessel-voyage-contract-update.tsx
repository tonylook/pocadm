import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './vessel-voyage-contract.reducer';
import { IVesselVoyageContract } from 'app/shared/model/vessel-voyage-contract.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VesselVoyageContractUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const vesselVoyageContractEntity = useAppSelector(state => state.vesselVoyageContract.entity);
  const loading = useAppSelector(state => state.vesselVoyageContract.loading);
  const updating = useAppSelector(state => state.vesselVoyageContract.updating);
  const updateSuccess = useAppSelector(state => state.vesselVoyageContract.updateSuccess);
  const handleClose = () => {
    props.history.push('/vessel-voyage-contract');
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
      ...vesselVoyageContractEntity,
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
          ...vesselVoyageContractEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="pocadmApp.vesselVoyageContract.home.createOrEditLabel" data-cy="VesselVoyageContractCreateUpdateHeading">
            <Translate contentKey="pocadmApp.vesselVoyageContract.home.createOrEditLabel">Create or edit a VesselVoyageContract</Translate>
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
                  id="vessel-voyage-contract-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('pocadmApp.vesselVoyageContract.holds')}
                id="vessel-voyage-contract-holds"
                name="holds"
                data-cy="holds"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.vesselVoyageContract.holdCapacity')}
                id="vessel-voyage-contract-holdCapacity"
                name="holdCapacity"
                data-cy="holdCapacity"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.vesselVoyageContract.source')}
                id="vessel-voyage-contract-source"
                name="source"
                data-cy="source"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.vesselVoyageContract.destination')}
                id="vessel-voyage-contract-destination"
                name="destination"
                data-cy="destination"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.vesselVoyageContract.period')}
                id="vessel-voyage-contract-period"
                name="period"
                data-cy="period"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.vesselVoyageContract.cost')}
                id="vessel-voyage-contract-cost"
                name="cost"
                data-cy="cost"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vessel-voyage-contract" replace color="info">
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

export default VesselVoyageContractUpdate;
