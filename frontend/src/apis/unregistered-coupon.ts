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
    `/coupons/unregistered/status?type=${type}`
  );

  return data;
};

export const getUnregisteredCouponById = async (id: number) => {
  const { data } = await client.get<UnregisteredCouponResponse>(`/coupons/unregistered/${id}`);

  return data;
};

export const createUnregisteredCoupon = async (body: CreateUnregisteredCouponRequest) => {
  const { data } = await client.post<UnregisteredCouponListResponse>('/coupons/unregistered', body);

  return data;
};

export const getUnregisteredCouponByCode = async (couponCode: string) => {
  const { data } = await client.get<UnregisteredCouponResponse>(
    `/coupons/unregistered/code?couponCode=${couponCode}`
  );

  return data;
};

export const registerUnregisteredCoupon = async (body: RegisterUnregisteredCouponRequest) => {
  const { data } = await client.post<UnregisteredCouponResponse>('/coupons/code', body);

  return data;
};
