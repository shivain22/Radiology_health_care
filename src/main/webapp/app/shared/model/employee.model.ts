import { IUnit } from 'app/shared/model/unit.model';
import { IServices } from 'app/shared/model/services.model';
import { IRank } from 'app/shared/model/rank.model';
import { IPatientInfo } from 'app/shared/model/patient-info.model';
import { ITechicianEquipmentMapping } from 'app/shared/model/techician-equipment-mapping.model';

export interface IEmployee {
  id?: number;
  his?: string | null;
  serviceNo?: string | null;
  name?: string | null;
  technician?: number | null;
  unit?: IUnit;
  services?: IServices;
  rank?: IRank;
  patientInfos?: IPatientInfo[] | null;
  techicianEquipmentMappings?: ITechicianEquipmentMapping[] | null;
}

export const defaultValue: Readonly<IEmployee> = {};
