import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/purchase-contract">
      <Translate contentKey="global.menu.entities.purchaseContract" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/sale-contract">
      <Translate contentKey="global.menu.entities.saleContract" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/vessel-voyage-contract">
      <Translate contentKey="global.menu.entities.vesselVoyageContract" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/vessel-time-contract">
      <Translate contentKey="global.menu.entities.vesselTimeContract" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/port">
      <Translate contentKey="global.menu.entities.port" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
