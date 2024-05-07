import dayjs from 'dayjs';
import { IEquipment } from 'app/shared/model/equipment.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IUser } from 'app/shared/model/user.model';

export interface ITechnicianEquipmentMapping {
  id?: number;
  dateTime?: dayjs.Dayjs;
  equipment?: IEquipment;
  employee?: IEmployee;
  user?: IUser;
}

export const defaultValue: Readonly<ITechnicianEquipmentMapping> = {};
