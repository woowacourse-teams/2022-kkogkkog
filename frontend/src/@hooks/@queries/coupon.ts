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
import { CouponListByStatusRequest } from '@/types/coupon/remote';

import { useFetchMe } from './user';
import { useMutation, useQuery } from './utils';

const QUERY_KEY = {
  coupon: 'coupon',
  reservationList: 'reservationList',
  sentCouponList: 'sentCouponList',
  receivedCouponList: 'receivedCouponList',
  couponListByStatus: 'couponListByStatus',
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
  });

  return {
    reservationList: data?.data ?? [],
    isLoading,
  };
};

export const useFetchSentCouponList = () => {
  const { data, isLoading } = useQuery([QUERY_KEY.sentCouponList], () => getSentCouponList(), {
    staleTime: 10000,
  });

  const sentOpenCouponList = (data?.data ?? []).filter(
    coupon => coupon.couponStatus === 'READY' || coupon.couponStatus === 'REQUESTED'
  );

  return {
    sentCouponList: data?.data ?? [],
    sentOpenCouponList,
    isLoading,
  };
};

export const useFetchReceivedCouponList = () => {
  const { data, isLoading } = useQuery(
    [QUERY_KEY.receivedCouponList],
    () => getReceivedCouponList(),
    {
      staleTime: 10000,
    }
  );

  const receivedOpenCouponList = (data?.data ?? []).filter(
    coupon => coupon.couponStatus === 'READY' || coupon.couponStatus === 'REQUESTED'
  );

  return {
    receivedCouponList: data?.data ?? [],
    receivedOpenCouponList,
    isLoading,
  };
};

export const useFetchCouponListByStatus = ({
  couponListType,
  body,
}: {
  couponListType: 'sent' | 'received';
  body: CouponListByStatusRequest;
}) => {
  const fetcher =
    couponListType === 'sent' ? getSentCouponListByStatus : getReceivedCouponListByStatus;

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
      queryClient.invalidateQueries(QUERY_KEY.sentCouponList);
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
        queryClient.invalidateQueries([QUERY_KEY.sentCouponList]);
        queryClient.invalidateQueries([QUERY_KEY.couponListByStatus, 'sent']);

        return;
      }

      queryClient.invalidateQueries([QUERY_KEY.reservationList]);
      queryClient.invalidateQueries([QUERY_KEY.receivedCouponList]);
      queryClient.invalidateQueries([QUERY_KEY.couponListByStatus, 'received']);
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });
};
