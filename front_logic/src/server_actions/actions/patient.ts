"use server";

import { InsertPatientParams } from "@/schema/patients";
import axios from "axios";
import { revalidatePath } from "next/cache";
import { cookies } from "next/headers";

const PatientUrl = process.env.BACKEND_URL + "/api/patient-infos";
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

const revalidatePatients = () => revalidatePath("/patients");

export const createPatientAction = async (patient: InsertPatientParams) => {
  try {
    const response = await axios.post(PatientUrl, patient, {
      headers: {
        Authorization: bearerToken,
      },
    });

    if (response.status === 201) {
      revalidatePatients();
      return console.log("Patient added successfully");
    }
  } catch (e) {
    console.log(e);
  }
};

export const deletePatientAction = async (id: number) => {
  try {
    const response = await axios.delete(
      PatientUrl + "/" + id,
      {
        headers: {
          Authorization: bearerToken,
        },
      }
    );
    if (response.status === 204) {
      revalidatePatients();
      return console.log("Patient deleted successfully");
    }
  } catch (e) {
    return handleErrors(e);
  }
};
