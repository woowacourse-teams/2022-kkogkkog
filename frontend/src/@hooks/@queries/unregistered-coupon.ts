import {
  getUnregisteredCoupon,
  getUnregisteredCouponListByStatus,
} from '@/apis/unregistered-coupon';
import { UnregisteredCouponListByStatusRequest } from '@/types/unregistered-coupon/remote';

import { useQuery } from './utils';

const QUERY_KEY = {
  unregisteredCoupon: 'unregisteredCoupon',
  unregisteredCouponListByStatus: 'unregisteredCouponListByStatus',
};

export const useFetchUnregisteredCoupon = (id: number) => {
  const { data, isLoading } = useQuery(
    [QUERY_KEY.unregisteredCoupon, id],
    () => getUnregisteredCoupon(id),
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
