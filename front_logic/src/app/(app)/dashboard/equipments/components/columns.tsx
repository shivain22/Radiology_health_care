"use client";

import { Button } from "@/components/ui/button";
import { EditButton } from "@/modules/shared/EditButton";
import { Pathname } from "@/modules/shared/Pathname";
import { EquipmentsData } from "@/schema/equipments";
import { ColumnDef } from "@tanstack/react-table";
// import Link from "next/link";
// import { usePathname } from "next/navigation";

export const columns: ColumnDef<EquipmentsData>[] = [
  // (alias) type EquipmentsData = {
  //     id: number;
  //     name: string;
  //     roomId: number;
  // }
  {
    accessorKey: "Index",
    header: () => (
      <div className="flex justify-center">
        <h1>Index</h1>
      </div>
    ),
    cell: (info) => {
      return <div className="flex justify-center">{info.row.index + 1}</div>;
    },
  },
  {
    accessorKey: "id",
    header: "Id",
  },
  {
    accessorKey : "name",
    header: "Name"
  },
  {
    //need to be change
    accessorKey: "roomId",
    header: "Room ID",
  }
  ,
  {
    accessorKey: "actions",
    header: () => (
      <div className="flex justify-center ">
        <h1>Actions</h1>
      </div>
    ),
    cell: ({ row }) => {
      // const pathname = usePathname();
      // const basePath = pathname.includes("equipments")
      //   ? pathname
      //   : pathname + "/patient-tests/";
        const basepath=Pathname({prop:"patient-tests"});

        const equipment = row.original;

        return (
            <div>
                <EditButton prop={{id:equipment.id}} basePath={basepath}/>
                <Button 
                    // onClick={() => deleteEquipmentsAction(equipment.id)}
                    variant={"destructive"}
                >
                    Delete
                </Button>
            </div>
        )
    },
  },
];
