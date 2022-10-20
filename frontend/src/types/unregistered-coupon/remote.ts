import { COUPON_ENG_TYPE, COUPON_HASHTAGS } from '@/types/coupon/client';

import { UNREGISTERED_COUPON_STATUS, UnregisteredCoupon } from './client';

export interface UnregisteredCouponListByStatusRequest {
  type: UNREGISTERED_COUPON_STATUS;
}

export interface UnregisteredCouponListResponse {
  data: UnregisteredCoupon[];
}

export type UnregisteredCouponResponse = UnregisteredCoupon;

export interface RegisterUnregisteredCouponRequest {
  couponCode: string;
}

export interface CreateUnregisteredCouponRequest {
  quantity: number;
  couponTag: COUPON_HASHTAGS;
  couponMessage: string;
  couponType: COUPON_ENG_TYPE;
}
