import { useState } from 'react';

import { CouponResponse } from '@/types/remote/response';

const useCouponModal = () => {
  const [currentCoupon, setCurrentCoupon] = useState<CouponResponse | null>(null);

  const openCouponModal = (coupon: CouponResponse) => {
    setCurrentCoupon(coupon);
  };

  const closeCouponModal = () => {
    setCurrentCoupon(null);
  };

  return {
    currentCoupon,
    openCouponModal,
    closeCouponModal,
  };
};

export default useCouponModal;
