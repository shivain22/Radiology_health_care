import Loading from '@/app/loading';
import EmployeeList from '@/modules/employees/EmployeeList';
import { getEmployees } from '@/server_actions/(get-requests)/getEmployess';
import { getRanks } from '@/server_actions/(get-requests)/getRanks';
import { getServices } from '@/server_actions/(get-requests)/getServices';
import { getUnits } from '@/server_actions/(get-requests)/getUnits';
import React, { Suspense } from 'react'

const EmployeesPage = () => {
  return (
    <main>
      <div className="relative">
        <div className="flex justify-between">
          <h1 className="font-semibold text-2xl my-2">Employees</h1>
        </div>
        <Employees />
      </div>
    </main>
  );
}

export default EmployeesPage


const Employees = async () => {

  const employees = await getEmployees();
  const services = await getServices();
  const ranks = await getRanks();
  const units = await getUnits();  


  return (
    <Suspense fallback={<Loading />}>


    {/* getting the data for the services and ranks for and displaying it in the form a table for the ranks */}
    {/* <RankList ranks={ranks} services={services} /> */}
    <h1>Employees</h1>
   <EmployeeList employees={employees} services={services} ranks={ranks} units={units} />
   
  </Suspense>

  )
}
