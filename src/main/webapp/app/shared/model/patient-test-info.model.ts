import { IPatientInfo } from 'app/shared/model/patient-info.model';
import { ITestTimings } from 'app/shared/model/test-timings.model';

export interface IPatientTestInfo {
  id?: number;
  patientInfo?: IPatientInfo;
  testTimings?: ITestTimings;
}

export const defaultValue: Readonly<IPatientTestInfo> = {};
