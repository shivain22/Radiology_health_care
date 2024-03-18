import { ITestCatogories } from 'app/shared/model/test-catogories.model';
import { IPatientTestInfo } from 'app/shared/model/patient-test-info.model';

export interface ITestTimings {
  id?: number;
  timings?: string;
  testCatogories?: ITestCatogories;
  patientTestInfos?: IPatientTestInfo[] | null;
}

export const defaultValue: Readonly<ITestTimings> = {};
