import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useFetchUnregisteredCouponByCode } from '@/@hooks/@queries/unregistered-coupon';

import NotFoundPage from '../404';
import ExpiredRegisteredCouponPage from './ExpiredRegisteredCouponPage';
import IssuedRegisteredCouponPage from './IssuedRegisteredCouponPage';
import RegisteredCouponPage from './RegisteredRegisteredCouponPage';

const UnregisteredCouponCodePage = () => {
  const couponCode = useGetSearchParam('couponCode');

  // @TODO: null 처리
  const { unregisteredCoupon } = useFetchUnregisteredCouponByCode(couponCode ?? '');

  if (couponCode === null) {
    return <NotFoundPage />;
  }

  if (unregisteredCoupon === undefined) {
    return <NotFoundPage />;
  }

  if (unregisteredCoupon.unregisteredCouponStatus === 'ISSUED') {
    return (
      <IssuedRegisteredCouponPage unregisteredCoupon={unregisteredCoupon} couponCode={couponCode} />
    );
  }

  if (unregisteredCoupon.unregisteredCouponStatus === 'REGISTERED') {
    return <RegisteredCouponPage />;
  }

  if (unregisteredCoupon.unregisteredCouponStatus === 'EXPIRED') {
    return <ExpiredRegisteredCouponPage />;
  }

  return <NotFoundPage />;
};

export default UnregisteredCouponCodePage;
