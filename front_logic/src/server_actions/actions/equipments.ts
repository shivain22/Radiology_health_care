import { InsertEquipParams } from "@/schema/equipment";
import axios from "axios";
import { revalidatePath } from "next/cache";
import { cookies } from "next/headers";

const equipUrl=process.env.BACKEND_URL+"/api/equipment";
const userAuthToken=cookies().get("accessToken")?.value;
const bearerToken=`Bearer ${userAuthToken}`;

const handleErrors=(e:unknown)=>{
    const errMsg="Error,please try again";
    if(e instanceof Error)return e.message.length>0?e.message:errMsg;
    if(e && typeof e==="object"&& "error"in e){
        const errAsStr=e.error as string;
        return errAsStr.length>0?errAsStr:errMsg;
    }

    return errMsg;
}

const revalidatEquip=()=>revalidatePath("/equipment");

export const createEquipAction=async(values:InsertEquipParams)=>{
    try{
        const response=await axios.post(
            equipUrl,
            {
                name:values.name,
                roomId:values.roomId,
            },
            {
                headers:{
                    Authorization:bearerToken,
                }
            }
        );
        if(response.status===201){
            revalidatEquip();
            console.log("Equipments added successfully");
        }
    } catch(e){
        return handleErrors(e);
    }
};

export const deleteEquipAction=async(id:number)=>{
    try{
        const response=await axios.delete(
            equipUrl+"/"+id,
            {
                headers:{
                    Authorization:bearerToken,
                }
            }
        );
        if(response.status===204){
            revalidatEquip();
            console.log("Equipments deleted successfully");
        }
    }catch(e){
        return handleErrors(e);
    }
}