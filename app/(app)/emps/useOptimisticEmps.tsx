
import { type Emp, type CompleteEmp } from "@/lib/db/schema/emps";
import { OptimisticAction } from "@/lib/utils";
import { useOptimistic } from "react";

export type TAddOptimistic = (action: OptimisticAction<Emp>) => void;

export const useOptimisticEmps = (
  emps: CompleteEmp[],
  
) => {
  const [optimisticEmps, addOptimisticEmp] = useOptimistic(
    emps,
    (
      currentState: CompleteEmp[],
      action: OptimisticAction<Emp>,
    ): CompleteEmp[] => {
      const { data } = action;

      

      const optimisticEmp = {
        ...data,
        
        id: "optimistic",
      };

      switch (action.action) {
        case "create":
          return currentState.length === 0
            ? [optimisticEmp]
            : [...currentState, optimisticEmp];
        case "update":
          return currentState.map((item) =>
            item.id === data.id ? { ...item, ...optimisticEmp } : item,
          );
        case "delete":
          return currentState.map((item) =>
            item.id === data.id ? { ...item, id: "delete" } : item,
          );
        default:
          return currentState;
      }
    },
  );

  return { addOptimisticEmp, optimisticEmps };
};
