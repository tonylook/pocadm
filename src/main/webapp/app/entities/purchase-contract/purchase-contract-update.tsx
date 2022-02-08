import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPort } from 'app/shared/model/port.model';
import { getEntities as getPorts } from 'app/entities/port/port.reducer';
import { getEntity, updateEntity, createEntity, reset } from './purchase-contract.reducer';
import { IPurchaseContract } from 'app/shared/model/purchase-contract.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Quality } from 'app/shared/model/enumerations/quality.model';

export const PurchaseContractUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const ports = useAppSelector(state => state.port.entities);
  const purchaseContractEntity = useAppSelector(state => state.purchaseContract.entity);
  const loading = useAppSelector(state => state.purchaseContract.loading);
  const updating = useAppSelector(state => state.purchaseContract.updating);
  const updateSuccess = useAppSelector(state => state.purchaseContract.updateSuccess);
  const qualityValues = Object.keys(Quality);
  const handleClose = () => {
    props.history.push('/purchase-contract');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPorts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...purchaseContractEntity,
      ...values,
      port: ports.find(it => it.id.toString() === values.port.toString()),
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
          soymealQuality: 'BAD',
          ...purchaseContractEntity,
          port: purchaseContractEntity?.port?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="pocadmApp.purchaseContract.home.createOrEditLabel" data-cy="PurchaseContractCreateUpdateHeading">
            <Translate contentKey="pocadmApp.purchaseContract.home.createOrEditLabel">Create or edit a PurchaseContract</Translate>
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
                  id="purchase-contract-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('pocadmApp.purchaseContract.purchasingWindow')}
                id="purchase-contract-purchasingWindow"
                name="purchasingWindow"
                data-cy="purchasingWindow"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.purchaseContract.soymealQuality')}
                id="purchase-contract-soymealQuality"
                name="soymealQuality"
                data-cy="soymealQuality"
                type="select"
              >
                {qualityValues.map(quality => (
                  <option value={quality} key={quality}>
                    {translate('pocadmApp.Quality.' + quality)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('pocadmApp.purchaseContract.price')}
                id="purchase-contract-price"
                name="price"
                data-cy="price"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.purchaseContract.volume')}
                id="purchase-contract-volume"
                name="volume"
                data-cy="volume"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.purchaseContract.status')}
                id="purchase-contract-status"
                name="status"
                data-cy="status"
                check
                type="checkbox"
              />
              <ValidatedField
                id="purchase-contract-port"
                name="port"
                data-cy="port"
                label={translate('pocadmApp.purchaseContract.port')}
                type="select"
                required
              >
                <option value="" key="0" />
                {ports
                  ? ports.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/purchase-contract" replace color="info">
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

export default PurchaseContractUpdate;
