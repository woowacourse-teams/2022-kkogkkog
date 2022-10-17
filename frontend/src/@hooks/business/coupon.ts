import { useToast } from '@/@hooks/@common/useToast';
import { useChangeCouponStatusMutation, useCreateCouponMutation } from '@/@hooks/@queries/coupon';
import { CreateCouponRequest } from '@/types/coupon/remote';
import { YYYYMMDD } from '@/types/utils';

export const useChangeCouponStatus = ({ couponId }: { couponId: number }) => {
  const { displayMessage } = useToast();

  const changeStatusMutate = useChangeCouponStatusMutation(couponId);

  const cancelCoupon = () => {
    return changeStatusMutate.mutateAsync(
      { couponId, body: { couponEvent: 'CANCEL' } },
      {
        onSuccess() {
          displayMessage('쿠폰 사용을 취소했어요', false);
        },
      }
    );
  };

  const requestCoupon = ({
    meetingDate,
    meetingMessage,
  }: {
    meetingDate: YYYYMMDD;
    meetingMessage: string;
  }) => {
    return changeStatusMutate.mutateAsync(
      { couponId, body: { couponEvent: 'REQUEST', meetingDate, meetingMessage } },
      {
        onSuccess() {
          displayMessage('쿠폰 사용을 요청했어요', false);
        },
      }
    );
  };

  const finishCoupon = () => {
    return changeStatusMutate.mutateAsync(
      { couponId, body: { couponEvent: 'FINISH' } },
      {
        onSuccess() {
          displayMessage('쿠폰 사용을 완료했어요', false);
        },
      }
    );
  };

  const acceptCoupon = ({ meetingMessage }: { meetingMessage: string }) => {
    return changeStatusMutate.mutateAsync(
      { couponId, body: { couponEvent: 'ACCEPT', meetingMessage } },
      {
        onSuccess() {
          displayMessage('쿠폰 사용을 승인했어요', false);
        },
      }
    );
  };

  const declineCoupon = ({ meetingMessage }: { meetingMessage: string }) => {
    return changeStatusMutate.mutateAsync(
      { couponId, body: { couponEvent: 'DECLINE', meetingMessage } },
      {
        onSuccess() {
          displayMessage('쿠폰 사용을 거절했어요', false);
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

export const useCreateCoupon = () => {
  const { displayMessage } = useToast();

  const createCouponMutate = useCreateCouponMutation();

  const createCoupon = async (body: CreateCouponRequest) => {
    const { data } = await createCouponMutate.mutateAsync(body, {
      onSuccess() {
        displayMessage('쿠폰을 생성했어요', false);
      },
    });

    return data;
  };

  return { createCoupon };
};
