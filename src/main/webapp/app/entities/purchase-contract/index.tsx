import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PurchaseContract from './purchase-contract';
import PurchaseContractDetail from './purchase-contract-detail';
import PurchaseContractUpdate from './purchase-contract-update';
import PurchaseContractDeleteDialog from './purchase-contract-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PurchaseContractUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PurchaseContractUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PurchaseContractDetail} />
      <ErrorBoundaryRoute path={match.url} component={PurchaseContract} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PurchaseContractDeleteDialog} />
  </>
);

export default Routes;
