import { useChangeCouponStatusMutation } from '../@queries/coupon';

type changeCouponStatusType = {
  onSuccessCallback?: () => void;
};

const useChangeCouponStatus = (id: number) => {
  const changeStatusMutate = useChangeCouponStatusMutation();

  const cancelCoupon = ({ onSuccessCallback }: changeCouponStatusType) => {
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
    { onSuccessCallback }: changeCouponStatusType
  ) => {
    changeStatusMutate.mutate(
      { id, body: { couponEvent: 'REQUEST', meetingDate } },
      {
        onSuccess() {
          onSuccessCallback?.();
        },
      }
    );
  };

  const finishCoupon = ({ onSuccessCallback }: changeCouponStatusType) => {
    changeStatusMutate.mutate(
      { id, body: { couponEvent: 'FINISH' } },
      {
        onSuccess() {
          onSuccessCallback?.();
        },
      }
    );
  };

  const acceptCoupon = ({ onSuccessCallback }: changeCouponStatusType) => {
    changeStatusMutate.mutate(
      { id, body: { couponEvent: 'ACCEPT' } },
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
  };
};

export default useChangeCouponStatus;
