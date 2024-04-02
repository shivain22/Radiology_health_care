"use client";

import { Button } from "@/components/ui/button";
import { PatientTestsData } from "@/schema/patient-tests";
import { ColumnDef } from "@tanstack/react-table";
import Link from "next/link";
import { usePathname } from "next/navigation";

export const columns: ColumnDef<PatientTestsData>[] = [
  {
    accessorKey: "Index",
    header: () => (
      <div className="flex justify-center">
        <h1>Index</h1>
      </div>
    ),
    cell: (info) => {
      return <div className="flex justfy-center">{info.row.index + 1}</div>;
    },
  },
  {
    accessorKey: "testTimings",
    header: "Test Timings",
  },
  {
    accessorKey: "priority",
    header: "Priority",
  },
  {
    accessorKey: "clinicalNote",
    header: "Clinical Note",
  },
  //   {
  //     accessorKey: "status",
  //     header: "Status",
  //   },
  {
    accessorKey: "spclInstruction",
    header: "Special Instruction",
  },
  {
    accessorKey: "patientInfoId",
    //this will be later replaced with the name or will remain the same
    header: "Patient Info Id ",
  },
  {
    accessorKey: "testCategoriesId",
    //this will be later replaced with the name
    header: "Test Categories Id ",
  },
  {
    accessorKey: "actions",
    header: () => (
      <div className="flex justify-center">
        <h1>Actions</h1>
      </div>
    ),
    cell: ({ row }) => {
      const pathname = usePathname();
      const basePath = pathname.includes("patient-tests")
        ? pathname
        : pathname + "/patient-tests/";
        const patientTests = row.original;

        return (
            <div>
                <Button variant={"link"} asChild>
                    <Link href={basePath + "/" + patientTests.id}>Edit</Link> 
                </Button>
                <Button 
                    // onClick={() => deletePatientTestsAction(patientTests.id)}
                    variant={"destructive"}
                >
                    Delete
                </Button>
            </div>
        )
    },
  },
];
