import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PatientInfo from './patient-info';
import PatientInfoDetail from './patient-info-detail';
import PatientInfoUpdate from './patient-info-update';
import PatientInfoDeleteDialog from './patient-info-delete-dialog';

const PatientInfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PatientInfo />} />
    <Route path="new" element={<PatientInfoUpdate />} />
    <Route path=":id">
      <Route index element={<PatientInfoDetail />} />
      <Route path="edit" element={<PatientInfoUpdate />} />
      <Route path="delete" element={<PatientInfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PatientInfoRoutes;
