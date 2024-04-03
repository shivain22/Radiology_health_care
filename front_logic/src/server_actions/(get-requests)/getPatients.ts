import {cookies} from "next/headers";
import axios from "axios";

export const getPatients = async () => {

    const PatientsUrl = process.env.BACKEND_URL + "/api/patient-infos";
    const userAuthToken = cookies().get("authToken")?.value;
    const bearerToken = `Bearer ${userAuthToken}`;

    const response = await axios.get(PatientsUrl, {
        headers: {
            Authorization: bearerToken,
        },
    });
    const Patients = response.data
    return Patients;
}