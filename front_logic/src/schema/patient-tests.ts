import { z } from "zod";


// {
//     "id": 0,
//     "testTimings": "2024-04-01",
//     "priority": "string",
//     "clinicalNote": "string",
//     "spclInstruction": "string",
//     "patientInfoId": 0,
//     "testCategoriesId": 0,
//     "login": "string",
//     "createdBy": "string",
//     "createdDate": "2024-04-01T07:35:56.289Z",
//     "lastModifiedBy": "string",
//     "lastModifiedDate": "2024-04-01T07:35:56.289Z"
//   }

const PatientTestsData = z.object({
    id: z.number(),
    testTimings: z.string(),
    priority : z.string(),
    clinicalNote: z.string(),
    spclInstruction: z.string(),
    patientInfoId: z.number(),
    testCategoriesId: z.number()
})

const formData = z.object({
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
