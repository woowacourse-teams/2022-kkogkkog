import { useQueryClient } from 'react-query';

import {
  createUnregisteredCoupon,
  deleteUnregisteredCoupon,
  getUnregisteredCouponById,
  getUnregisteredCouponListByStatus,
  registerUnregisteredCoupon,
} from '@/apis/unregistered-coupon';
import { UnregisteredCouponListByStatusRequest } from '@/types/unregistered-coupon/remote';

import { getUnregisteredCouponByCode } from '../../apis/unregistered-coupon';
import { useLoading } from '../@common/useLoading';
import { useCouponInvalidationOnRegisterUnregisteredCoupon } from './coupon';
import { useMutation, useQuery } from './utils';

const QUERY_KEY = {
  unregisteredCoupon: 'unregisteredCoupon',
  unregisteredCouponListByStatus: 'unregisteredCouponListByStatus',

  ISSUED: 'ISSUED',
  REGISTERED: 'REGISTERED',
  EXPIRED: 'EXPIRED',
} as const;

export const useFetchUnregisteredCouponById = (id: number) => {
  const { data, isLoading } = useQuery(
    [QUERY_KEY.unregisteredCoupon, id],
    () => getUnregisteredCouponById(id),
    {
      staleTime: 10000,
    }
  );

  return {
    unregisteredCoupon: data,
    isLoading,
  };
};

export const useFetchUnregisteredCouponByCode = (couponCode: string) => {
  const { data, isLoading } = useQuery(
    [QUERY_KEY.unregisteredCoupon, couponCode],
    () => getUnregisteredCouponByCode(couponCode),
    {
      staleTime: 10000,
    }
  );

  return {
    unregisteredCoupon: data,
    isLoading,
  };
};

export const useFetchUnregisteredCouponListByStatus = (
  body: UnregisteredCouponListByStatusRequest
) => {
  const { data, isLoading } = useQuery(
    [QUERY_KEY.unregisteredCouponListByStatus, body.type],
    () => getUnregisteredCouponListByStatus(body),
    {
      staleTime: 10000,
    }
  );

  return {
    unregisteredCouponListByStatus: data?.data ?? [],
    isLoading,
  };
};

/** Mutation */

export const useCreateUnregisteredCouponMutation = () => {
  const queryClient = useQueryClient();
  const { showLoading, hideLoading } = useLoading();

  return useMutation(createUnregisteredCoupon, {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.unregisteredCouponListByStatus, QUERY_KEY.ISSUED]);
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });
};

export const useRegisterUnregisteredCouponMutation = (id: number) => {
  const queryClient = useQueryClient();
  const { showLoading, hideLoading } = useLoading();
  const { invalidateReceivedCouponList } = useCouponInvalidationOnRegisterUnregisteredCoupon();

  return useMutation(registerUnregisteredCoupon, {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.unregisteredCoupon, id]);
      queryClient.invalidateQueries([QUERY_KEY.unregisteredCouponListByStatus, QUERY_KEY.ISSUED]);

      invalidateReceivedCouponList();
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });
};

export const useDeleteUnregisteredCouponMutation = (id: number) => {
  const queryClient = useQueryClient();
  const { showLoading, hideLoading } = useLoading();

  return useMutation(deleteUnregisteredCoupon, {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.unregisteredCouponListByStatus, QUERY_KEY.ISSUED]);
      queryClient.removeQueries([QUERY_KEY.unregisteredCoupon, id]);
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });
};
