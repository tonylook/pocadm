export interface IVesselTimeContract {
  id?: number;
  holds?: number | null;
  holdCapacity?: number | null;
  period?: number | null;
  costPerDay?: number | null;
}

export const defaultValue: Readonly<IVesselTimeContract> = {};
