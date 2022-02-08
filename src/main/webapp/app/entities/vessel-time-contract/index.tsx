import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VesselTimeContract from './vessel-time-contract';
import VesselTimeContractDetail from './vessel-time-contract-detail';
import VesselTimeContractUpdate from './vessel-time-contract-update';
import VesselTimeContractDeleteDialog from './vessel-time-contract-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VesselTimeContractUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VesselTimeContractUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VesselTimeContractDetail} />
      <ErrorBoundaryRoute path={match.url} component={VesselTimeContract} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VesselTimeContractDeleteDialog} />
  </>
);

export default Routes;
