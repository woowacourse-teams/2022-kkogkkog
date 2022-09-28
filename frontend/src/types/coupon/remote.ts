import { YYYYMMDDhhmmss } from '@/types/utils';

import { Coupon, COUPON_ENG_TYPE, COUPON_EVENT, CouponHistory } from './client';

/** Request */

export interface CreateCouponRequest {
  receiverIds: number[];
  couponTag: string;
  couponMessage: string;
  couponType: COUPON_ENG_TYPE;
}

export interface ChangeCouponStatusRequest {
  couponEvent: COUPON_EVENT;
  meetingMessage?: string;
  meetingDate?: YYYYMMDDhhmmss;
}

/** Response */

export interface CouponListResponse {
  data: Coupon[];
}

export type CreateCouponResponse = CouponListResponse;
export type SentCouponResponse = CouponListResponse;
export type ReceivedCouponResponse = CouponListResponse;
export type SentCouponByStatusResponse = CouponListResponse;
export type ReceivedCouponByStatusResponse = CouponListResponse;

export interface CouponDetailResponse extends Coupon {
  couponHistories: CouponHistory[];
}

export interface AcceptedCouponListResponse {
  data: {
    meetingData: YYYYMMDDhhmmss;
    couponse: Coupon[];
  };
}
