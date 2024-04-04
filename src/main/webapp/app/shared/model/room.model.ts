import { IUser } from 'app/shared/model/user.model';
import { IEquipment } from 'app/shared/model/equipment.model';

export interface IRoom {
  id?: number;
  roomNo?: number;

  equipment?: IEquipment[] | null;
}

export const defaultValue: Readonly<IRoom> = {};
