import { useToast } from '@/@hooks/@common/useToast';
import { COUPON_ENG_TYPE, COUPON_HASHTAGS } from '@/types/client/coupon';
import { UserResponse } from '@/types/remote/response';

import {
  useChangeCouponStatusMutation,
  useCreateCouponMutation,
  useRequestCouponMutation,
} from '../@queries/coupon';

export const useChangeCouponStatus = ({
  id,
  reservationId,
}: {
  id: number;
  reservationId: number | null;
}) => {
  const { displayMessage } = useToast();

  const changeStatusMutate = useChangeCouponStatusMutation(id);
  const requestCouponMutate = useRequestCouponMutation();

  const cancelCoupon = () => {
    return changeStatusMutate.mutateAsync(
      { reservationId, body: { event: 'CANCEL' } },
      {
        onSuccess() {
          displayMessage('쿠폰 사용을 취소했어요', false);
        },
      }
    );
  };

  const requestCoupon = ({ meetingDate, message }: { meetingDate: string; message: string }) => {
    return requestCouponMutate.mutateAsync(
      { body: { couponId: id, meetingDate, message } },
      {
        onSuccess() {
          displayMessage('쿠폰 사용을 요청했어요', false);
        },
      }
    );
  };

  const finishCoupon = () => {
    return changeStatusMutate.mutateAsync(
      { reservationId, body: { event: 'FINISH' } },
      {
        onSuccess() {
          displayMessage('쿠폰 사용을 완료했어요', false);
        },
      }
    );
  };

  const acceptCoupon = ({ message }: { message: string }) => {
    return changeStatusMutate.mutateAsync(
      { reservationId, body: { event: 'ACCEPT', message } },
      {
        onSuccess() {
          displayMessage('쿠폰 사용을 승인했어요', false);
        },
      }
    );
  };

  const declineCoupon = ({ message }: { message: string }) => {
    return changeStatusMutate.mutateAsync(
      { reservationId, body: { event: 'DECLINE', message } },
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

  const createCoupon = async ({
    receiverList,
    hashtag,
    description,
    type,
  }: {
    receiverList: UserResponse[];
    hashtag: COUPON_HASHTAGS;
    description: string;
    type: COUPON_ENG_TYPE;
  }) => {
    const result = await createCouponMutate.mutateAsync(
      {
        receiverIds: receiverList.map(({ id }) => id),
        hashtag,
        description,
        couponType: type,
      },
      {
        onSuccess() {
          displayMessage('쿠폰을 생성했어요', false);
        },
      }
    );

    return result?.data?.data;
  };

  return { createCoupon };
};
