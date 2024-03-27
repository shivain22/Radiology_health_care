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

type NOpenModal = (unit?: UnitData) => void;

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
      {units.length === 0 ? (
        <EmptyState openModal={openModal} />
      ) : (
        <ul>
          {units.map((unit) => (
            <Unit unit={unit} key={unit.id} openModal={openModal} />
          ))}
        </ul>
      )}
    </div>
  );
}

const Unit = ({
  unit,
  openModal,
}: {
  unit: UnitData;
  openModal: NOpenModal;
}) => {
  const pathname = usePathname();
  const basePath = pathname.includes("units") ? pathname : pathname + "/units/";

  return (
    <li className={cn("flex justify-between my-2")}>
      <div className="w-full">
        <div>{unit.name}</div>
      </div>
      <Button variant={"link"} asChild>
        <Link href={basePath + "/" + unit.id}>Edit</Link>
      </Button>
    </li>
  );
};

const EmptyState = ({ openModal }: { openModal: NOpenModal }) => {
  return (
    <div className="text-center">
      <h3 className="mt-2 text-sm font-semibold text-secondary-foreground">
        No units
      </h3>
      <p className="mt-1 text-sm text-muted-foreground">
        Get started by creating a new unit.
      </p>
      <div className="mt-6">
        <Button onClick={() => openModal()}>
          <PlusIcon className="h-4" /> New Units{" "}
        </Button>
      </div>
    </div>
  );
};
