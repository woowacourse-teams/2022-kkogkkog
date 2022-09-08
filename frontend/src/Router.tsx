import { Suspense } from 'react';
import { Navigate, Outlet, Route, Routes } from 'react-router-dom';

import CustomSuspense from '@/@components/@shared/CustomSuspense';
import Loading from '@/@components/@shared/Loading';
import NotFoundPage from '@/@pages/404';
import CouponListPage from '@/@pages/coupon-list';
import CouponCreatePage from '@/@pages/coupon-list/create';
import UserHistoryPage from '@/@pages/history';
import JoinPage from '@/@pages/join';
import LandingPage from '@/@pages/landing';
import ProfilePage from '@/@pages/profile';

import { useFetchMe } from './@hooks/@queries/user';
import CouponDetailPage from './@pages/coupon-list/coupon-detail';
import CouponAcceptPage from './@pages/coupon-list/coupon-detail/accept/index';
import CouponDeclinePage from './@pages/coupon-list/coupon-detail/decline';
import CouponRequestPage from './@pages/coupon-list/coupon-detail/request';
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
  JOIN: '/join',
  PROFILE: '/profile',
  PROFILE_EDIT: '/profile/edit',
  NOT_FOUND: '/*',
  USER_HISTORY: '/history',
  DOWNLOAD_REDIRECT: '/download/redirect',
  DOWNLOAD: '/download',
  COUPON_DETAIL: '/coupon-list/:couponId',
  COUPON_REQUEST: '/coupon-list/:couponId/request',
  COUPON_ACCEPT: '/coupon-list/:couponId/accept',
  COUPON_DECLINE: '/coupon-list/:couponId/decline',
};

const Router = () => {
  return (
    <Routes>
      <Route
        path={PATH.LANDING}
        element={
          <Suspense fallback={<Loading />}>
            <LandingPage />
          </Suspense>
        }
      />
      <Route path={PATH.LOGIN} element={<LoginPage />} />
      <Route path={PATH.JOIN} element={<JoinPage />} />
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
            <Suspense fallback={<Loading />}>
              <CouponCreatePage />
            </Suspense>
          }
        />
        {/* @TODO: Skeleton */}
        <Route
          path={PATH.COUPON_DETAIL}
          element={
            <Suspense fallback={<Loading />}>
              <CouponDetailPage />
            </Suspense>
          }
        />
        <Route
          path={PATH.COUPON_REQUEST}
          element={
            <Suspense fallback={<Loading />}>
              <CouponRequestPage />
            </Suspense>
          }
        />
        <Route
          path={PATH.COUPON_ACCEPT}
          element={
            <Suspense fallback={<Loading />}>
              <CouponAcceptPage />
            </Suspense>
          }
        />
        <Route
          path={PATH.COUPON_DECLINE}
          element={
            <Suspense fallback={<Loading />}>
              <CouponDeclinePage />
            </Suspense>
          }
        />
        <Route path={PATH.PROFILE} element={<ProfilePage />} />
        <Route path={PATH.PROFILE_EDIT} element={<ProfileEditPage />} />
        <Route
          path={PATH.USER_HISTORY}
          element={
            <Suspense fallback={<Loading />}>
              <UserHistoryPage />
            </Suspense>
          }
        />
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
    <CustomSuspense isLoading={isLoading} fallback={<Loading />}>
      <Navigate to='/' replace />
    </CustomSuspense>
  );
};
