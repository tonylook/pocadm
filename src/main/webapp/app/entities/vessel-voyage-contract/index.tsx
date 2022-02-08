import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VesselVoyageContract from './vessel-voyage-contract';
import VesselVoyageContractDetail from './vessel-voyage-contract-detail';
import VesselVoyageContractUpdate from './vessel-voyage-contract-update';
import VesselVoyageContractDeleteDialog from './vessel-voyage-contract-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VesselVoyageContractUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VesselVoyageContractUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VesselVoyageContractDetail} />
      <ErrorBoundaryRoute path={match.url} component={VesselVoyageContract} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VesselVoyageContractDeleteDialog} />
  </>
);

export default Routes;
