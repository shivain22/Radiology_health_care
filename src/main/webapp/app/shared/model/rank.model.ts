import { IEmpService } from 'app/shared/model/emp-service.model';
import { IUser } from 'app/shared/model/user.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { rankDivisions } from 'app/shared/model/enumerations/rank-divisions.model';

export interface IRank {
  id?: number;
  name?: string;
  shortName?: string | null;
  division?: keyof typeof rankDivisions | null;
  empService?: IEmpService;

  employees?: IEmployee[] | null;
}

export const defaultValue: Readonly<IRank> = {};
