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

  return useMutation(createCoupon, {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.couponList, QUERY_KEY.sent]);
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });
};

export const useChangeCouponStatusMutation = (id: number) => {
  const queryClient = useQueryClient();
  const { coupon } = useFetchCoupon(id);
  const { me } = useFetchMe();
  const { showLoading, hideLoading } = useLoading();

  return useMutation(changeCouponStatus, {
    onSuccess() {
      const isSent = coupon?.sender.id === me?.id;

      queryClient.invalidateQueries([QUERY_KEY.coupon, id]);

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
};
