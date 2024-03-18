import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rank from './rank';
import RankDetail from './rank-detail';
import RankUpdate from './rank-update';
import RankDeleteDialog from './rank-delete-dialog';

const RankRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rank />} />
    <Route path="new" element={<RankUpdate />} />
    <Route path=":id">
      <Route index element={<RankDetail />} />
      <Route path="edit" element={<RankUpdate />} />
      <Route path="delete" element={<RankDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RankRoutes;
