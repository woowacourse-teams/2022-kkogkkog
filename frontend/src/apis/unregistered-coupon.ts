import {
  UnregisteredCouponListByStatusRequest,
  UnregisteredCouponListResponse,
  UnregisteredCouponResponse,
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

export const getUnregisteredCoupon = async (id: number) => {
  const { data } = await client.get<UnregisteredCouponResponse>(`/coupons/unregistered/${id}`);

  return data;
};
