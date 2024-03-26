import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PatientTestTimings from './patient-test-timings';
import PatientTestTimingsDetail from './patient-test-timings-detail';
import PatientTestTimingsUpdate from './patient-test-timings-update';
import PatientTestTimingsDeleteDialog from './patient-test-timings-delete-dialog';

const PatientTestTimingsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PatientTestTimings />} />
    <Route path="new" element={<PatientTestTimingsUpdate />} />
    <Route path=":id">
      <Route index element={<PatientTestTimingsDetail />} />
      <Route path="edit" element={<PatientTestTimingsUpdate />} />
      <Route path="delete" element={<PatientTestTimingsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PatientTestTimingsRoutes;
