import { useChangeCouponStatusMutation, useRequestCouponMutation } from '../@queries/coupon';
import { EffectCallback } from '../types';

const useChangeCouponStatus = (id: number) => {
  const changeStatusMutate = useChangeCouponStatusMutation(id);
  const requestCouponMutate = useRequestCouponMutation();

  const cancelCoupon = ({ onSuccessCallback }: EffectCallback = {}) => {
    changeStatusMutate.mutate(
      { id, body: { couponEvent: 'CANCEL' } },
      {
        onSuccess() {
          onSuccessCallback?.();
        },
      }
    );
  };

  const requestCoupon = (
    { meetingDate }: { meetingDate: string },
    { onSuccessCallback }: EffectCallback = {}
  ) => {
    requestCouponMutate.mutate(
      { id, body: { couponEvent: 'REQUEST', meetingDate } },
      {
        onSuccess() {
          onSuccessCallback?.();
        },
      }
    );
  };

  const finishCoupon = ({ onSuccessCallback }: EffectCallback = {}) => {
    changeStatusMutate.mutate(
      { id, body: { couponEvent: 'FINISH' } },
      {
        onSuccess() {
          onSuccessCallback?.();
        },
      }
    );
  };

  const acceptCoupon = ({ onSuccessCallback }: EffectCallback = {}) => {
    changeStatusMutate.mutate(
      { id, body: { couponEvent: 'ACCEPT' } },
      {
        onSuccess() {
          onSuccessCallback?.();
        },
      }
    );
  };

  const declineCoupon = ({ onSuccessCallback }: EffectCallback = {}) => {
    changeStatusMutate.mutate(
      { id, body: { couponEvent: 'DECLINE' } },
      {
        onSuccess() {
          onSuccessCallback?.();
        },
      }
    );
  };

  return {
    cancelCoupon,
    requestCoupon,
    finishCoupon,
    acceptCoupon,
    declineCoupon,
  };
};

export default useChangeCouponStatus;
