import { getEmpById, getEmps } from "@/lib/api/emps/queries";
import { publicProcedure, router } from "@/lib/server/trpc";
import {
  empIdSchema,
  insertEmpParams,
  updateEmpParams,
} from "@/lib/db/schema/emps";
import { createEmp, deleteEmp, updateEmp } from "@/lib/api/emps/mutations";

export const empsRouter = router({
  getEmps: publicProcedure.query(async () => {
    return getEmps();
  }),
  getEmpById: publicProcedure.input(empIdSchema).query(async ({ input }) => {
    return getEmpById(input.id);
  }),
  createEmp: publicProcedure
    .input(insertEmpParams)
    .mutation(async ({ input }) => {
      return createEmp(input);
    }),
  updateEmp: publicProcedure
    .input(updateEmpParams)
    .mutation(async ({ input }) => {
      return updateEmp(input.id, input);
    }),
  deleteEmp: publicProcedure
    .input(empIdSchema)
    .mutation(async ({ input }) => {
      return deleteEmp(input.id);
    }),
});
