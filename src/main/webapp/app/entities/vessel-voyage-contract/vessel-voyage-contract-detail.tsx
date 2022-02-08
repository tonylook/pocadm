import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './vessel-voyage-contract.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VesselVoyageContractDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const vesselVoyageContractEntity = useAppSelector(state => state.vesselVoyageContract.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vesselVoyageContractDetailsHeading">
          <Translate contentKey="pocadmApp.vesselVoyageContract.detail.title">VesselVoyageContract</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{vesselVoyageContractEntity.id}</dd>
          <dt>
            <span id="holds">
              <Translate contentKey="pocadmApp.vesselVoyageContract.holds">Holds</Translate>
            </span>
          </dt>
          <dd>{vesselVoyageContractEntity.holds}</dd>
          <dt>
            <span id="holdCapacity">
              <Translate contentKey="pocadmApp.vesselVoyageContract.holdCapacity">Hold Capacity</Translate>
            </span>
          </dt>
          <dd>{vesselVoyageContractEntity.holdCapacity}</dd>
          <dt>
            <span id="source">
              <Translate contentKey="pocadmApp.vesselVoyageContract.source">Source</Translate>
            </span>
          </dt>
          <dd>{vesselVoyageContractEntity.source}</dd>
          <dt>
            <span id="destination">
              <Translate contentKey="pocadmApp.vesselVoyageContract.destination">Destination</Translate>
            </span>
          </dt>
          <dd>{vesselVoyageContractEntity.destination}</dd>
          <dt>
            <span id="period">
              <Translate contentKey="pocadmApp.vesselVoyageContract.period">Period</Translate>
            </span>
          </dt>
          <dd>{vesselVoyageContractEntity.period}</dd>
          <dt>
            <span id="cost">
              <Translate contentKey="pocadmApp.vesselVoyageContract.cost">Cost</Translate>
            </span>
          </dt>
          <dd>{vesselVoyageContractEntity.cost}</dd>
        </dl>
        <Button tag={Link} to="/vessel-voyage-contract" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vessel-voyage-contract/${vesselVoyageContractEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VesselVoyageContractDetail;
