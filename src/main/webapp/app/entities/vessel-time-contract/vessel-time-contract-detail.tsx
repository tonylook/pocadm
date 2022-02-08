import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './vessel-time-contract.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VesselTimeContractDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const vesselTimeContractEntity = useAppSelector(state => state.vesselTimeContract.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vesselTimeContractDetailsHeading">
          <Translate contentKey="pocadmApp.vesselTimeContract.detail.title">VesselTimeContract</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{vesselTimeContractEntity.id}</dd>
          <dt>
            <span id="holds">
              <Translate contentKey="pocadmApp.vesselTimeContract.holds">Holds</Translate>
            </span>
          </dt>
          <dd>{vesselTimeContractEntity.holds}</dd>
          <dt>
            <span id="holdCapacity">
              <Translate contentKey="pocadmApp.vesselTimeContract.holdCapacity">Hold Capacity</Translate>
            </span>
          </dt>
          <dd>{vesselTimeContractEntity.holdCapacity}</dd>
          <dt>
            <span id="period">
              <Translate contentKey="pocadmApp.vesselTimeContract.period">Period</Translate>
            </span>
          </dt>
          <dd>{vesselTimeContractEntity.period}</dd>
          <dt>
            <span id="costPerDay">
              <Translate contentKey="pocadmApp.vesselTimeContract.costPerDay">Cost Per Day</Translate>
            </span>
          </dt>
          <dd>{vesselTimeContractEntity.costPerDay}</dd>
        </dl>
        <Button tag={Link} to="/vessel-time-contract" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vessel-time-contract/${vesselTimeContractEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VesselTimeContractDetail;
