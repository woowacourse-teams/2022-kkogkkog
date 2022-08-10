import { COUPON_ENG_TYPE, COUPON_EVENT } from '@/types/client/coupon';

export interface CommonHistory {
  id: number;
  nickname: string;
  imageUrl: string;
  couponType: COUPON_ENG_TYPE;
  couponEvent: COUPON_EVENT;
  meetingDate?: string;
  createdTime: string;
}
