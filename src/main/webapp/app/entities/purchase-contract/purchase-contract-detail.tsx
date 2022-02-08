import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './purchase-contract.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PurchaseContractDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const purchaseContractEntity = useAppSelector(state => state.purchaseContract.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="purchaseContractDetailsHeading">
          <Translate contentKey="pocadmApp.purchaseContract.detail.title">PurchaseContract</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{purchaseContractEntity.id}</dd>
          <dt>
            <span id="purchasingWindow">
              <Translate contentKey="pocadmApp.purchaseContract.purchasingWindow">Purchasing Window</Translate>
            </span>
          </dt>
          <dd>{purchaseContractEntity.purchasingWindow}</dd>
          <dt>
            <span id="soymealQuality">
              <Translate contentKey="pocadmApp.purchaseContract.soymealQuality">Soymeal Quality</Translate>
            </span>
          </dt>
          <dd>{purchaseContractEntity.soymealQuality}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="pocadmApp.purchaseContract.price">Price</Translate>
            </span>
          </dt>
          <dd>{purchaseContractEntity.price}</dd>
          <dt>
            <span id="volume">
              <Translate contentKey="pocadmApp.purchaseContract.volume">Volume</Translate>
            </span>
          </dt>
          <dd>{purchaseContractEntity.volume}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="pocadmApp.purchaseContract.status">Status</Translate>
            </span>
          </dt>
          <dd>{purchaseContractEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="pocadmApp.purchaseContract.port">Port</Translate>
          </dt>
          <dd>{purchaseContractEntity.port ? purchaseContractEntity.port.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/purchase-contract" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/purchase-contract/${purchaseContractEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PurchaseContractDetail;
