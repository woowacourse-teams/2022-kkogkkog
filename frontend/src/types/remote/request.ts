import { COUPON_ENG_TYPE, COUPON_EVENT } from '@/types/client/coupon';

import { MeResponse } from './response';

export type EditMeRequest = Pick<MeResponse, 'nickname'>;

export interface SignupRequest {
  nickname: string;
  accessToken: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface CreateCouponRequest {
  receiverIds: number[];
  hashtag: string;
  couponType: COUPON_ENG_TYPE;
  description: string;
}

export interface ChangeCouponStatusRequest {
  event: COUPON_EVENT;
  message?: string;
}

export interface CouponReservationRequest {
  couponId: number;
  meetingDate: string;
  message: string;
}

export interface ReadHistoryRequest {
  id: number;
}
