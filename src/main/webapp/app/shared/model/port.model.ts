export interface IPort {
  id?: number;
  loadingTime?: number | null;
  unloadingTime?: number | null;
  waitingTime?: number | null;
}

export const defaultValue: Readonly<IPort> = {};
