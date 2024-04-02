"use client";
import { ServiceData } from "@/schema/services";
import { UnitData } from "@/schema/units";
import { useState } from "react";
import Modal from "../shared/Modal";
import { UnitForm } from "./UnitForm";
import { Button } from "@/components/ui/button";
import { usePathname } from "next/navigation";
import Link from "next/link";
import { cn } from "@/lib/utils";
import { PlusIcon } from "lucide-react";
import { deleteUnitAction } from "@/server_actions/actions/units";
import { DataTable } from "./data-table";
import { columns } from "./columns";

export type TOpenModal = (unit?: UnitData) => void;

export default function UnitList({
  units,
  services,
  serviceId,
}: {
  units: UnitData[];
  services: ServiceData[];
  serviceId?: number;
}) {
  const [open, setOpen] = useState(false);
  const [activeUnit, setActiveUnit] = useState<UnitData | null>(null);

  const openModal = (unit?: UnitData) => {
    setOpen(true);
    unit && setActiveUnit(unit);
  };

  const closeModal = () => {
    setOpen(false);
  };

  return (
    <div>
      <Modal
        open={open}
        setOpen={setOpen}
        title={activeUnit ? "Edit Unit" : "Add Unit"}
      >
        <div>
          <UnitForm
            unit={activeUnit}
            closeModal={closeModal}
            openModal={openModal}
            services={services}
            serviceId={serviceId}
          />
        </div>
      </Modal>
      <div className="absolute right-0 top-0">
        <Button onClick={() => openModal()} variant={"outline"}>
          +
        </Button>
      </div>
      <DataTable
        columns={columns}
        data={units}
        openModal={()=>openModal()}/>
      </div>
  );
}



