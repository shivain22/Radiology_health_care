import dayjs from 'dayjs';
import { IEquipment } from 'app/shared/model/equipment.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface ITechnicianEquipmentMapping {
  id?: number;
  dateTime?: dayjs.Dayjs;
  equipment?: IEquipment;
  employee?: IEmployee;
}

export const defaultValue: Readonly<ITechnicianEquipmentMapping> = {};
