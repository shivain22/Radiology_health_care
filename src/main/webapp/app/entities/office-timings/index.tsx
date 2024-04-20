import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import OfficeTimings from './office-timings';
import OfficeTimingsDetail from './office-timings-detail';
import OfficeTimingsUpdate from './office-timings-update';
import OfficeTimingsDeleteDialog from './office-timings-delete-dialog';

const OfficeTimingsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<OfficeTimings />} />
    <Route path="new" element={<OfficeTimingsUpdate />} />
    <Route path=":id">
      <Route index element={<OfficeTimingsDetail />} />
      <Route path="edit" element={<OfficeTimingsUpdate />} />
      <Route path="delete" element={<OfficeTimingsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OfficeTimingsRoutes;
