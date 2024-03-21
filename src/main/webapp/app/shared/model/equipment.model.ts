import { IRoom } from 'app/shared/model/room.model';
import { ITechnicianEquipmentMapping } from 'app/shared/model/technician-equipment-mapping.model';
import { ITestCategories } from 'app/shared/model/test-categories.model';

export interface IEquipment {
  id?: number;
  name?: string;
  room?: IRoom | null;
  technicianEquipmentMappings?: ITechnicianEquipmentMapping[] | null;
  testCategories?: ITestCategories[] | null;
}

export const defaultValue: Readonly<IEquipment> = {};
