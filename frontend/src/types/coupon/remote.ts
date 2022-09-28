import { YYYYMMDD, YYYYMMDDhhmmss } from '@/types/utils';

import { Coupon, COUPON_ENG_TYPE, COUPON_EVENT, COUPON_STATUS, CouponHistory } from './client';

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
  meetingDate?: YYYYMMDD;
}

export interface CouponListByStatusRequest {
  type: COUPON_STATUS;
}

export type SentCouponListByStatusRequest = CouponListByStatusRequest;
export type ReceivedCouponListByStatusRequest = CouponListByStatusRequest;

/** Response */

export interface CouponListResponse {
  data: Coupon[];
}

export type CreateCouponListResponse = CouponListResponse;
export type SentCouponListResponse = CouponListResponse;
export type ReceivedCouponListResponse = CouponListResponse;
export type SentCouponListByStatusResponse = CouponListResponse;
export type ReceivedCouponListByStatusResponse = CouponListResponse;

export interface CouponDetailResponse extends Coupon {
  couponHistories: CouponHistory[];
}

export interface AcceptedCouponListResponse {
  data: {
    meetingDate: YYYYMMDDhhmmss;
    coupons: Coupon[];
  };
}
