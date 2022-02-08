export interface IVesselVoyageContract {
  id?: number;
  holds?: number | null;
  holdCapacity?: number | null;
  source?: string | null;
  destination?: string | null;
  period?: number | null;
  cost?: number | null;
}

export const defaultValue: Readonly<IVesselVoyageContract> = {};
