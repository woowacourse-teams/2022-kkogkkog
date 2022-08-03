import {
  COUPON_COLORS,
  COUPON_ENG_TYPE,
  COUPON_LIST_TYPE,
  COUPON_MODIFIERS,
  COUPON_STATUS,
} from '@/types/client/coupon';
import { User, UserHistory } from '@/types/client/user';

export interface UserResponse {
  id: number;
  userId: string;
  workspaceId: string;
  email: string;
  nickname: string;
  imageUrl: string;
}

export interface MeResponse extends UserResponse {
  unReadCount: number;
  histories: UserHistory[];
}

export type UserListResponse = { data: UserResponse[] };

export interface LoginResponse {
  accessToken: string;
}

export interface OAuthLoginResponse {
  accessToken: string;
  isCreated: boolean;
}

export interface CouponResponse {
  id: number;
  sender: User;
  receiver: User;
  modifier: COUPON_MODIFIERS;
  message: string;
  backgroundColor: COUPON_COLORS;
  couponType: COUPON_ENG_TYPE;
  couponStatus: COUPON_STATUS;
  meetingDate?: string;
}

export type CouponListResponse = { data: Record<COUPON_LIST_TYPE, CouponResponse[]> };

export type UserHistoryResponse = {
  data?: UserHistory[];
};
