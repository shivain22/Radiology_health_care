import { NextResponse } from "next/server";
import { revalidatePath } from "next/cache";
import { z } from "zod";

import {
  createEmp,
  deleteEmp,
  updateEmp,
} from "@/lib/api/emps/mutations";
import { 
  empIdSchema,
  insertEmpParams,
  updateEmpParams 
} from "@/lib/db/schema/emps";

export async function POST(req: Request) {
  try {
    const validatedData = insertEmpParams.parse(await req.json());
    const { success } = await createEmp(validatedData);

    revalidatePath("/emps"); // optional - assumes you will have named route same as entity

    return NextResponse.json(success, { status: 201 });
  } catch (err) {
    if (err instanceof z.ZodError) {
      return NextResponse.json({ error: err.issues }, { status: 400 });
    } else {
      return NextResponse.json({ error: err }, { status: 500 });
    }
  }
}


export async function PUT(req: Request) {
  try {
    const { searchParams } = new URL(req.url);
    const id = searchParams.get("id");

    const validatedData = updateEmpParams.parse(await req.json());
    const validatedParams = empIdSchema.parse({ id });

    const { success } = await updateEmp(validatedParams.id, validatedData);

    return NextResponse.json(success, { status: 200 });
  } catch (err) {
    if (err instanceof z.ZodError) {
      return NextResponse.json({ error: err.issues }, { status: 400 });
    } else {
      return NextResponse.json(err, { status: 500 });
    }
  }
}

export async function DELETE(req: Request) {
  try {
    const { searchParams } = new URL(req.url);
    const id = searchParams.get("id");

    const validatedParams = empIdSchema.parse({ id });
    const { success } = await deleteEmp(validatedParams.id);

    return NextResponse.json(success, { status: 200 });
  } catch (err) {
    if (err instanceof z.ZodError) {
      return NextResponse.json({ error: err.issues }, { status: 400 });
    } else {
      return NextResponse.json(err, { status: 500 });
    }
  }
}
