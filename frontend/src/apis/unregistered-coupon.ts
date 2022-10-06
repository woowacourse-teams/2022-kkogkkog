import {
  UnregisteredCouponListByStatusRequest,
  UnregisteredCouponListResponse,
} from '@/types/unregistered-coupon/remote';

import { client } from '.';

export const getUnregisteredCouponListByStatus = async ({
  type,
}: UnregisteredCouponListByStatusRequest) => {
  const { data } = await client.get<UnregisteredCouponListResponse>(
    `/coupons/unregistered/status?type=${type}`
  );

  return data;
};
