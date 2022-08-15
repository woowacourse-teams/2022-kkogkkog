import { AxiosError } from 'axios';

import { useToast } from '@/@hooks/@common/useToast';

import { useChangeCouponStatusMutation, useRequestCouponMutation } from '../@queries/coupon';
import { EffectCallback } from '../types';

const useChangeCouponStatus = (id: number) => {
  const { displayMessage } = useToast();

  const changeStatusMutate = useChangeCouponStatusMutation(id);
  const requestCouponMutate = useRequestCouponMutation();

  const cancelCoupon = ({ onSuccessCallback }: EffectCallback = {}) => {
    changeStatusMutate.mutate(
      { id, body: { event: 'CANCEL' } },
      {
        onSuccess() {
          onSuccessCallback?.();

          displayMessage('쿠폰 사용을 취소했어요', false);
        },
        onError(error) {
          if (error instanceof AxiosError) {
            displayMessage(error?.response?.data?.message, true);
          }
        },
      }
    );
  };

  const requestCoupon = (
    { meetingDate, message }: { meetingDate: string; message: string },
    { onSuccessCallback }: EffectCallback = {}
  ) => {
    requestCouponMutate.mutate(
      { id, body: { couponId: id, meetingDate, message } },
      {
        onSuccess() {
          onSuccessCallback?.();

          displayMessage('쿠폰 사용을 요청했어요', false);
        },
        onError(error) {
          if (error instanceof AxiosError) {
            displayMessage(error?.response?.data?.message, true);
          }
        },
      }
    );
  };

  const finishCoupon = ({ onSuccessCallback }: EffectCallback = {}) => {
    changeStatusMutate.mutate(
      { id, body: { event: 'FINISH' } },
      {
        onSuccess() {
          onSuccessCallback?.();

          displayMessage('쿠폰 사용을 완료했어요', false);
        },
        onError(error) {
          if (error instanceof AxiosError) {
            displayMessage(error?.response?.data?.message, true);
          }
        },
      }
    );
  };

  const acceptCoupon = ({ onSuccessCallback }: EffectCallback = {}) => {
    changeStatusMutate.mutate(
      { id, body: { event: 'ACCEPT' } },
      {
        onSuccess() {
          onSuccessCallback?.();

          displayMessage('쿠폰 사용을 승인했어요', false);
        },
        onError(error) {
          if (error instanceof AxiosError) {
            displayMessage(error?.response?.data?.message, true);
          }
        },
      }
    );
  };

  const declineCoupon = ({ onSuccessCallback }: EffectCallback = {}) => {
    changeStatusMutate.mutate(
      { id, body: { event: 'DECLINE' } },
      {
        onSuccess() {
          onSuccessCallback?.();

          displayMessage('쿠폰 사용을 거절했어요', false);
        },
        onError(error) {
          if (error instanceof AxiosError) {
            displayMessage(error?.response?.data?.message, true);
          }
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
