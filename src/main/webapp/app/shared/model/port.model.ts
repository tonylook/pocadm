export interface IPort {
  id?: number;
  lodingTime?: number | null;
  unloadingTime?: number | null;
  waitingTime?: number | null;
}

export const defaultValue: Readonly<IPort> = {};
