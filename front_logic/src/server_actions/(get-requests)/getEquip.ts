"use server";
import axios from "axios";
import { cookies} from "next/headers";

export const getEquip = async () => {
  const equipUrl = process.env.BACKEND_URL + "/api/equipment";
  const userauthToken = cookies().get("authToken")?.value;

  const bearerToken = `Bearer ${userauthToken}`;

  const response = await axios.get(equipUrl, {
    headers: {
      Authorization: bearerToken,
    },
  });
  const Equip = response.data;
  return Equip;
};
