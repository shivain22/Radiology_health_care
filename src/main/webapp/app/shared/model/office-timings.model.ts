import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IOfficeTimings {
  id?: number;
  date?: dayjs.Dayjs | null;
  shiftStart?: dayjs.Dayjs;
  shiftEnd?: dayjs.Dayjs;
  defaultTimings?: boolean | null;
  user?: IUser;
}

export const defaultValue: Readonly<IOfficeTimings> = {
  defaultTimings: false,
};
