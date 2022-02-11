import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './port.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PortDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const portEntity = useAppSelector(state => state.port.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="portDetailsHeading">
          <Translate contentKey="pocadmApp.port.detail.title">Port</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{portEntity.id}</dd>
          <dt>
            <span id="loadingTime">
              <Translate contentKey="pocadmApp.port.loadingTime">Loading Time</Translate>
            </span>
          </dt>
          <dd>{portEntity.loadingTime}</dd>
          <dt>
            <span id="unloadingTime">
              <Translate contentKey="pocadmApp.port.unloadingTime">Unloading Time</Translate>
            </span>
          </dt>
          <dd>{portEntity.unloadingTime}</dd>
          <dt>
            <span id="waitingTime">
              <Translate contentKey="pocadmApp.port.waitingTime">Waiting Time</Translate>
            </span>
          </dt>
          <dd>{portEntity.waitingTime}</dd>
        </dl>
        <Button tag={Link} to="/port" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/port/${portEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PortDetail;
