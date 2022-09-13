import { useMemo } from 'react';
import { useQueryClient } from 'react-query';

import { useLoading } from '@/@hooks/@common/useLoading';
import {
  changeCouponStatus,
  createCoupon,
  getCoupon,
  getCouponList,
  reserveCoupon,
} from '@/apis/coupon';
import { COUPON_STATUS } from '@/types/client/coupon';
import { CouponReservationRequest } from '@/types/remote/request';
import { CouponResponse } from '@/types/remote/response';

import { useMutation, useQuery } from './utils';

const QUERY_KEY = {
  couponList: 'couponList',
  coupon: 'coupon',
};

/** Query */

export const useFetchCouponList = () => {
  /** suspense false만 isLoading을 사용할 수 있다. */
  const { data, ...rest } = useQuery([QUERY_KEY.couponList], getCouponList, {
    suspense: true,
    staleTime: 10000,
  });

  const couponList = data?.data?.data;

  const parsedSentCouponList = useMemo(
    () =>
      (couponList?.sent ?? [])?.reduceRight<Record<COUPON_STATUS, CouponResponse[]>>(
        (prev, coupon) => {
          const key = coupon.couponStatus;

          return { ...prev, [key]: [...prev[key], coupon] };
        },
        {
          REQUESTED: [],
          READY: [],
          ACCEPTED: [],
          FINISHED: [],
        }
      ),
    [couponList]
  );

  const parsedReceivedCouponList = useMemo(
    () =>
      (couponList?.received ?? [])?.reduceRight<Record<COUPON_STATUS, CouponResponse[]>>(
        (prev, coupon) => {
          const key = coupon.couponStatus;

          return { ...prev, [key]: [...prev[key], coupon] };
        },
        {
          REQUESTED: [],
          READY: [],
          ACCEPTED: [],
          FINISHED: [],
        }
      ),
    [couponList]
  );

  const reservationRecord = useMemo(() => {
    const combinedCouponList = [...(couponList?.received ?? []), ...(couponList?.sent ?? [])];

    return combinedCouponList.reduce<Record<string, CouponResponse[]>>((prev, coupon) => {
      const { couponStatus, meetingDate } = coupon;

      if (couponStatus === 'ACCEPTED' && meetingDate) {
        return { ...prev, [meetingDate]: [...(prev[meetingDate] ?? []), coupon] };
      }

      return prev;
    }, {});
  }, [couponList]);

  const receivedOpenCouponList = useMemo(
    () => [...parsedReceivedCouponList.REQUESTED, ...parsedReceivedCouponList.READY],
    [parsedReceivedCouponList.REQUESTED, parsedReceivedCouponList.READY]
  );

  const sentOpenCouponList = useMemo(
    () => [...parsedSentCouponList.REQUESTED, ...parsedSentCouponList.READY],
    [parsedSentCouponList.REQUESTED, parsedSentCouponList.READY]
  );

  return {
    couponList,
    parsedSentCouponList,
    parsedReceivedCouponList,
    reservationRecord,
    receivedOpenCouponList,
    sentOpenCouponList,
    ...rest,
  };
};

export const useFetchCoupon = (id: number) => {
  const { data, ...rest } = useQuery([QUERY_KEY.coupon, id], () => getCoupon(id));

  return {
    coupon: data?.data,
    ...rest,
  };
};

/** Mutation */

export const useCreateCouponMutation = () => {
  const queryClient = useQueryClient();
  const { showLoading, hideLoading } = useLoading();

  return useMutation(createCoupon, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.couponList);
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
  const { showLoading, hideLoading } = useLoading();

  return useMutation(changeCouponStatus, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.couponList);
      queryClient.invalidateQueries([QUERY_KEY.coupon, id]);
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });
};

export const useRequestCouponMutation = () => {
  const queryClient = useQueryClient();
  const { showLoading, hideLoading } = useLoading();

  return useMutation<unknown, unknown, { body: CouponReservationRequest }>(reserveCoupon, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.couponList);
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });
};
