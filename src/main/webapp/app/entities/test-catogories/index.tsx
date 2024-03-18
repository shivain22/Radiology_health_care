import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TestCatogories from './test-catogories';
import TestCatogoriesDetail from './test-catogories-detail';
import TestCatogoriesUpdate from './test-catogories-update';
import TestCatogoriesDeleteDialog from './test-catogories-delete-dialog';

const TestCatogoriesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TestCatogories />} />
    <Route path="new" element={<TestCatogoriesUpdate />} />
    <Route path=":id">
      <Route index element={<TestCatogoriesDetail />} />
      <Route path="edit" element={<TestCatogoriesUpdate />} />
      <Route path="delete" element={<TestCatogoriesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TestCatogoriesRoutes;
