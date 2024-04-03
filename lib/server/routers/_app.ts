import { computersRouter } from "./computers";
import { router } from "@/lib/server/trpc";
import { servicesRouter } from "./services";
import { ranksRouter } from "./ranks";
import { unitsRouter } from "./units";
import { employeesRouter } from "./employees";
import { empsRouter } from "./emps";

export const appRouter = router({
  computers: computersRouter,
  services: servicesRouter,
  ranks: ranksRouter,
  units: unitsRouter,
  employees: employeesRouter,
  emps: empsRouter,
});

export type AppRouter = typeof appRouter;
