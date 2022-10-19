import {
  CreateUnregisteredCouponRequest,
  RegisterUnregisteredCouponRequest,
} from '@/types/unregistered-coupon/remote';

import { useToast } from '../@common/useToast';
import {
  useCreateUnregisteredCouponMutation,
  useDeleteUnregisteredCouponMutation,
  useRegisterUnregisteredCouponMutation,
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

export const useRegisterUnregisteredCoupon = () => {
  const { displayMessage } = useToast();
  const { mutateAsync } = useRegisterUnregisteredCouponMutation();

  const registerUnregisteredCoupon = (body: RegisterUnregisteredCouponRequest) => {
    return mutateAsync(body, {
      onSuccess() {
        displayMessage('쿠폰이 등록되었습니다.', false);
      },
    });
  };

  return {
    registerUnregisteredCoupon,
  };
};

export const useDeleteUnregisteredCoupon = () => {
  const { displayMessage } = useToast();
  const { mutateAsync } = useDeleteUnregisteredCouponMutation();

  const deleteUnregisteredCoupon = (id: number) => {
    return mutateAsync(id, {
      onSuccess() {
        displayMessage('쿠폰이 삭제되었습니다.', false);
      },
    });
  };

  return {
    deleteUnregisteredCoupon,
  };
};
