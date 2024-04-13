import axios from "axios";

export const getRanksByServiceId = async (
  serviceId: number,
  authToken?: string
) => {
  const ranksByIdUrl =
    process.env.NEXT_PUBLIC_BACKEND_URL +
    "/api/ranks?empServiceId.equals=" +
    serviceId;
  const userAuthToken = authToken;

  const bearerToken = `Bearer ${userAuthToken}`;
  const response = await axios.get(ranksByIdUrl, {
    headers: {
      Authorization: bearerToken,
    },
  });
  const Ranks = response.data;
  return Ranks;
};

export const getUnitsByServiceId = async (
  serviceId: number,
  authToken?: string
) => {
  const unitsByIdUrl =
    process.env.NEXT_PUBLIC_BACKEND_URL +
    "/api/units?empServiceId.equals=" +
    serviceId;
  const userAuthToken = authToken;

  const bearerToken = `Bearer ${userAuthToken}`;
  const response = await axios.get(unitsByIdUrl, {
    headers: {
      Authorization: bearerToken,
    },
  });
  const Units = response.data;
  return Units;
};

export const getEquipmentsByClient = async (authToken?: string) => {
  const publicEquipmentsUrl =
    process.env.NEXT_PUBLIC_BACKEND_URL + "/api/equipment";
  const userAuthToken = authToken;
  const bearerToken = `Bearer ${userAuthToken}`;
  const response = await axios.get(publicEquipmentsUrl, {
    headers: {
      Authorization: bearerToken,
    },
  });
  const Equipments = response.data;
  return Equipments;
};

export const getParentTestCategories = async(authToken? : string) => {
  const getParentTestCategoriesUrl = process.env.NEXT_PUBLIC_BACKEND_URL + "/api/test-categories?parentTestCategoryId.specified=false";
  
  const userAuthToken = authToken;
  const bearerToken = `Bearer ${userAuthToken}`;
  const response = await axios.get(getParentTestCategoriesUrl, {
    headers: {
      Authorization: bearerToken
    }
  })
  const TestCategories = response.data 
  return TestCategories
}