import axios from "axios";
import { cookies } from "next/headers";

export const getRooms = async () => {
  const roomsUrl = process.env.BACKEND_URL + "/api/rooms";
  const userAuthToken = cookies().get("authToken")?.value;
  const bearerToken = `Bearer ${userAuthToken}`;

  const response = await axios.get(roomsUrl, {
    headers: {
      Authorization: bearerToken,
    },
  });

  const Rooms = response.data;
  return Rooms;
};
