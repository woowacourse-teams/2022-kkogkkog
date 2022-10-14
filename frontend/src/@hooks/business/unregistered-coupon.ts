import { RegisterUnregisteredCouponRequest } from '@/types/unregistered-coupon/remote';

import { useRegisteredUnregisteredCouponMutation } from '../@queries/unregistered-coupon';

export const useRegisteredUnregisteredCoupon = ({
  couponCode,
}: RegisterUnregisteredCouponRequest) => {
  const { mutateAsync } = useRegisteredUnregisteredCouponMutation({
    couponCode,
  });

  const registerUnregisteredCoupon = (body: RegisterUnregisteredCouponRequest) => {
    return mutateAsync(body);
  };

  return {
    registerUnregisteredCoupon,
  };
};
