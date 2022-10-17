import {
  CreateUnregisteredCouponRequest,
  RegisterUnregisteredCouponRequest,
} from '@/types/unregistered-coupon/remote';

import { useToast } from '../@common/useToast';
import {
  useCreateUnregisteredCouponMutation,
  useRegisteredUnregisteredCouponMutation,
} from '../@queries/unregistered-coupon';

export const useCreateUnregisteredCoupon = () => {
  const { displayMessage } = useToast();

  const { mutateAsync } = useCreateUnregisteredCouponMutation();

  const createUnregisteredCoupon = async (body: CreateUnregisteredCouponRequest) => {
    const { data } = await mutateAsync(body, {
      onSuccess() {
        displayMessage('쿠폰을 생성했어요', false);
      },
    });

    return data;
  };

  return { createUnregisteredCoupon };
};

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
