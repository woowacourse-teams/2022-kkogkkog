import { COUPON_ENG_TYPE, COUPON_EVENT } from '@/types/client/coupon';

export interface User {
  id: number;
  email: string;
  nickname: string;
}

export type UserHistory = {
  id: number;
  nickname: string;
  imageUrl: string;
  couponId: number;
  couponType: COUPON_ENG_TYPE;
  couponEvent: COUPON_EVENT;
  meetingTime?: string;
  isRead: boolean;
  createdAt: string;
};
