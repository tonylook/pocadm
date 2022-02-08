import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Port from './port';
import PortDetail from './port-detail';
import PortUpdate from './port-update';
import PortDeleteDialog from './port-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PortUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PortUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PortDetail} />
      <ErrorBoundaryRoute path={match.url} component={Port} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PortDeleteDialog} />
  </>
);

export default Routes;
