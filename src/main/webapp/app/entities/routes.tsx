import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Employee from './employee';
import Unit from './unit';
import Services from './services';
import Rank from './rank';
import PatientInfo from './patient-info';
import Rooms from './rooms';
import Equipments from './equipments';
import TestCatogories from './test-catogories';
import TestTimings from './test-timings';
import PatientTestInfo from './patient-test-info';
import TechicianEquipmentMapping from './techician-equipment-mapping';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="employee/*" element={<Employee />} />
        <Route path="unit/*" element={<Unit />} />
        <Route path="services/*" element={<Services />} />
        <Route path="rank/*" element={<Rank />} />
        <Route path="patient-info/*" element={<PatientInfo />} />
        <Route path="rooms/*" element={<Rooms />} />
        <Route path="equipments/*" element={<Equipments />} />
        <Route path="test-catogories/*" element={<TestCatogories />} />
        <Route path="test-timings/*" element={<TestTimings />} />
        <Route path="patient-test-info/*" element={<PatientTestInfo />} />
        <Route path="techician-equipment-mapping/*" element={<TechicianEquipmentMapping />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
