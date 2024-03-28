"use server";

import { revalidatePath } from "next/cache";
import {
  createEmp,
  deleteEmp,
  updateEmp,
} from "@/lib/api/emps/mutations";
import {
  EmpId,
  NewEmpParams,
  UpdateEmpParams,
  empIdSchema,
  insertEmpParams,
  updateEmpParams,
} from "@/lib/db/schema/emps";

const handleErrors = (e: unknown) => {
  const errMsg = "Error, please try again.";
  if (e instanceof Error) return e.message.length > 0 ? e.message : errMsg;
  if (e && typeof e === "object" && "error" in e) {
    const errAsStr = e.error as string;
    return errAsStr.length > 0 ? errAsStr : errMsg;
  }
  return errMsg;
};

const revalidateEmps = () => revalidatePath("/emps");

export const createEmpAction = async (input: NewEmpParams) => {
  try {
    const payload = insertEmpParams.parse(input);
    await createEmp(payload);
    revalidateEmps();
  } catch (e) {
    return handleErrors(e);
  }
};

export const updateEmpAction = async (input: UpdateEmpParams) => {
  try {
    const payload = updateEmpParams.parse(input);
    await updateEmp(payload.id, payload);
    revalidateEmps();
  } catch (e) {
    return handleErrors(e);
  }
};

export const deleteEmpAction = async (input: EmpId) => {
  try {
    const payload = empIdSchema.parse({ id: input });
    await deleteEmp(payload.id);
    revalidateEmps();
  } catch (e) {
    return handleErrors(e);
  }
};