"use client";

import { useState } from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";

import { cn } from "@/lib/utils";
import { type Emp, CompleteEmp } from "@/lib/db/schema/emps";
import Modal from "@/components/shared/Modal";

import { useOptimisticEmps } from "@/app/(app)/emps/useOptimisticEmps";
import { Button } from "@/components/ui/button";
import EmpForm from "./EmpForm";
import { PlusIcon } from "lucide-react";

type TOpenModal = (emp?: Emp) => void;

export default function EmpList({
  emps,
   
}: {
  emps: CompleteEmp[];
   
}) {
  const { optimisticEmps, addOptimisticEmp } = useOptimisticEmps(
    emps,
     
  );
  const [open, setOpen] = useState(false);
  const [activeEmp, setActiveEmp] = useState<Emp | null>(null);
  const openModal = (emp?: Emp) => {
    setOpen(true);
    emp ? setActiveEmp(emp) : setActiveEmp(null);
  };
  const closeModal = () => setOpen(false);

  return (
    <div>
      <Modal
        open={open}
        setOpen={setOpen}
        title={activeEmp ? "Edit Emp" : "Create Emp"}
      >
        <EmpForm
          emp={activeEmp}
          addOptimistic={addOptimisticEmp}
          openModal={openModal}
          closeModal={closeModal}
          
        />
      </Modal>
      <div className="absolute right-0 top-0 ">
        <Button onClick={() => openModal()} variant={"outline"}>
          +
        </Button>
      </div>
      {optimisticEmps.length === 0 ? (
        <EmptyState openModal={openModal} />
      ) : (
        <ul>
          {optimisticEmps.map((emp) => (
            <Emp
              emp={emp}
              key={emp.id}
              openModal={openModal}
            />
          ))}
        </ul>
      )}
    </div>
  );
}

const Emp = ({
  emp,
  openModal,
}: {
  emp: CompleteEmp;
  openModal: TOpenModal;
}) => {
  const optimistic = emp.id === "optimistic";
  const deleting = emp.id === "delete";
  const mutating = optimistic || deleting;
  const pathname = usePathname();
  const basePath = pathname.includes("emps")
    ? pathname
    : pathname + "/emps/";


  return (
    <li
      className={cn(
        "flex justify-between my-2",
        mutating ? "opacity-30 animate-pulse" : "",
        deleting ? "text-destructive" : "",
      )}
    >
      <div className="w-full">
        <div>{emp.technician}</div>
      </div>
      <Button variant={"link"} asChild>
        <Link href={ basePath + "/" + emp.id }>
          Edit
        </Link>
      </Button>
    </li>
  );
};

const EmptyState = ({ openModal }: { openModal: TOpenModal }) => {
  return (
    <div className="text-center">
      <h3 className="mt-2 text-sm font-semibold text-secondary-foreground">
        No emps
      </h3>
      <p className="mt-1 text-sm text-muted-foreground">
        Get started by creating a new emp.
      </p>
      <div className="mt-6">
        <Button onClick={() => openModal()}>
          <PlusIcon className="h-4" /> New Emps </Button>
      </div>
    </div>
  );
};
