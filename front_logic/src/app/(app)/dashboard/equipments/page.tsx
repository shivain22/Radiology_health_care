import { getEquipments } from '@/server_actions/(get-requests)/getEquiments';
import React from 'react'

export default async function EquipPage () {
  return (
    <main>
    <div className="relative">
          <div className="flex justify-between">
            <h1 className="font-semibold text-2xl my-2">Equipment</h1>
          </div>
          <Equipment/>
        </div>
    </main>
  );
}

const Equipment=async()=>{
    const equipments=await getEquipments();
    console.log(equipments);

    return(
        <h1>Equipment</h1>
    )
}

