import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rooms from './rooms';
import RoomsDetail from './rooms-detail';
import RoomsUpdate from './rooms-update';
import RoomsDeleteDialog from './rooms-delete-dialog';

const RoomsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rooms />} />
    <Route path="new" element={<RoomsUpdate />} />
    <Route path=":id">
      <Route index element={<RoomsDetail />} />
      <Route path="edit" element={<RoomsUpdate />} />
      <Route path="delete" element={<RoomsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RoomsRoutes;
