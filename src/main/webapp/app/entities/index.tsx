import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PurchaseContract from './purchase-contract';
import SaleContract from './sale-contract';
import VesselVoyageContract from './vessel-voyage-contract';
import VesselTimeContract from './vessel-time-contract';
import Port from './port';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}purchase-contract`} component={PurchaseContract} />
      <ErrorBoundaryRoute path={`${match.url}sale-contract`} component={SaleContract} />
      <ErrorBoundaryRoute path={`${match.url}vessel-voyage-contract`} component={VesselVoyageContract} />
      <ErrorBoundaryRoute path={`${match.url}vessel-time-contract`} component={VesselTimeContract} />
      <ErrorBoundaryRoute path={`${match.url}port`} component={Port} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
