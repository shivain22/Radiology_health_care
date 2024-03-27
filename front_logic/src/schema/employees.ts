import { z } from "zod";

export const empData=z.object({
    id:z.number(),
    name:z.string(),
    technician:z.boolean(),
    his:z.string(),
    serviceNo:z.string(),
    rankId:z.number(),
    empServiceId:z.number(),
    unitId:z.number(),
});

export const formData=z.object({
    name:z.string(),
    technician:z.boolean(),
    his:z.string(),
    serviceNo:z.string(),
    rankId:z.string(),
    empServiceId:z.string(),
    unitId:z.string(),
});

const insertEmpParams=empData.omit({id:true});

export type InsertEmpParams=z.infer< typeof insertEmpParams>;

export type EmpData=z.infer<typeof empData>;

export type Empform=z.infer<typeof formData>;
