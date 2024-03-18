import { IEquipments } from 'app/shared/model/equipments.model';
import { ITestTimings } from 'app/shared/model/test-timings.model';

export interface ITestCatogories {
  id?: number;
  name?: string;
  equipments?: IEquipments;
  testCatogories_parent?: ITestCatogories | null;
  test_catogories_parent_catogories?: ITestCatogories[] | null;
  testTimings?: ITestTimings[] | null;
}

export const defaultValue: Readonly<ITestCatogories> = {};
