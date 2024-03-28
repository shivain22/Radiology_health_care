"use server";
import { InsertUnitParams } from "@/schema/units";
import axios from "axios";
import { revalidatePath } from "next/cache";
import { cookies } from "next/headers";

const unitsUrl = process.env.BACKEND_URL + "/api/units";
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

const revalidateUnits = () => revalidatePath("/units");

export const createUnitsActions = async (unit: InsertUnitParams) => {
  try {
    const response = await axios.post(unitsUrl, unit, {
      headers: {
        Authorization: bearerToken,
      },
    });
    if (response.status === 201) {
      revalidateUnits();
      return console.log("Units added successfully");
    }
  } catch (e) {
    return handleErrors(e);
  }
};

export const deleteUnitAction = async (id: number) => {
  try {
    const response = await axios.delete(unitsUrl + "/" + id, {
      headers: {
        Authorization: bearerToken,
      },
    });
    if (response.status === 204) {
      revalidateUnits();
      return console.log("Units deleted successfully");
    }
  } catch (e) {
    return handleErrors(e);
  }
};
