import {
  CreateUnregisteredCouponRequest,
  RegisterUnregisteredCouponRequest,
  UnregisteredCouponListByStatusRequest,
  UnregisteredCouponListResponse,
  UnregisteredCouponResponse,
} from '@/types/unregistered-coupon/remote';

import { client } from '.';

export const getUnregisteredCouponListByStatus = async ({
  type,
}: UnregisteredCouponListByStatusRequest) => {
  const { data } = await client.get<UnregisteredCouponListResponse>(
    `/lazy-coupons/status?type=${type}`
  );

  return data;
};

export const getUnregisteredCouponById = async (id: number) => {
  const { data } = await client.get<UnregisteredCouponResponse>(`/lazy-coupons/${id}`);

  return data;
};

export const createUnregisteredCoupon = async (body: CreateUnregisteredCouponRequest) => {
  const { data } = await client.post<UnregisteredCouponListResponse>('/lazy-coupons', body);

  return data;
};

export const getUnregisteredCouponByCode = async (couponCode: string) => {
  const { data } = await client.get<UnregisteredCouponResponse>(
    `/lazy-coupons/code?couponCode=${couponCode}`
  );

  return data;
};

export const registerUnregisteredCoupon = async (body: RegisterUnregisteredCouponRequest) => {
  const { data } = await client.post<UnregisteredCouponResponse>('/lazy-coupons/register', body);

  return data;
};

export const deleteUnregisteredCoupon = async (id: number) => {
  const { data } = await client.delete<UnregisteredCouponResponse>(`/lazy-coupons/${id}`);

  return data;
};
