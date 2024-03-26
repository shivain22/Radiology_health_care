import { IEmpService } from 'app/shared/model/emp-service.model';
import { IUser } from 'app/shared/model/user.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IRank {
  id?: number;
  name?: string;
  empService?: IEmpService;
  user?: IUser;
  employees?: IEmployee[] | null;
}

export const defaultValue: Readonly<IRank> = {};
