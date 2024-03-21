import { IEmpService } from 'app/shared/model/emp-service.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IRank {
  id?: number;
  name?: string;
  empService?: IEmpService;
  employees?: IEmployee[] | null;
}

export const defaultValue: Readonly<IRank> = {};
