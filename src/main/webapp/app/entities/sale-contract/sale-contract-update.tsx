import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPort } from 'app/shared/model/port.model';
import { getEntities as getPorts } from 'app/entities/port/port.reducer';
import { getEntity, updateEntity, createEntity, reset } from './sale-contract.reducer';
import { ISaleContract } from 'app/shared/model/sale-contract.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Quality } from 'app/shared/model/enumerations/quality.model';

export const SaleContractUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const ports = useAppSelector(state => state.port.entities);
  const saleContractEntity = useAppSelector(state => state.saleContract.entity);
  const loading = useAppSelector(state => state.saleContract.loading);
  const updating = useAppSelector(state => state.saleContract.updating);
  const updateSuccess = useAppSelector(state => state.saleContract.updateSuccess);
  const qualityValues = Object.keys(Quality);
  const handleClose = () => {
    props.history.push('/sale-contract');
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
      ...saleContractEntity,
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
          ...saleContractEntity,
          port: saleContractEntity?.port?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="pocadmApp.saleContract.home.createOrEditLabel" data-cy="SaleContractCreateUpdateHeading">
            <Translate contentKey="pocadmApp.saleContract.home.createOrEditLabel">Create or edit a SaleContract</Translate>
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
                  id="sale-contract-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('pocadmApp.saleContract.deliveryWindow')}
                id="sale-contract-deliveryWindow"
                name="deliveryWindow"
                data-cy="deliveryWindow"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.saleContract.soymealQuality')}
                id="sale-contract-soymealQuality"
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
                label={translate('pocadmApp.saleContract.price')}
                id="sale-contract-price"
                name="price"
                data-cy="price"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.saleContract.volume')}
                id="sale-contract-volume"
                name="volume"
                data-cy="volume"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.saleContract.allowances')}
                id="sale-contract-allowances"
                name="allowances"
                data-cy="allowances"
                type="text"
              />
              <ValidatedField
                label={translate('pocadmApp.saleContract.status')}
                id="sale-contract-status"
                name="status"
                data-cy="status"
                check
                type="checkbox"
              />
              <ValidatedField
                id="sale-contract-port"
                name="port"
                data-cy="port"
                label={translate('pocadmApp.saleContract.port')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sale-contract" replace color="info">
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

export default SaleContractUpdate;
