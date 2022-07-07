import { Route, Routes } from 'react-router-dom';

import KkogkkogListPage from '@/@pages/kkogkkog-list';
import KkogkkogCreatePage from '@/@pages/kkogkkog-list/create';
import LandingPage from '@/@pages/landing';

export const PATH = {
  LANDING: '/',
  KKOGKKOG_LIST: '/kkogkkog-list',
  KKOGKKOG_CREATE: '/kkogkkog-list/create',
  // KKOGKKOG_DETAIL: '/kkogkkog-list/:id',
};

const Router = () => {
  return (
    <Routes>
      <Route path={PATH.LANDING} element={<LandingPage />} />
      <Route path={PATH.KKOGKKOG_LIST} element={<KkogkkogListPage />} />
      <Route path={PATH.KKOGKKOG_CREATE} element={<KkogkkogCreatePage />} />
    </Routes>
  );
};

export default Router;
