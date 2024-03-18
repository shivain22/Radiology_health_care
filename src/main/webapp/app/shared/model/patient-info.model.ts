import { IEmployee } from 'app/shared/model/employee.model';
import { IPatientTestInfo } from 'app/shared/model/patient-test-info.model';

export interface IPatientInfo {
  id?: number;
  age?: number | null;
  gender?: string | null;
  relation?: string | null;
  employee?: IEmployee;
  patientTestInfos?: IPatientTestInfo[] | null;
}

export const defaultValue: Readonly<IPatientInfo> = {};
