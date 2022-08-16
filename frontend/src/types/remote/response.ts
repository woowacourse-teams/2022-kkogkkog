import {
  COUPON_ENG_TYPE,
  COUPON_HASHTAGS,
  COUPON_LIST_TYPE,
  COUPON_STATUS,
  CouponHistory,
} from '@/types/client/coupon';
import { UserHistory } from '@/types/client/user';

import { COUPON_MEMBER_TYPE } from './../client/coupon';

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
  isNew: boolean;
}

export interface CouponResponse {
  couponId: number;
  reservationId: number | null;
  memberId: number;
  nickname: string;
  imageUrl: string;
  hashtag: COUPON_HASHTAGS;
  description: string;
  couponType: COUPON_ENG_TYPE;
  couponStatus: COUPON_STATUS;
  message: string;
  meetingDate?: string;
  memberType: COUPON_MEMBER_TYPE;
}

export interface CouponDetailResponse {
  id: number;
  reservationId: number | null;
  senderId: number;
  senderNickname: string;
  senderImageUrl: string;
  receiverId: number;
  receiverNickname: string;
  receiverImageUrl: string;
  hashtag: COUPON_HASHTAGS;
  meetingDate?: string;
  description: string;
  couponType: COUPON_ENG_TYPE;
  couponStatus: COUPON_STATUS;
  couponHistories: CouponHistory[];
}

export type CouponCreateResponse = {
  data: Omit<
    CouponDetailResponse,
    'senderImageUrl' | 'receiverImageUrl' | 'couponHistories' | 'meetingDate'
  >[];
};

export type CouponListResponse = { data: Record<COUPON_LIST_TYPE, CouponResponse[]> };

export type UserHistoryResponse = {
  data: UserHistory[];
};
