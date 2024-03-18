import { IServices } from 'app/shared/model/services.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IRank {
  id?: number;
  name?: string;
  services?: IServices;
  employees?: IEmployee[] | null;
}

export const defaultValue: Readonly<IRank> = {};
