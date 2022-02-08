import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './vessel-voyage-contract.reducer';
import { IVesselVoyageContract } from 'app/shared/model/vessel-voyage-contract.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VesselVoyageContract = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const vesselVoyageContractList = useAppSelector(state => state.vesselVoyageContract.entities);
  const loading = useAppSelector(state => state.vesselVoyageContract.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="vessel-voyage-contract-heading" data-cy="VesselVoyageContractHeading">
        <Translate contentKey="pocadmApp.vesselVoyageContract.home.title">Vessel Voyage Contracts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="pocadmApp.vesselVoyageContract.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="pocadmApp.vesselVoyageContract.home.createLabel">Create new Vessel Voyage Contract</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vesselVoyageContractList && vesselVoyageContractList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="pocadmApp.vesselVoyageContract.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="pocadmApp.vesselVoyageContract.holds">Holds</Translate>
                </th>
                <th>
                  <Translate contentKey="pocadmApp.vesselVoyageContract.holdCapacity">Hold Capacity</Translate>
                </th>
                <th>
                  <Translate contentKey="pocadmApp.vesselVoyageContract.source">Source</Translate>
                </th>
                <th>
                  <Translate contentKey="pocadmApp.vesselVoyageContract.destination">Destination</Translate>
                </th>
                <th>
                  <Translate contentKey="pocadmApp.vesselVoyageContract.period">Period</Translate>
                </th>
                <th>
                  <Translate contentKey="pocadmApp.vesselVoyageContract.cost">Cost</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vesselVoyageContractList.map((vesselVoyageContract, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${vesselVoyageContract.id}`} color="link" size="sm">
                      {vesselVoyageContract.id}
                    </Button>
                  </td>
                  <td>{vesselVoyageContract.holds}</td>
                  <td>{vesselVoyageContract.holdCapacity}</td>
                  <td>{vesselVoyageContract.source}</td>
                  <td>{vesselVoyageContract.destination}</td>
                  <td>{vesselVoyageContract.period}</td>
                  <td>{vesselVoyageContract.cost}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${vesselVoyageContract.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${vesselVoyageContract.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${vesselVoyageContract.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="pocadmApp.vesselVoyageContract.home.notFound">No Vessel Voyage Contracts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default VesselVoyageContract;
