import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useFetchUnregisteredCouponByCode } from '@/@hooks/@queries/unregistered-coupon';

import NotFoundPage from '../404';
import ExpiredUnregisteredCouponPage from './ExpiredRegisteredCouponPage';
import IssuedUnregisteredCouponPage from './IssuedRegisteredCouponPage';
import RegisteredCouponPage from './RegisteredRegisteredCouponPage';

const UnregisteredCouponCodeProxyPage = () => {
  const couponCode = useGetSearchParam('couponCode');

  // @TODO: null 처리
  const { unregisteredCoupon } = useFetchUnregisteredCouponByCode(couponCode ?? '');

  if (couponCode === null) {
    return <NotFoundPage />;
  }

  if (unregisteredCoupon === undefined) {
    return <NotFoundPage />;
  }

  const { unregisteredCouponStatus } = unregisteredCoupon;

  if (unregisteredCouponStatus === 'ISSUED') {
    return (
      <IssuedUnregisteredCouponPage
        unregisteredCoupon={unregisteredCoupon}
        couponCode={couponCode}
      />
    );
  }

  if (unregisteredCouponStatus === 'REGISTERED') {
    return <RegisteredCouponPage />;
  }

  if (unregisteredCouponStatus === 'EXPIRED') {
    return <ExpiredUnregisteredCouponPage />;
  }

  return <NotFoundPage />;
};

export default UnregisteredCouponCodeProxyPage;
