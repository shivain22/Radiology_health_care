import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TestTimings from './test-timings';
import TestTimingsDetail from './test-timings-detail';
import TestTimingsUpdate from './test-timings-update';
import TestTimingsDeleteDialog from './test-timings-delete-dialog';

const TestTimingsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TestTimings />} />
    <Route path="new" element={<TestTimingsUpdate />} />
    <Route path=":id">
      <Route index element={<TestTimingsDetail />} />
      <Route path="edit" element={<TestTimingsUpdate />} />
      <Route path="delete" element={<TestTimingsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TestTimingsRoutes;
