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
import { COUPON_LIST_TYPE, COUPON_STATUS } from '@/types/client/coupon';
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

  const couponList = useMemo(
    () => data?.data?.data ?? { received: [], sent: [] },
    [data?.data?.data]
  );

  const parseCouponList = (couponStatus: COUPON_LIST_TYPE) => {
    return couponList[couponStatus].reduceRight<Record<COUPON_STATUS, CouponResponse[]>>(
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
    );
  };

  const parseOpenCouponList = (couponStatus: COUPON_LIST_TYPE) => {
    const parsedCouponList = parseCouponList(couponStatus);

    return [...parsedCouponList.REQUESTED, ...parsedCouponList.READY];
  };

  const generateReservationRecord = () => {
    const combinedCouponList = [...couponList.received, ...couponList.sent];

    return combinedCouponList.reduce<Record<string, CouponResponse[]>>((prev, coupon) => {
      const { couponStatus, meetingDate } = coupon;

      if (couponStatus === 'ACCEPTED' && meetingDate) {
        return { ...prev, [meetingDate]: [...(prev[meetingDate] ?? []), coupon] };
      }

      return prev;
    }, {});
  };

  return {
    couponList,
    isLoading: rest.isLoading,
    parseCouponList,
    parseOpenCouponList,
    generateReservationRecord,
  };
};

export const useFetchCoupon = (id: number) => {
  const { data } = useQuery([QUERY_KEY.coupon, id], () => getCoupon(id));

  return {
    coupon: data?.data,
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
