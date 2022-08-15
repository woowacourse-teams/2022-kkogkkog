import { COUPON_ENG_TYPE, COUPON_EVENT } from '@/types/client/coupon';

import { MeResponse } from './response';

export type EditMeRequest = Pick<MeResponse, 'nickname'>;

export interface JoinRequest {
  nickname: string;
  email: string;
  password: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface CreateCouponRequest {
  receivers: number[];
  backgroundColor: string;
  hashtag: string;
  couponType: COUPON_ENG_TYPE;
  message: string;
}

export interface ChangeCouponStatusRequest {
  event: COUPON_EVENT;
}

export interface CouponReservationRequest {
  couponId: number;
  event: COUPON_EVENT;
  meetingDate: string;
  message: string;
}

export interface ReadHistoryRequest {
  id: number;
}
