import { Suspense } from 'react';
import { Navigate, Outlet, Route, Routes } from 'react-router-dom';

import CustomSuspense from '@/@components/@shared/CustomSuspense';
import Loading from '@/@components/@shared/Loading';
import NotFoundPage from '@/@pages/404';
import CouponListPage from '@/@pages/coupon-list';
import CouponCreatePage from '@/@pages/coupon-list/create';
import UserHistoryPage from '@/@pages/history';
import LandingPage from '@/@pages/landing';
import ProfilePage from '@/@pages/profile';

import { useFetchMe } from './@hooks/@queries/user';
import DownloadPage from './@pages/download';
import LoginPage from './@pages/login';
import ProfileEditPage from './@pages/profile/edit';
import Redirect from './@pages/redirect';

export const PATH = {
  LANDING: '/',
  COUPON_LIST: '/coupon-list',
  SENT_COUPON_LIST: '/coupon-list/sent',
  RECEIVED_COUPON_LIST: '/coupon-list/received',
  COUPON_CREATE: '/coupon-list/create',
  LOGIN: '/login',
  LOGIN_REDIRECT: '/login/redirect',
  PROFILE: '/profile',
  PROFILE_EDIT: '/profile/edit',
  NOT_FOUND: '/*',
  USER_HISTORY: '/history',
  DOWNLOAD_REDIRECT: '/download/redirect',
  DOWNLOAD: '/download',
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
      <Route path={PATH.LOGIN_REDIRECT} element={<Redirect />} />
      <Route path={PATH.DOWNLOAD} element={<DownloadPage />} />
      <Route path={PATH.DOWNLOAD_REDIRECT} element={<Redirect />} />
      <Route element={<PrivateRoute />}>
        <Route
          path={PATH.SENT_COUPON_LIST}
          element={
            <Suspense fallback={<CouponListPage.Skeleton />}>
              <CouponListPage />
            </Suspense>
          }
        />
        <Route
          path={PATH.RECEIVED_COUPON_LIST}
          element={
            <Suspense fallback={<CouponListPage.Skeleton />}>
              <CouponListPage />
            </Suspense>
          }
        />
        <Route
          path={PATH.COUPON_CREATE}
          element={
            <Suspense fallback={<Loading>👻</Loading>}>
              <CouponCreatePage />
            </Suspense>
          }
        />
        <Route path={PATH.PROFILE} element={<ProfilePage />} />
        <Route path={PATH.PROFILE_EDIT} element={<ProfileEditPage />} />
        <Route path={PATH.USER_HISTORY} element={<UserHistoryPage />} />
      </Route>
      <Route path={PATH.NOT_FOUND} element={<NotFoundPage />} />
    </Routes>
  );
};

export default Router;

const PrivateRoute = () => {
  const { me, isLoading } = useFetchMe();

  return me ? (
    <Outlet />
  ) : (
    <CustomSuspense isLoading={isLoading} fallback={<Loading>👻</Loading>}>
      <Navigate to='/' replace />
    </CustomSuspense>
  );
};
