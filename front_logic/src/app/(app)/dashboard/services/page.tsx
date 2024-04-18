import Loading from '@/app/loading';

import { getServices } from '@/server_actions/(get-requests)/getServices'
import React, { Suspense } from 'react'
import ServiceList from './components/ServiceList';

export default async function ServicesPage() {
  return (
    <main>
      <div className="relative">
        <div className="flex justify-between">
          <h1 className="font-semibold text-2xl my-2">Services</h1>
        </div>
        <Services />
      </div>
    </main>
  )
}

const Services = async() => {
  const services = await getServices();
  return (
    <Suspense fallback={<Loading />}>

      {/* getting the data for the services and ranks for and displaying it in the form a table for the ranks */}
      <ServiceList services={services} />
     
     
    </Suspense>
  )
}
