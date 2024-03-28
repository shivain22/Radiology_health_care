"use server";
import { InsertEmployeeParams } from "@/schema/employees";
import axios from "axios";
import { revalidatePath } from "next/cache";
import { cookies } from "next/headers";

const EmployeesUrl = process.env.BACKEND_URL + "/api/employees";
const userAuthToken = cookies().get("authToken")?.value;
const bearerToken = `Bearer ${userAuthToken}`;

const handleErrors = (e: unknown) => {
  const errMsg = "Error, please try again";
  if (e instanceof Error) return e.message.length > 0 ? e.message : errMsg;
  if (e && typeof e === "object" && "error" in e) {
    const errAsStr = e.error as string;
    return errAsStr.length > 0 ? errAsStr : errMsg;
  }
  return errMsg;
};

const revalidateEmployees = () => revalidatePath("/employees");
export const createEmployeeAction = async (employee: InsertEmployeeParams) => {
  try {
    const response = await axios.post(EmployeesUrl, employee, {
      headers: {
        Authorization: bearerToken,
      },
    });

    if (response.status === 201) {
      revalidateEmployees();
      return console.log("Employee added successfully");
    }
  } catch (e) {
    return handleErrors(e)
  }
};

export const deleteEmployeeAction = async (id: number) => {
  try {
    const response = await axios.delete(
      EmployeesUrl + "/" + id,
      {
        headers: {
          Authorization: bearerToken,
        },
      }
    );
    if (response.status === 204) {
      revalidateEmployees();
      return console.log("Employee deleted successfully");
    }
  } catch (e) {
    return handleErrors(e);
  }
}