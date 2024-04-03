import { z } from "zod";

export const roomData = z.object({
  id: z.number(),
  roomNo: z.number(),
});

export const formData = z.object({
  roomNo: z.string(),
});
const insertRoomParams = roomData.omit({ id: true });

export type InsertRoomParams = z.infer<typeof insertRoomParams>;
export type RoomData = z.infer<typeof roomData>;
export type RoomForm = z.infer<typeof formData>;
