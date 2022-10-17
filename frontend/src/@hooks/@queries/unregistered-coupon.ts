import { useQueryClient } from 'react-query';

import {
  createUnregisteredCoupon,
  getUnregisteredCouponById,
  getUnregisteredCouponListByStatus,
  registerUnregisteredCoupon,
} from '@/apis/unregistered-coupon';
import {
  RegisterUnregisteredCouponRequest,
  UnregisteredCouponListByStatusRequest,
} from '@/types/unregistered-coupon/remote';

import { getUnregisteredCouponByCode } from '../../apis/unregistered-coupon';
import { useLoading } from '../@common/useLoading';
import { useMutation, useQuery } from './utils';

const QUERY_KEY = {
  unregisteredCoupon: 'unregisteredCoupon',
  unregisteredCouponListByStatus: 'unregisteredCouponListByStatus',

  ISSUED: 'ISSUED',
  REGISTERED: 'REGISTERED',
  EXPIRED: 'EXPIRED',
};

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

export const useRegisteredUnregisteredCouponMutation = ({
  couponCode,
}: RegisterUnregisteredCouponRequest) => {
  const queryClient = useQueryClient();
  const { showLoading, hideLoading } = useLoading();

  return useMutation(registerUnregisteredCoupon, {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.unregisteredCoupon]);
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });
};
