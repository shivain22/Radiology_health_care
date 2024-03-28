import { Suspense } from "react";

import Loading from "@/app/loading";
import EmpList from "@/components/emps/EmpList";
import { getEmps } from "@/lib/api/emps/queries";


export const revalidate = 0;

export default async function EmpsPage() {
  return (
    <main>
      <div className="relative">
        <div className="flex justify-between">
          <h1 className="font-semibold text-2xl my-2">Emps</h1>
        </div>
        <Emps />
      </div>
    </main>
  );
}

const Emps = async () => {
  
  const { emps } = await getEmps();
  
  return (
    <Suspense fallback={<Loading />}>
      <EmpList emps={emps}  />
    </Suspense>
  );
};
