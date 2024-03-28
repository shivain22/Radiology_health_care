"use client";

import { useOptimistic, useState } from "react";
import { TAddOptimistic } from "@/app/(app)/emps/useOptimisticEmps";
import { type Emp } from "@/lib/db/schema/emps";
import { cn } from "@/lib/utils";

import { Button } from "@/components/ui/button";
import Modal from "@/components/shared/Modal";
import EmpForm from "@/components/emps/EmpForm";


export default function OptimisticEmp({ 
  emp,
   
}: { 
  emp: Emp; 
  
  
}) {
  const [open, setOpen] = useState(false);
  const openModal = (_?: Emp) => {
    setOpen(true);
  };
  const closeModal = () => setOpen(false);
  const [optimisticEmp, setOptimisticEmp] = useOptimistic(emp);
  const updateEmp: TAddOptimistic = (input) =>
    setOptimisticEmp({ ...input.data });

  return (
    <div className="m-4">
      <Modal open={open} setOpen={setOpen}>
        <EmpForm
          emp={optimisticEmp}
          
          closeModal={closeModal}
          openModal={openModal}
          addOptimistic={updateEmp}
        />
      </Modal>
      <div className="flex justify-between items-end mb-4">
        <h1 className="font-semibold text-2xl">{optimisticEmp.technician}</h1>
        <Button className="" onClick={() => setOpen(true)}>
          Edit
        </Button>
      </div>
      <pre
        className={cn(
          "bg-secondary p-4 rounded-lg break-all text-wrap",
          optimisticEmp.id === "optimistic" ? "animate-pulse" : "",
        )}
      >
        {JSON.stringify(optimisticEmp, null, 2)}
      </pre>
    </div>
  );
}
