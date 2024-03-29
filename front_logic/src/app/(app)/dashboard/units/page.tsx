import Loading from '@/app/loading';
import UnitList from '@/modules/units/UnitList';

import { getServices } from '@/server_actions/(get-requests)/getServices';
import { getUnits } from '@/server_actions/(get-requests)/getUnits';
import React from 'react'
import { Suspense } from "react";


export default async function UnitsPage() {
  return (
    <main>
      <div className="relative">
        <div className="flex justify-between">
          <h1 className="font-semibold text-2xl my-2">Units</h1>
        </div>
        <Units />
      </div>
    </main>
  );
}


const Units = async () => {
  

  //getting the data for the ranks and 
  const units = await getUnits();
  const services = await getServices();
  // console.log(services)
  // const { services } = await getServices();
  return (
    <Suspense fallback={<Loading />}>

      {/* getting the data for the services and ranks for and displaying it in the form a table for the ranks */}
      {/* <RankList ranks={ranks} services={services} /> */}
      <h1>Services and Units</h1>
      <UnitList units={units} services={services} />
     
    </Suspense>
  );
};
