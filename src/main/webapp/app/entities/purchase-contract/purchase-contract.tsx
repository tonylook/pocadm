import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './purchase-contract.reducer';
import { IPurchaseContract } from 'app/shared/model/purchase-contract.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PurchaseContract = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const purchaseContractList = useAppSelector(state => state.purchaseContract.entities);
  const loading = useAppSelector(state => state.purchaseContract.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="purchase-contract-heading" data-cy="PurchaseContractHeading">
        <Translate contentKey="pocadmApp.purchaseContract.home.title">Purchase Contracts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="pocadmApp.purchaseContract.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="pocadmApp.purchaseContract.home.createLabel">Create new Purchase Contract</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {purchaseContractList && purchaseContractList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="pocadmApp.purchaseContract.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="pocadmApp.purchaseContract.purchasingWindow">Purchasing Window</Translate>
                </th>
                <th>
                  <Translate contentKey="pocadmApp.purchaseContract.soymealQuality">Soymeal Quality</Translate>
                </th>
                <th>
                  <Translate contentKey="pocadmApp.purchaseContract.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="pocadmApp.purchaseContract.volume">Volume</Translate>
                </th>
                <th>
                  <Translate contentKey="pocadmApp.purchaseContract.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="pocadmApp.purchaseContract.port">Port</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {purchaseContractList.map((purchaseContract, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${purchaseContract.id}`} color="link" size="sm">
                      {purchaseContract.id}
                    </Button>
                  </td>
                  <td>{purchaseContract.purchasingWindow}</td>
                  <td>
                    <Translate contentKey={`pocadmApp.Quality.${purchaseContract.soymealQuality}`} />
                  </td>
                  <td>{purchaseContract.price}</td>
                  <td>{purchaseContract.volume}</td>
                  <td>{purchaseContract.status ? 'true' : 'false'}</td>
                  <td>{purchaseContract.port ? <Link to={`port/${purchaseContract.port.id}`}>{purchaseContract.port.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${purchaseContract.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${purchaseContract.id}/edit`}
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
                        to={`${match.url}/${purchaseContract.id}/delete`}
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
              <Translate contentKey="pocadmApp.purchaseContract.home.notFound">No Purchase Contracts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PurchaseContract;
