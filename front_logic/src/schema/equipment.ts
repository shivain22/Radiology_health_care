import { z } from "zod";

export const equipData = z.object({
  id: z.number(),
  name: z.string(),
  roomId: z.number(),
});

export const formData = z.object({
  name: z.string(),
  roomId: z.string(),
});

const insertEquipParams = equipData.omit({ id: true });
export type InsertEquipParams = z.infer<typeof insertEquipParams>;
export type EquipData = z.infer<typeof equipData>;
export type EquipForm = z.infer<typeof formData>;
