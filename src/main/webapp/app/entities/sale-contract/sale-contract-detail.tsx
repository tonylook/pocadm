import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './sale-contract.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SaleContractDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const saleContractEntity = useAppSelector(state => state.saleContract.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="saleContractDetailsHeading">
          <Translate contentKey="pocadmApp.saleContract.detail.title">SaleContract</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{saleContractEntity.id}</dd>
          <dt>
            <span id="deliveryWindow">
              <Translate contentKey="pocadmApp.saleContract.deliveryWindow">Delivery Window</Translate>
            </span>
          </dt>
          <dd>{saleContractEntity.deliveryWindow}</dd>
          <dt>
            <span id="soymealQuality">
              <Translate contentKey="pocadmApp.saleContract.soymealQuality">Soymeal Quality</Translate>
            </span>
          </dt>
          <dd>{saleContractEntity.soymealQuality}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="pocadmApp.saleContract.price">Price</Translate>
            </span>
          </dt>
          <dd>{saleContractEntity.price}</dd>
          <dt>
            <span id="volume">
              <Translate contentKey="pocadmApp.saleContract.volume">Volume</Translate>
            </span>
          </dt>
          <dd>{saleContractEntity.volume}</dd>
          <dt>
            <span id="allowances">
              <Translate contentKey="pocadmApp.saleContract.allowances">Allowances</Translate>
            </span>
          </dt>
          <dd>{saleContractEntity.allowances}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="pocadmApp.saleContract.status">Status</Translate>
            </span>
          </dt>
          <dd>{saleContractEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="pocadmApp.saleContract.port">Port</Translate>
          </dt>
          <dd>{saleContractEntity.port ? saleContractEntity.port.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/sale-contract" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sale-contract/${saleContractEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SaleContractDetail;
