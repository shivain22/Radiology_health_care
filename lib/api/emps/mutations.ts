import { db } from "@/lib/db/index";
import { eq } from "drizzle-orm";
import { 
  EmpId, 
  NewEmpParams,
  UpdateEmpParams, 
  updateEmpSchema,
  insertEmpSchema, 
  emps,
  empIdSchema 
} from "@/lib/db/schema/emps";

export const createEmp = async (emp: NewEmpParams) => {
  const newEmp = insertEmpSchema.parse(emp);
  try {
    await db.insert(emps).values(newEmp)
    return { success: true }
  } catch (err) {
    const message = (err as Error).message ?? "Error, please try again";
    console.error(message);
    throw { error: message };
  }
};

export const updateEmp = async (id: EmpId, emp: UpdateEmpParams) => {
  const { id: empId } = empIdSchema.parse({ id });
  const newEmp = updateEmpSchema.parse(emp);
  try {
    await db
     .update(emps)
     .set(newEmp)
     .where(eq(emps.id, empId!))
    return {success: true}
  } catch (err) {
    const message = (err as Error).message ?? "Error, please try again";
    console.error(message);
    throw { error: message };
  }
};

export const deleteEmp = async (id: EmpId) => {
  const { id: empId } = empIdSchema.parse({ id });
  try {
    await db.delete(emps).where(eq(emps.id, empId!))
    return {success: true}
  } catch (err) {
    const message = (err as Error).message ?? "Error, please try again";
    console.error(message);
    throw { error: message };
  }
};

