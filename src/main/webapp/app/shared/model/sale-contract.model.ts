import { IPort } from 'app/shared/model/port.model';
import { Quality } from 'app/shared/model/enumerations/quality.model';

export interface ISaleContract {
  id?: number;
  deliveryWindow?: number | null;
  soymealQuality?: Quality | null;
  price?: number | null;
  volume?: number | null;
  allowances?: number | null;
  status?: boolean | null;
  port?: IPort;
}

export const defaultValue: Readonly<ISaleContract> = {
  status: false,
};
