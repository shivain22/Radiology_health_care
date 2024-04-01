import { z } from "zod";

const EmployeeData = z.object({
  id: z.number(),
  name: z.string(),
  technician: z.boolean(),
  his: z.string(),
  serviceNo: z.string(),
  empServiceId: z.number(),
  rankId: z.number(),
  unitId: z.number(),
});

export const formData = z.object({
  name: z.string(),
  technician: z.boolean(),
  serviceNo: z.string(),
  his: z.string(),
  empServiceId: z.string(),
  unitId: z.string(),
  rankId: z.string(),
});

const insertEmployeeParams = EmployeeData.omit({ id: true });

export type Employeeform = z.infer<typeof formData>;
export type EmployeeData = z.infer<typeof EmployeeData>;
export type InsertEmployeeParams = z.infer<typeof insertEmployeeParams>;
export type TransformEmployeeData = {
  id: number;
  name: string;
  technician: boolean;
  his: string;
  serviceNo: string;
  empServiceId?: number;
  rankId?: number;
  unitId?: number;
  empServiceName: string;
  unitName: string;
  rankName: string;
};
