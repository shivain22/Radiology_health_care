import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TestCategories from './test-categories';
import TestCategoriesDetail from './test-categories-detail';
import TestCategoriesUpdate from './test-categories-update';
import TestCategoriesDeleteDialog from './test-categories-delete-dialog';

const TestCategoriesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TestCategories />} />
    <Route path="new" element={<TestCategoriesUpdate />} />
    <Route path=":id">
      <Route index element={<TestCategoriesDetail />} />
      <Route path="edit" element={<TestCategoriesUpdate />} />
      <Route path="delete" element={<TestCategoriesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TestCategoriesRoutes;
