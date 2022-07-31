import { Suspense } from 'react';
import { Navigate, Outlet, Route, Routes } from 'react-router-dom';

import CustomSuspense from '@/@components/@shared/CustomSuspense';
import Loading from '@/@components/@shared/Loading';
import NotFoundPage from '@/@pages/404';
import JoinPage from '@/@pages/join';
import KkogkkogListPage from '@/@pages/kkogkkog-list';
import KkogkkogCreatePage from '@/@pages/kkogkkog-list/create';
import LandingPage from '@/@pages/landing';
import ProfilePage from '@/@pages/profile';

import useMe from './@hooks/user/useMe';
import LoginPage from './@pages/login';
import LoginRedirect from './@pages/login/redirect';

export const PATH = {
  LANDING: '/',
  KKOGKKOG_LIST: '/kkogkkog-list',
  SENT_KKOGKKOG_LIST: '/kkogkkog-list/sent',
  RECEIVED_KKOGKKOG_LIST: '/kkogkkog-list/received',
  KKOGKKOG_CREATE: '/kkogkkog-list/create',
  LOGIN: '/login',
  LOGIN_REDIRECT: '/login/redirect',
  JOIN: '/join',
  PROFILE: '/profile',
  NOT_FOUND: '/*',
};

const Router = () => {
  return (
    <Routes>
      <Route
        path={PATH.LANDING}
        element={
          <Suspense fallback={<Loading>👻</Loading>}>
            <LandingPage />
          </Suspense>
        }
      />
      <Route path={PATH.LOGIN} element={<LoginPage />} />
      <Route path={PATH.LOGIN_REDIRECT} element={<LoginRedirect />} />
      <Route path={PATH.JOIN} element={<JoinPage />} />
      <Route element={<PrivateRoute />}>
        <Route
          path={PATH.SENT_KKOGKKOG_LIST}
          element={
            <Suspense fallback={<KkogkkogListPage.Skeleton />}>
              <KkogkkogListPage />
            </Suspense>
          }
        />
        <Route
          path={PATH.RECEIVED_KKOGKKOG_LIST}
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
      <Route path={PATH.NOT_FOUND} element={<NotFoundPage />} />
    </Routes>
  );
};

export default Router;

const PrivateRoute = () => {
  const { me, isLoading } = useMe();

  return me ? (
    <Outlet />
  ) : (
    <CustomSuspense isLoading={isLoading} fallback={<Loading>👻</Loading>}>
      <Navigate to='/' replace />
    </CustomSuspense>
  );
};
