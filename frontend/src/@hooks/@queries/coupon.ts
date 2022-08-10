import { useMemo } from 'react';
import { useMutation, useQuery, useQueryClient } from 'react-query';

import {
  changeCouponStatus,
  createCoupon,
  getCoupon,
  getCouponList,
  requestCoupon,
} from '@/apis/coupon';
import { COUPON_STATUS } from '@/types/client/coupon';
import { CouponResponse } from '@/types/remote/response';

const QUERY_KEY = {
  couponList: 'couponList',
};

/** Query */

export const useFetchCouponList = () => {
  const { data, ...rest } = useQuery([QUERY_KEY.couponList], getCouponList, {
    suspense: true,
  });

  const couponList = data?.data?.data;

  const parsedSentCouponList = useMemo(
    () =>
      couponList &&
      couponList?.sent?.reduceRight<Record<COUPON_STATUS, CouponResponse[]>>(
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
      couponList &&
      couponList?.received?.reduceRight<Record<COUPON_STATUS, CouponResponse[]>>(
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

  return {
    couponList,
    parsedSentCouponList,
    parsedReceivedCouponList,
    ...rest,
  };
};

export const useFetchCoupon = (id: number) => {
  const { data, ...rest } = useQuery([QUERY_KEY.couponList], () => getCoupon(id));

  return {
    coupon: data?.data,
    ...rest,
  };
};

/** Mutation */

export const useCreateCouponMutation = () => {
  const queryClient = useQueryClient();

  return useMutation(createCoupon, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.couponList);
    },
    onError() {
      alert('입력창을 확인하고 다시 시도해주세요.');
    },
  });
};

export const useChangeCouponStatusMutation = () => {
  const queryClient = useQueryClient();

  return useMutation(changeCouponStatus, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.couponList);
    },
    onError() {
      alert('잘못된 접근입니다. 다시 시도해주세요.');
    },
  });
};

export const useRequestCouponMutation = () => {
  const queryClient = useQueryClient();

  return useMutation(requestCoupon, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.couponList);
    },
    onError() {
      alert('잘못된 접근입니다. 다시 시도해주세요.');
    },
  });
};
