import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmpService from './emp-service';
import EmpServiceDetail from './emp-service-detail';
import EmpServiceUpdate from './emp-service-update';
import EmpServiceDeleteDialog from './emp-service-delete-dialog';

const EmpServiceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmpService />} />
    <Route path="new" element={<EmpServiceUpdate />} />
    <Route path=":id">
      <Route index element={<EmpServiceDetail />} />
      <Route path="edit" element={<EmpServiceUpdate />} />
      <Route path="delete" element={<EmpServiceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmpServiceRoutes;
