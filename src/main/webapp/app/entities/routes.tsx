import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Room from './room';
import EmpService from './emp-service';
import Equipment from './equipment';
import Rank from './rank';
import Unit from './unit';
import Employee from './employee';
import TechnicianEquipmentMapping from './technician-equipment-mapping';
import PatientInfo from './patient-info';
import PatientTestTimings from './patient-test-timings';
import TestCategories from './test-categories';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="room/*" element={<Room />} />
        <Route path="emp-service/*" element={<EmpService />} />
        <Route path="equipment/*" element={<Equipment />} />
        <Route path="rank/*" element={<Rank />} />
        <Route path="unit/*" element={<Unit />} />
        <Route path="employee/*" element={<Employee />} />
        <Route path="technician-equipment-mapping/*" element={<TechnicianEquipmentMapping />} />
        <Route path="patient-info/*" element={<PatientInfo />} />
        <Route path="patient-test-timings/*" element={<PatientTestTimings />} />
        <Route path="test-categories/*" element={<TestCategories />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
