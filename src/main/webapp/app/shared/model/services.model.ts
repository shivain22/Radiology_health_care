import { IEmployee } from 'app/shared/model/employee.model';
import { IRank } from 'app/shared/model/rank.model';

export interface IServices {
  id?: number;
  name?: string;
  employees?: IEmployee[] | null;
  ranks?: IRank[] | null;
}

export const defaultValue: Readonly<IServices> = {};
