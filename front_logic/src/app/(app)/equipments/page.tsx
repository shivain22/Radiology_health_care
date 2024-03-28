import { getEquip } from '@/server_actions/(get-requests)/getEquip';
import React from 'react'

export default async function EquipPage () {
  return (
    <main>
    <div className="relative">
          <div className="flex justify-between">
            <h1 className="font-semibold text-2xl my-2">Equipment</h1>
          </div>
          <Equip/>
        </div>
    </main>
  );
}

const Equip=async()=>{
    const equip=await getEquip();
    console.log(equip);

    return(
        <h1>Equipment</h1>
    )
}

