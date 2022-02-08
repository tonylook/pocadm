import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
import sessions from 'app/modules/account/sessions/sessions.reducer';
// prettier-ignore
import purchaseContract from 'app/entities/purchase-contract/purchase-contract.reducer';
// prettier-ignore
import saleContract from 'app/entities/sale-contract/sale-contract.reducer';
// prettier-ignore
import vesselVoyageContract from 'app/entities/vessel-voyage-contract/vessel-voyage-contract.reducer';
// prettier-ignore
import vesselTimeContract from 'app/entities/vessel-time-contract/vessel-time-contract.reducer';
// prettier-ignore
import port from 'app/entities/port/port.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  sessions,
  purchaseContract,
  saleContract,
  vesselVoyageContract,
  vesselTimeContract,
  port,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
