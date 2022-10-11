import { UNREGISTERED_COUPON_STATUS, UnregisteredCoupon } from './client';

export interface UnregisteredCouponListByStatusRequest {
  type: UNREGISTERED_COUPON_STATUS;
}

export interface UnregisteredCouponListResponse {
  data: UnregisteredCoupon[];
}
