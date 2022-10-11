import { UserResponse } from '@/types/user/remote';
import { YYYYMMDDhhmmss } from '@/types/utils';

import { COUPON_ENG_TYPE, COUPON_EVENT } from '../coupon/client';

export type Member = Pick<UserResponse, 'id' | 'nickname' | 'imageUrl'>;

export interface UserCouponHistory {
  id: number;
  couponId: number;
  couponType: COUPON_ENG_TYPE;
  couponEvent: COUPON_EVENT;
  meetingDate: YYYYMMDDhhmmss;
  createdTime: YYYYMMDDhhmmss;
  isRead: boolean;
  nickname: string;
  imageUrl: string;
}

export type OAuthType = 'slack' | 'google';
