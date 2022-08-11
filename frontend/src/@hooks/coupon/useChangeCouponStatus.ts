import { useChangeCouponStatusMutation, useRequestCouponMutation } from '../@queries/coupon';

type changeCouponStatusType = {
  onSuccessCallback?: () => void;
};

const useChangeCouponStatus = (id: number) => {
  const changeStatusMutate = useChangeCouponStatusMutation(id);
  const requestCouponMutate = useRequestCouponMutation();

  const cancelCoupon = ({ onSuccessCallback }: changeCouponStatusType = {}) => {
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
    { onSuccessCallback }: changeCouponStatusType = {}
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

  const finishCoupon = ({ onSuccessCallback }: changeCouponStatusType = {}) => {
    changeStatusMutate.mutate(
      { id, body: { couponEvent: 'FINISH' } },
      {
        onSuccess() {
          onSuccessCallback?.();
        },
      }
    );
  };

  const acceptCoupon = ({ onSuccessCallback }: changeCouponStatusType = {}) => {
    changeStatusMutate.mutate(
      { id, body: { couponEvent: 'ACCEPT' } },
      {
        onSuccess() {
          onSuccessCallback?.();
        },
      }
    );
  };

  const declineCoupon = ({ onSuccessCallback }: changeCouponStatusType = {}) => {
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
