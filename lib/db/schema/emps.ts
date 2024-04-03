import { boolean, varchar, mysqlTable } from "drizzle-orm/mysql-core";
import { createInsertSchema, createSelectSchema } from "drizzle-zod";
import { z } from "zod";

import { type getEmps } from "@/lib/api/emps/queries";

import { nanoid } from "@/lib/utils";


export const emps = mysqlTable('emps', {
  id: varchar("id", { length: 191 }).primaryKey().$defaultFn(() => nanoid()),
  technician: boolean("technician").notNull()
});


// Schema for emps - used to validate API requests
const baseSchema = createSelectSchema(emps)

export const insertEmpSchema = createInsertSchema(emps);
export const insertEmpParams = baseSchema.extend({
  technician: z.coerce.boolean()
}).omit({ 
  id: true
});

export const updateEmpSchema = baseSchema;
export const updateEmpParams = baseSchema.extend({
  technician: z.coerce.boolean()
})
export const empIdSchema = baseSchema.pick({ id: true });

// Types for emps - used to type API request params and within Components
export type Emp = typeof emps.$inferSelect;
export type NewEmp = z.infer<typeof insertEmpSchema>;
export type NewEmpParams = z.infer<typeof insertEmpParams>;
export type UpdateEmpParams = z.infer<typeof updateEmpParams>;
export type EmpId = z.infer<typeof empIdSchema>["id"];
    
// this type infers the return from getEmps() - meaning it will include any joins
export type CompleteEmp = Awaited<ReturnType<typeof getEmps>>["emps"][number];

