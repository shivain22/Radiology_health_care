import { IEquipments } from 'app/shared/model/equipments.model';

export interface IRooms {
  id?: number;
  roomNo?: number;
  equipments?: IEquipments[] | null;
}

export const defaultValue: Readonly<IRooms> = {};
