import { getUnregisteredCouponListByStatus } from '@/apis/unregistered-coupon';
import { UnregisteredCouponListByStatusRequest } from '@/types/unregistered-coupon/remote';

import { useQuery } from './utils';

const QUERY_KEY = {
  couponListByStatus: 'couponListByStatus',
};

export const useFetchUnregisteredCouponListByStatus = (
  body: UnregisteredCouponListByStatusRequest
) => {
  const { data, isLoading } = useQuery(
    [QUERY_KEY.couponListByStatus, body.type],
    () => getUnregisteredCouponListByStatus(body),
    {
      staleTime: 10000,
    }
  );

  return {
    couponListByStatus: data?.data ?? [],
    isLoading,
  };
};
