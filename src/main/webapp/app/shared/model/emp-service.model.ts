import { IRank } from 'app/shared/model/rank.model';
import { IUnit } from 'app/shared/model/unit.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IEmpService {
  id?: number;
  name?: string;
  ranks?: IRank[] | null;
  units?: IUnit[] | null;
  employees?: IEmployee[] | null;
}

export const defaultValue: Readonly<IEmpService> = {};
