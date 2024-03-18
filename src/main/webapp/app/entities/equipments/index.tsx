import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Equipments from './equipments';
import EquipmentsDetail from './equipments-detail';
import EquipmentsUpdate from './equipments-update';
import EquipmentsDeleteDialog from './equipments-delete-dialog';

const EquipmentsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Equipments />} />
    <Route path="new" element={<EquipmentsUpdate />} />
    <Route path=":id">
      <Route index element={<EquipmentsDetail />} />
      <Route path="edit" element={<EquipmentsUpdate />} />
      <Route path="delete" element={<EquipmentsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EquipmentsRoutes;
