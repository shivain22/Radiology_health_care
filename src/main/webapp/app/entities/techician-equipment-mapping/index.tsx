import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TechicianEquipmentMapping from './techician-equipment-mapping';
import TechicianEquipmentMappingDetail from './techician-equipment-mapping-detail';
import TechicianEquipmentMappingUpdate from './techician-equipment-mapping-update';
import TechicianEquipmentMappingDeleteDialog from './techician-equipment-mapping-delete-dialog';

const TechicianEquipmentMappingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TechicianEquipmentMapping />} />
    <Route path="new" element={<TechicianEquipmentMappingUpdate />} />
    <Route path=":id">
      <Route index element={<TechicianEquipmentMappingDetail />} />
      <Route path="edit" element={<TechicianEquipmentMappingUpdate />} />
      <Route path="delete" element={<TechicianEquipmentMappingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TechicianEquipmentMappingRoutes;
