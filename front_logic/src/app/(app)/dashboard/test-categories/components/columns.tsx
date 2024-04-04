"use client";

import { Button } from "@/components/ui/button";
import { PatientTestsData } from "@/schema/patient-tests";
import { TestCategoryData } from "@/schema/testcategory";
import { ColumnDef } from "@tanstack/react-table";
// import Link from "next/link";
// import { usePathname } from "next/navigation";


export const columns: ColumnDef<TestCategoryData>[] = [
    {
        accessorKey: "Index",
        header: "Index", 
        cell: (info) => {
            return <div className="flex justify-center">{info.row.index + 1}</div>;
        }
    },
    {
        accessorKey: "testName",
        header: "Test Name",

    },
    {
        accessorKey: "equipmentId",
        header: "Equipment Id",
    },
    {
        accessorKey: "parentTestCategoryId",
        header: "Parent Test Category Id",
    },
    {
        accessorKey: "actions",
        header: "Actions",
        cell: ({ row }) => {
            // const pathname = usePathname();
            // const basePath = pathname.includes("patient-tests")
            //   ? pathname
            //   : pathname + "/patient-tests/";
              const patientTests = row.original;
      
              return (
                  <div>
                      {/* <Button variant={"link"} asChild>
                          <Link href={basePath + "/" + patientTests.id}>Edit</Link> 
                      </Button> */}
                      <Button 
                          // onClick={() => deletePatientTestsAction(patientTests.id)}
                          variant={"destructive"}
                      >
                          Delete
                      </Button>
                  </div>
              )
          },
    }


]