import { z } from "zod";


const PatientTestsData = z.object({
    id: z.number(),
    testTimings: z.string(),
    priority : z.string(),
    clinicalNote: z.string(),
    spclInstruction: z.string(),
    patientInfoId: z.number(),
    testCategoriesId: z.number()
})

export const formData = z.object({
    testTimings: z.string(),
    priority : z.string(),
    clinicalNote: z.string(),
    spclInstruction: z.string(),
    patientInfoId: z.string(),
    testCategoriesId: z.string()
})

const insertPatientTestsParams = PatientTestsData.omit({ id: true });

export type PatientTestsform = z.infer<typeof formData>;
export type PatientTestsData = z.infer<typeof PatientTestsData>


