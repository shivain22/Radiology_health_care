import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PatientTestInfo from './patient-test-info';
import PatientTestInfoDetail from './patient-test-info-detail';
import PatientTestInfoUpdate from './patient-test-info-update';
import PatientTestInfoDeleteDialog from './patient-test-info-delete-dialog';

const PatientTestInfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PatientTestInfo />} />
    <Route path="new" element={<PatientTestInfoUpdate />} />
    <Route path=":id">
      <Route index element={<PatientTestInfoDetail />} />
      <Route path="edit" element={<PatientTestInfoUpdate />} />
      <Route path="delete" element={<PatientTestInfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PatientTestInfoRoutes;
