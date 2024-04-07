import { IEquipment } from 'app/shared/model/equipment.model';
import { IUser } from 'app/shared/model/user.model';
import { IPatientTestTimings } from 'app/shared/model/patient-test-timings.model';

export interface ITestCategories {
  id?: number;
  testName?: string;
  testDuration?: number | null;
  equipment?: IEquipment;
  parentTestCategory?: ITestCategories | null;
  patientTestTimings?: IPatientTestTimings[] | null;
  testCategoryParents?: ITestCategories[] | null;
}

export const defaultValue: Readonly<ITestCategories> = {};
