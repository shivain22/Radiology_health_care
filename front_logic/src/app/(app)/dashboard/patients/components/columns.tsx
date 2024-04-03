"use client"

import { PatientData } from "@/schema/patients"
import { ColumnDef } from "@tanstack/react-table"

export const columns: ColumnDef<PatientData>[] = [
  
    {
        accessorKey: "name",
        header: "Name",
    },
    {
        accessorKey: "age",
        header: "Age",
        
    },
    {
        accessorKey: "gender",
        header: "Gender",
    },
    {
        accessorKey: "dateOfBirth",
        header: "Date of Birth",
    },
    {
        accessorKey: "mobile",
        header: "Mobile",
    },
    {
        accessorKey: "relation",
        header: "Relation",
    },
    
   
]