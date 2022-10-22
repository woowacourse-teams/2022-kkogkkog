import { useQueryClient } from 'react-query';

import { useLoading } from '@/@hooks/@common/useLoading';
import {
  changeCouponStatus,
  createCoupon,
  getCoupon,
  getReceivedCouponList,
  getReceivedCouponListByStatus,
  getReservationList,
  getSentCouponList,
  getSentCouponListByStatus,
} from '@/apis/coupon';
import { COUPON_LIST_TYPE } from '@/types/coupon/client';
import { CouponListByStatusRequest } from '@/types/coupon/remote';
import { YYYYMMDD } from '@/types/utils';

import { useToast } from '../@common/useToast';
import { useFetchMe } from './user';
import { useMutation, useQuery } from './utils';

const QUERY_KEY = {
  /** MAIN KEY */
  coupon: 'coupon',
  reservationList: 'reservationList',
  couponList: 'couponList',
  couponListByStatus: 'couponListByStatus',

  /** SUB KEY */
  sent: 'sent',
  received: 'received',

  REQUESTED: 'REQUESTED',
  READY: 'READY',
  ACCEPTED: 'ACCEPTED',
  FINISHED: 'FINISHED',
};

export const useFetchCoupon = (id: number) => {
  const { data } = useQuery([QUERY_KEY.coupon, id], () => getCoupon(id), {
    staleTime: 10000,
  });

  return {
    coupon: data,
  };
};

export const useFetchReservationList = () => {
  const { data, isLoading } = useQuery([QUERY_KEY.reservationList], () => getReservationList(), {
    staleTime: 10000,
    suspense: false,
  });

  return {
    reservationList: data?.data ?? [],
    isLoading,
  };
};

export const useFetchCouponList = ({ couponListType }: { couponListType: COUPON_LIST_TYPE }) => {
  const fetcher = couponListType === QUERY_KEY.sent ? getSentCouponList : getReceivedCouponList;

  const { data, isLoading } = useQuery([QUERY_KEY.couponList, couponListType], () => fetcher(), {
    staleTime: 10000,
    suspense: false,
  });

  const openCouponList = (data?.data ?? []).filter(
    coupon => coupon.couponStatus === 'READY' || coupon.couponStatus === 'REQUESTED'
  );

  return {
    couponList: data?.data ?? [],
    openCouponList,
    isLoading,
  };
};

export const useFetchCouponListByStatus = ({
  couponListType,
  body,
}: {
  couponListType: COUPON_LIST_TYPE;
  body: CouponListByStatusRequest;
}) => {
  const fetcher =
    couponListType === QUERY_KEY.sent ? getSentCouponListByStatus : getReceivedCouponListByStatus;

  const { data, isLoading } = useQuery(
    [QUERY_KEY.couponListByStatus, couponListType, body.type],
    () => fetcher(body),
    {
      staleTime: 10000,
      suspense: false,
    }
  );

  return {
    couponListByStatus: data?.data ?? [],
    isLoading,
  };
};

/** Mutation */

export const useCreateCouponMutation = () => {
  const queryClient = useQueryClient();

  const { showLoading, hideLoading } = useLoading();
  const { displayMessage } = useToast();

  const { mutateAsync } = useMutation(createCoupon, {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.couponList, QUERY_KEY.sent]);
      displayMessage('쿠폰을 생성했어요', false); // 모든 컴포넌트에 이것이 사용되는가? yes -> 이곳에 정의 / no -> 컴포넌트 단에서 후처리
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });

  // 로직이 없는 경우 mutateAsync를 이름만 바꾸어 보내준다.
  return { createCoupon: mutateAsync };
};

export const useChangeCouponStatusMutation = (couponId: number) => {
  const queryClient = useQueryClient();

  const { coupon } = useFetchCoupon(couponId);
  const { me } = useFetchMe();

  const { showLoading, hideLoading } = useLoading();
  const { displayMessage } = useToast();

  const { mutateAsync } = useMutation(changeCouponStatus, {
    onSuccess() {
      const isSent = coupon?.sender.id === me?.id;

      queryClient.invalidateQueries([QUERY_KEY.coupon, couponId]);

      if (isSent) {
        queryClient.invalidateQueries([QUERY_KEY.reservationList]);
        queryClient.invalidateQueries([QUERY_KEY.couponList, QUERY_KEY.sent]);
        queryClient.invalidateQueries([QUERY_KEY.couponListByStatus, QUERY_KEY.sent]);

        return;
      }

      queryClient.invalidateQueries([QUERY_KEY.reservationList]);
      queryClient.invalidateQueries([QUERY_KEY.couponList, QUERY_KEY.received]);
      queryClient.invalidateQueries([QUERY_KEY.couponListByStatus, QUERY_KEY.received]);
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });

  const cancelCoupon = () => {
    return mutateAsync(
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
    return mutateAsync(
      { couponId, body: { couponEvent: 'REQUEST', meetingDate, meetingMessage } },
      {
        onSuccess() {
          displayMessage('쿠폰 사용을 요청했어요', false);
        },
      }
    );
  };

  const finishCoupon = () => {
    return mutateAsync(
      { couponId, body: { couponEvent: 'FINISH' } },
      {
        onSuccess() {
          displayMessage('쿠폰 사용을 완료했어요', false);
        },
      }
    );
  };

  const acceptCoupon = ({ meetingMessage }: { meetingMessage: string }) => {
    return mutateAsync(
      { couponId, body: { couponEvent: 'ACCEPT', meetingMessage } },
      {
        onSuccess() {
          displayMessage('쿠폰 사용을 승인했어요', false);
        },
      }
    );
  };

  const declineCoupon = ({ meetingMessage }: { meetingMessage: string }) => {
    return mutateAsync(
      { couponId, body: { couponEvent: 'DECLINE', meetingMessage } },
      {
        onSuccess() {
          displayMessage('쿠폰 사용을 거절했어요', false);
        },
      }
    );
  };

  // mutate 사용 방식을 제한하여 반환한다.
  return {
    cancelCoupon,
    requestCoupon,
    finishCoupon,
    acceptCoupon,
    declineCoupon,
  };
};

/** invalidateQueries */

export const useCouponInvalidationOnRegisterUnregisteredCoupon = () => {
  const queryClient = useQueryClient();

  const invalidateReceivedCouponList = () => {
    queryClient.invalidateQueries([QUERY_KEY.couponList, QUERY_KEY.received]);
    queryClient.invalidateQueries([
      QUERY_KEY.couponListByStatus,
      QUERY_KEY.received,
      QUERY_KEY.READY,
    ]);
  };

  return {
    invalidateReceivedCouponList,
  };
};
