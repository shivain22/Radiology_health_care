import { cookies } from "next/headers";
import axios from "axios";

export const getUnits = async () => {
  const unitsUrl = process.env.BACKEND_URL + "/api/units";
  const userAuthToken = cookies().get("authToken")?.value;

  const bearerToken = `Bearer ${userAuthToken}`;
  const response = await axios.get(unitsUrl, {
    headers: {
      Authorization: bearerToken,
    },
  });
  const Units = response.data
  return Units;
};