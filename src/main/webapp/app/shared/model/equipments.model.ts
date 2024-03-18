import { IRooms } from 'app/shared/model/rooms.model';
import { ITestCatogories } from 'app/shared/model/test-catogories.model';
import { ITechicianEquipmentMapping } from 'app/shared/model/techician-equipment-mapping.model';

export interface IEquipments {
  id?: number;
  name?: string;
  rooms?: IRooms;
  testCatogories?: ITestCatogories[] | null;
  techicianEquipmentMappings?: ITechicianEquipmentMapping[] | null;
}

export const defaultValue: Readonly<IEquipments> = {};
