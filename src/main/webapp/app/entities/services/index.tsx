import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Services from './services';
import ServicesDetail from './services-detail';
import ServicesUpdate from './services-update';
import ServicesDeleteDialog from './services-delete-dialog';

const ServicesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Services />} />
    <Route path="new" element={<ServicesUpdate />} />
    <Route path=":id">
      <Route index element={<ServicesDetail />} />
      <Route path="edit" element={<ServicesUpdate />} />
      <Route path="delete" element={<ServicesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServicesRoutes;
