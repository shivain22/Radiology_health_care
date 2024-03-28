"use client";

import { useState } from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";

import { cn } from "@/lib/utils";

import Modal from "../shared/Modal";

import { PlusIcon } from "lucide-react";
import { Button } from "@/components/ui/button";
import { EmployeeData } from "@/schema/employees";
import { ServiceData } from "@/schema/services";
import { RankData } from "@/schema/ranks";
import { UnitData } from "@/schema/units";
import { deleteEmployeeAction } from "@/server_actions/actions/employee";
import EmployeeForm from "./EmployeeForm";

type TOpenModal = (employee?: EmployeeData) => void;

export default function EmployeeList({
  employees,
  services,
  serviceId,
  ranks,
  rankId,
  units,
  unitId,
}: {
  employees: EmployeeData[];
  services: ServiceData[];
  serviceId?: number;
  ranks: RankData[];
  rankId?: number;
  units: UnitData[];
  unitId?: number;
}) {
  const [open, setOpen] = useState(false);
  const [activeEmployee, setActiveEmployee] = useState<EmployeeData | null>(
    null
  );
  const openModal = (employee?: EmployeeData) => {
    setOpen(true);
    employee ? setActiveEmployee(employee) : setActiveEmployee(null);
  };
  const closeModal = () => setOpen(false);

  return (
    <div>
      <Modal
        open={open}
        setOpen={setOpen}
        title={activeEmployee ? "Edit Employee" : "Create Employee"}
      >
        <EmployeeForm
          employee={activeEmployee}
          openModal={openModal}
          closeModal={closeModal}
          services={services}
          serviceId={serviceId}
          ranks={ranks}
          rankId={rankId}
          units={units}
          unitId={unitId}
        />
      </Modal>
      <div className="absolute right-0 top-0 ">
        <Button onClick={() => openModal()} variant={"outline"}>
          +
        </Button>
      </div>
      {employees.length === 0 ? (
        <EmptyState openModal={openModal} />
      ) : (
        <ul>
          {employees.map((employee) => (
            <Employee
              employee={employee}
              key={employee.id}
              openModal={openModal}
            />
          ))}
        </ul>
      )}
    </div>
  );
}

const Employee = ({
  employee,
  openModal,
}: {
  employee: EmployeeData;
  openModal: TOpenModal;
}) => {
  const pathname = usePathname();
  const basePath = pathname.includes("employees")
    ? pathname
    : pathname + "/employees/";

  return (
    <li className={cn("flex justify-between my-2")}>
      <div className="w-full flex gap-2">
        <h1>Name:</h1>
        <div>{employee.name}</div>-<h1>Employee His :</h1>
        <div>{employee.his}</div>
        <h1>Employee Service No :</h1>
        <div>{employee.serviceNo}</div>
        <h1>Employee service Id :</h1>
        <div>{employee.empServiceId}</div>
      </div>
      <div className="flex gap-2 mr-5">
        <Button variant={"link"} asChild>
          <Link href={basePath + "/" + employee.id}>Edit</Link>
        </Button>
        <Button
          onClick={() => deleteEmployeeAction(employee.id)}
          variant={"destructive"}
        >
          Delete
        </Button>
      </div>
    </li>
  );
};

const EmptyState = ({ openModal }: { openModal: TOpenModal }) => {
  return (
    <div className="text-center">
      <h3 className="mt-2 text-sm font-semibold text-secondary-foreground">
        No employees
      </h3>
      <p className="mt-1 text-sm text-muted-foreground">
        Get started by creating a new employee.
      </p>
      <div className="mt-6">
        <Button onClick={() => openModal()}>
          <PlusIcon className="h-4" /> New Employees{" "}
        </Button>
      </div>
    </div>
  );
};
