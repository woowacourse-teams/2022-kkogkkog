import { AxiosError } from 'axios';

import { useToast } from '@/@hooks/@common/useToast';

import { useChangeCouponStatusMutation, useRequestCouponMutation } from '../@queries/coupon';
import { EffectCallback } from '../types';

const useChangeCouponStatus = ({
  id,
  reservationId,
}: {
  id: number;
  reservationId: number | null;
}) => {
  const { displayMessage } = useToast();

  const changeStatusMutate = useChangeCouponStatusMutation(id);
  const requestCouponMutate = useRequestCouponMutation();

  const cancelCoupon = ({ onSuccessCallback }: EffectCallback = {}) => {
    changeStatusMutate.mutate(
      { reservationId, body: { event: 'CANCEL' } },
      {
        onSuccess() {
          onSuccessCallback?.();

          displayMessage('쿠폰 사용을 취소했어요', false);
        },
      }
    );
  };

  const requestCoupon = (
    { meetingDate, message }: { meetingDate: string; message: string },
    { onSuccessCallback }: EffectCallback = {}
  ) => {
    requestCouponMutate.mutate(
      { body: { couponId: id, meetingDate, message } },
      {
        onSuccess() {
          onSuccessCallback?.();

          displayMessage('쿠폰 사용을 요청했어요', false);
        },
      }
    );
  };

  const finishCoupon = ({ onSuccessCallback }: EffectCallback = {}) => {
    changeStatusMutate.mutate(
      { reservationId, body: { event: 'FINISH' } },
      {
        onSuccess() {
          onSuccessCallback?.();

          displayMessage('쿠폰 사용을 완료했어요', false);
        },
      }
    );
  };

  const acceptCoupon = (
    { message }: { message: string },
    { onSuccessCallback }: EffectCallback = {}
  ) => {
    changeStatusMutate.mutate(
      { reservationId, body: { event: 'ACCEPT', message } },
      {
        onSuccess() {
          onSuccessCallback?.();

          displayMessage('쿠폰 사용을 승인했어요', false);
        },
      }
    );
  };

  const declineCoupon = (
    { message }: { message: string },
    { onSuccessCallback }: EffectCallback = {}
  ) => {
    changeStatusMutate.mutate(
      { reservationId, body: { event: 'DECLINE', message } },
      {
        onSuccess() {
          onSuccessCallback?.();

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

export default useChangeCouponStatus;
