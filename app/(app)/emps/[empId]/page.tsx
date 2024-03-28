import { Suspense } from "react";
import { notFound } from "next/navigation";

import { getEmpById } from "@/lib/api/emps/queries";
import OptimisticEmp from "./OptimisticEmp";


import { BackButton } from "@/components/shared/BackButton";
import Loading from "@/app/loading";


export const revalidate = 0;

export default async function EmpPage({
  params,
}: {
  params: { empId: string };
}) {

  return (
    <main className="overflow-auto">
      <Emp id={params.empId} />
    </main>
  );
}

const Emp = async ({ id }: { id: string }) => {
  
  const { emp } = await getEmpById(id);
  

  if (!emp) notFound();
  return (
    <Suspense fallback={<Loading />}>
      <div className="relative">
        <BackButton currentResource="emps" />
        <OptimisticEmp emp={emp}  />
      </div>
    </Suspense>
  );
};
