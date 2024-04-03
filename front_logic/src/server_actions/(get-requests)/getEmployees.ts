import { cookies } from "next/headers";
import axios from "axios";

export const getEmployees = async () => {
  const EmployeesUrl = process.env.BACKEND_URL + "/api/employees";
  const userAuthToken = cookies().get("authToken")?.value;

  const bearerToken = `Bearer ${userAuthToken}`;
  const response = await axios.get(EmployeesUrl, {
    headers: {
      Authorization: bearerToken,
    },
  });
  const Employees = response.data
  return Employees;
};