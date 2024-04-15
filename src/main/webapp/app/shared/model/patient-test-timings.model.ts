import dayjs from 'dayjs';
import { IPatientInfo } from 'app/shared/model/patient-info.model';
import { ITestCategories } from 'app/shared/model/test-categories.model';

export interface IPatientTestTimings {
  id?: number;
  priority?: string | null;
  clinicalNote?: string | null;
  spclInstruction?: string | null;
  status?: string | null;
  startTime?: dayjs.Dayjs | null;
  endTime?: dayjs.Dayjs | null;
  patientInfo?: IPatientInfo;
  testCategories?: ITestCategories | null;
}

export const defaultValue: Readonly<IPatientTestTimings> = {};
