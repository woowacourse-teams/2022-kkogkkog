import { AxiosError } from 'axios';
import { useMemo } from 'react';
import { useMutation, useQuery, useQueryClient } from 'react-query';

import { useToast } from '@/@hooks/@common/useToast';
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
  coupon: 'coupon',
};

/** Query */

export const useFetchCouponList = () => {
  const { displayMessage } = useToast();

  const { data, ...rest } = useQuery([QUERY_KEY.couponList], getCouponList, {
    suspense: true,
    onError(error) {
      if (error instanceof AxiosError) {
        displayMessage(error?.response?.data?.message, true);
      }
    },
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

  const acceptedCouponList = useMemo(() => {
    const combinedCouponList = [...(couponList?.received ?? []), ...(couponList?.sent ?? [])];

    return combinedCouponList.reduce<Record<string, CouponResponse[]>>((prev, coupon) => {
      const { couponStatus, meetingDate } = coupon;

      if (couponStatus === 'ACCEPTED' && meetingDate) {
        return { ...prev, [meetingDate]: [...(prev[meetingDate] ?? []), coupon] };
      }

      return prev;
    }, {});
  }, [couponList]);

  return {
    couponList,
    parsedSentCouponList,
    parsedReceivedCouponList,
    acceptedCouponList,
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

  return useMutation(createCoupon, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.couponList);
    },
  });
};

export const useChangeCouponStatusMutation = (id: number) => {
  const queryClient = useQueryClient();

  return useMutation(changeCouponStatus, {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.coupon, id]);
    },
  });
};

export const useRequestCouponMutation = () => {
  const queryClient = useQueryClient();

  return useMutation(requestCoupon, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.couponList);
    },
  });
};
