import {z } from "zod";
// {
//     "id": 0,
//     "name": "string",
//     "age": 0,
//     "gender": "string",
//     "dateOfBirth": "string",
//     "mobile": 0,
//     "relation": "string",
//     "employeeIdId": 0,
//     "employeeHisNoId": "string",
//     "employeeServiceNoId": "string"
//   }

const PatientData = z.object({
    id: z.number(),
    name: z.string(),
    age: z.number(),
    gender: z.string(),
    dateOfBirth: z.string(),
    mobile: z.number(),
    relation: z.string(),
    employeeIdId: z.number(),
    employeeHisNoId: z.string(),
    employeeServiceNoId: z.string(),
});

export const formData = z.object({
    name: z.string(),
    age: z.string(),
    gender: z.string(),
    dateOfBirth: z.date(),
    mobile: z.string(),
    relation: z.string(),
    employeeIdId: z.string(),
    employeeHisNoId: z.string(),
    employeeServiceNoId: z.string(),
});


const insertPatientParams = PatientData.omit({ id: true });

export type PatientData = z.infer<typeof PatientData>;

export type Patientform = z.infer<typeof formData>;

export type InsertPatientParams = z.infer<typeof insertPatientParams>;