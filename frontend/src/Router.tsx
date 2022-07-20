import { Suspense } from 'react';
import { Navigate, Outlet, Route, Routes } from 'react-router-dom';

import Loading from '@/@components/@shared/Loading';
import JoinPage from '@/@pages/join';
import KkogkkogListPage from '@/@pages/kkogkkog-list';
import KkogkkogCreatePage from '@/@pages/kkogkkog-list/create';
import LandingPage from '@/@pages/landing';
import ProfilePage from '@/@pages/profile';

import useMe from './@hooks/user/useMe';
import LoginPage from './@pages/login';

export const PATH = {
  LANDING: '/',
  KKOGKKOG_LIST: '/kkogkkog-list',
  KKOGKKOG_CREATE: '/kkogkkog-list/create',
  LOGIN: '/login',
  JOIN: '/join',
  PROFILE: '/profile',
};

const Router = () => {
  return (
    <Routes>
      <Route
        path={PATH.LANDING}
        element={
          <Suspense fallback={<LandingPage.Skeleton />}>
            <LandingPage />
          </Suspense>
        }
      />
      <Route path={PATH.LOGIN} element={<LoginPage />} />
      <Route path={PATH.JOIN} element={<JoinPage />} />
      <Route element={<PrivateRoute />}>
        <Route
          path={PATH.KKOGKKOG_LIST}
          element={
            <Suspense fallback={<KkogkkogListPage.Skeleton />}>
              <KkogkkogListPage />
            </Suspense>
          }
        />
        <Route
          path={PATH.KKOGKKOG_CREATE}
          element={
            <Suspense fallback={<Loading>👻</Loading>}>
              <KkogkkogCreatePage />
            </Suspense>
          }
        />
        <Route path={PATH.PROFILE} element={<ProfilePage />} />
      </Route>
    </Routes>
  );
};

export default Router;

const PrivateRoute = () => {
  const { me } = useMe();

  return me ? <Outlet /> : <Navigate to='/' replace />;
};
