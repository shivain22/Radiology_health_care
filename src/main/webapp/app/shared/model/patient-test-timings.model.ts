import dayjs from 'dayjs';
import { IPatientInfo } from 'app/shared/model/patient-info.model';
import { ITestCategories } from 'app/shared/model/test-categories.model';

export interface IPatientTestTimings {
  id?: number;
  testTimings?: dayjs.Dayjs | null;
  priority?: string | null;
  clinicalNote?: string | null;
  spclInstruction?: string | null;
  patientInfo?: IPatientInfo;
  testCategories?: ITestCategories | null;
}

export const defaultValue: Readonly<IPatientTestTimings> = {};
