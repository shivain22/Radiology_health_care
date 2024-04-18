import { z } from "zod";

const rankData = z.object({
  id: z.number(),
  name: z.string(),
  shortName:z.string().max(10),
  division:z.enum(["OTHERS","COMMISSIONED","NON_COMMISSIONED"]),
  empServiceId: z.number(),
});

export const formData = z.object({
  name: z.string(),
  shortName:z.string().max(10),
  division:z.enum(["OTHERS","COMMISSIONED","NON_COMMISSIONED"]),
  empServiceId: z.string(),
});

const insertRankParams = rankData.omit({ id: true });

// export const updateRankParams = dataSchema;

// export const rankIdSchema = dataSchema.pick({
//   id: true,
// });

// export type UpdateRankParams = z.infer<typeof updateRankParams>;

// export type RankId = z.infer<typeof rankIdSchema>["id"];

// export type NewRankParams = z.infer<typeof insertRankParams>;

export type RankedForm = z.infer<typeof formData>;
export type RankData = z.infer<typeof rankData>;
export type InsertRankParams = z.infer<typeof insertRankParams>;

export type TransformRankData={
  id:number;
  name:string;
  empServiceId?:number;
  empServiceName:string;
};
