import { COUPON_ENG_TYPE, COUPON_HASHTAGS } from '@/types/coupon/client';
import { Member } from '@/types/user/client';
import { YYYYMMDDhhmmss } from '@/types/utils';

import { UNREGISTERED_COUPON_STATUS } from './client';

export interface UnregisteredCouponListByStatusRequest {
  type: UNREGISTERED_COUPON_STATUS;
}

export interface UnregisteredCouponListResponse {
  data: UnregisteredCouponResponse[];
}

export interface UnregisteredCouponResponse {
  id: number;
  couponCode: string;
  couponId: number | null;
  sender: Member;
  receiver: Member | null;
  couponTag: COUPON_HASHTAGS;
  couponMessage: string;
  couponType: COUPON_ENG_TYPE;
  unregisteredCouponStatus: UNREGISTERED_COUPON_STATUS;
  createdTime: YYYYMMDDhhmmss;
}

export interface RegisterUnregisteredCouponRequest {
  couponCode: string;
}

export interface CreateUnregisteredCouponRequest {
  quantity: number;
  couponTag: COUPON_HASHTAGS;
  couponMessage: string;
  couponType: COUPON_ENG_TYPE;
}
