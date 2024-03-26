import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TechnicianEquipmentMapping from './technician-equipment-mapping';
import TechnicianEquipmentMappingDetail from './technician-equipment-mapping-detail';
import TechnicianEquipmentMappingUpdate from './technician-equipment-mapping-update';
import TechnicianEquipmentMappingDeleteDialog from './technician-equipment-mapping-delete-dialog';

const TechnicianEquipmentMappingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TechnicianEquipmentMapping />} />
    <Route path="new" element={<TechnicianEquipmentMappingUpdate />} />
    <Route path=":id">
      <Route index element={<TechnicianEquipmentMappingDetail />} />
      <Route path="edit" element={<TechnicianEquipmentMappingUpdate />} />
      <Route path="delete" element={<TechnicianEquipmentMappingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TechnicianEquipmentMappingRoutes;
