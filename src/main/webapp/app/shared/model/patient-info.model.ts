import { IEmployee } from 'app/shared/model/employee.model';
import { IPatientTestTimings } from 'app/shared/model/patient-test-timings.model';

export interface IPatientInfo {
  id?: number;
  name?: string | null;
  age?: number | null;
  gender?: string | null;
  dateOfBirth?: string | null;
  relation?: string | null;
  mobile?: number | null;
  employeeId?: IEmployee | null;
  employeeHis?: IEmployee | null;
  employeeServiceNo?: IEmployee | null;
  patientTestTimings?: IPatientTestTimings[] | null;
}

export const defaultValue: Readonly<IPatientInfo> = {};
