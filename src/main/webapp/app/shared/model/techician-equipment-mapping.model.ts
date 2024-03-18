import { IEmployee } from 'app/shared/model/employee.model';
import { IEquipments } from 'app/shared/model/equipments.model';

export interface ITechicianEquipmentMapping {
  id?: number;
  dateTime?: string;
  employee?: IEmployee;
  equipments?: IEquipments;
}

export const defaultValue: Readonly<ITechicianEquipmentMapping> = {};
