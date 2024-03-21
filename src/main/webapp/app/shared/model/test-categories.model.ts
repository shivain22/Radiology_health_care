import { IEquipment } from 'app/shared/model/equipment.model';
import { IPatientTestTimings } from 'app/shared/model/patient-test-timings.model';

export interface ITestCategories {
  id?: number;
  testName?: string;
  equipment?: IEquipment;
  parentTestCategory?: ITestCategories | null;
  patientTestTimings?: IPatientTestTimings[] | null;
  testCategoryParents?: ITestCategories[] | null;
}

export const defaultValue: Readonly<ITestCategories> = {};
