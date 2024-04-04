import { IRank } from 'app/shared/model/rank.model';
import { IEmpService } from 'app/shared/model/emp-service.model';
import { IUnit } from 'app/shared/model/unit.model';
import { IUser } from 'app/shared/model/user.model';
import { ITechnicianEquipmentMapping } from 'app/shared/model/technician-equipment-mapping.model';
import { IPatientInfo } from 'app/shared/model/patient-info.model';

export interface IEmployee {
  id?: number;
  name?: string | null;
  technician?: boolean | null;
  his?: string | null;
  serviceNo?: string | null;
  rank?: IRank;
  empService?: IEmpService;
  unit?: IUnit;

  technicianEquipmentMappings?: ITechnicianEquipmentMapping[] | null;
  patientInfoEmployeeIds?: IPatientInfo[] | null;
  patientInfoEmployeeHis?: IPatientInfo[] | null;
  patientInfoEmployeeServiceNos?: IPatientInfo[] | null;
}

export const defaultValue: Readonly<IEmployee> = {
  technician: false,
};
