"use server";
import { InsertRoomParams } from "@/schema/rooms";
import axios from "axios";
import { revalidatePath } from "next/cache";
import { cookies } from "next/headers";

const roomsUrl = process.env.BACKEND_URL + "/api/rooms";
const userAuthToken = cookies().get("authToken")?.value;
const bearerToken = `Bearer ${userAuthToken};
`;
const handleErrors = (e: unknown) => {
  const errMsg = "Error,please try again";

  if (e instanceof Error) return e.message.length > 0 ? e.message : errMsg;
  if (e && typeof e === "object" && "error" in e) {
    const errAsStr = e.error as string;
    return errAsStr.length > 0 ? errAsStr : errMsg;
  }

  return errMsg;
};
const revalidateRooms=()=>revalidatePath("/rooms");
export const createRoomAction = async (values: InsertRoomParams) => {
  try {
    const response = await axios.post(
      roomsUrl,
      {
        roomNo: values.roomNo,
      },
      {
        headers: {
          Authorization: bearerToken,
        },
      }
    );
    if (response.status === 201) {
      revalidateRooms();
      console.log("Rooms added succesfully");
    }
  } catch (e) {
    return handleErrors(e);
  }
};

export const deleteRoomAction = async (id: number) => {
  try {
    const response = await axios.delete(roomsUrl + "/" + id, {
      headers: {
        Authorization: bearerToken,
      },
    });
    if (response.status === 204) {
      console.log("Room deleted successfully");
    }
  } catch (e) {
    return handleErrors(e);
  }
};
