import { db } from "@/lib/db/index";
import { eq } from "drizzle-orm";
import { type EmpId, empIdSchema, emps } from "@/lib/db/schema/emps";

export const getEmps = async () => {
  const rows = await db.select().from(emps);
  const e = rows
  return { emps: e };
};

export const getEmpById = async (id: EmpId) => {
  const { id: empId } = empIdSchema.parse({ id });
  const [row] = await db.select().from(emps).where(eq(emps.id, empId));
  if (row === undefined) return {};
  const e = row;
  return { emp: e };
};


