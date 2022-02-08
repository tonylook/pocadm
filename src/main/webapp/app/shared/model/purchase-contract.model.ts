import { IPort } from 'app/shared/model/port.model';
import { Quality } from 'app/shared/model/enumerations/quality.model';

export interface IPurchaseContract {
  id?: number;
  purchasingWindow?: number | null;
  soymealQuality?: Quality | null;
  price?: number | null;
  volume?: number | null;
  status?: boolean | null;
  port?: IPort;
}

export const defaultValue: Readonly<IPurchaseContract> = {
  status: false,
};
