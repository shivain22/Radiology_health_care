import Loading from "@/app/loading";
import { getPatientTests } from "@/server_actions/(get-requests)/getPatientTests";
import React, { Suspense } from "react";
import PatientTestsList from "./components/PatientTestsList";
import { userAuthToken } from "@/server_actions/utils/getcookies";
import { getPatients } from "@/server_actions/(get-requests)/getPatients";
import { getTestCategories } from "@/server_actions/(get-requests)/getTestCategories";
import { TestCategoryData } from "@/schema/testcategory";
import { PatientData } from "@/schema/patients";
import {
  PatientTestsData,
  TransformPatientTestsData,
} from "@/schema/patient-tests";

const PatientTestsPage = () => {
  return (
    <main>
      <div className="relative">
        <div className="flex justify-between">
          <h1 className="font-semibold text-2xl my-2">Patient Tests</h1>
        </div>
        <PatientTests />
      </div>
    </main>
  );
};

export default PatientTestsPage;

const PatientTests = async () => {
  const patientTestsInfo = await getPatientTests();

  const patientInfo = await getPatients();
  const TestsInfo = await getTestCategories();

  const testMap = new Map<number, string>(
    TestsInfo.map((testinfo: TestCategoryData) => [
      testinfo.id,
      testinfo.testName,
    ])
  );

  const patientMap = new Map<number, string>(
    patientInfo.map((patientInfo: PatientData) => [
      patientInfo.id,
      patientInfo.name,
    ])
  );

  const transformPatientTestsData = (
    patientTest: PatientTestsData,
    patientMap: Map<number, string>,
    testMap: Map<number, string>
  ): TransformPatientTestsData => {
    const transformedPatientTest: TransformPatientTestsData = {
      ...patientTest,
      patientName: patientMap.get(patientTest.patientInfoId) || "",
      testName: testMap.get(patientTest.testCategoriesId) || "",
    };

    return transformedPatientTest;
  };

  const transformedPatientTests: TransformPatientTestsData[] =
    patientTestsInfo.map((patientTest: PatientTestsData) =>
      transformPatientTestsData(patientTest, patientMap, testMap)
    );
  return (
    <Suspense fallback={<Loading />}>
      <PatientTestsList patientTests={transformedPatientTests} token={userAuthToken} />
    </Suspense>
  );
};
