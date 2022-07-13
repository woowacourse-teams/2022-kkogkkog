import { Suspense } from 'react';
import { Route, Routes } from 'react-router-dom';

import KkogkkogListPage from '@/@pages/kkogkkog-list';
import KkogkkogCreatePage from '@/@pages/kkogkkog-list/create';
import LandingPage from '@/@pages/landing';

import Login from './@pages/login';
import Signup from './@pages/signup';

export const PATH = {
  LANDING: '/',
  KKOGKKOG_LIST: '/kkogkkog-list',
  KKOGKKOG_CREATE: '/kkogkkog-list/create',
  LOGIN: '/login',
  SIGNUP: '/signup',
  // KKOGKKOG_DETAIL: '/kkogkkog-list/:id',
};

const Router = () => {
  return (
    <Routes>
      <Route path={PATH.LANDING} element={<LandingPage />} />
      <Route
        path={PATH.KKOGKKOG_LIST}
        element={
          <Suspense fallback={<KkogkkogListPage.Skeleton />}>
            <KkogkkogListPage />
          </Suspense>
        }
      />
      <Route path={PATH.KKOGKKOG_CREATE} element={<KkogkkogCreatePage />} />
      <Route path={PATH.LOGIN} element={<Login />} />
      <Route path={PATH.SIGNUP} element={<Signup />} />
    </Routes>
  );
};

export default Router;
