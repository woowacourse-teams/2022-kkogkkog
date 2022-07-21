import {
  COUPON_STATUS,
  KKOGKKOG_COLORS,
  KKOGKKOG_ENG_TYPE,
  KKOGKKOG_MODIFIERS,
} from '@/types/client/kkogkkog';
import { User } from '@/types/client/user';

export interface MeResponse {
  id: number;
  email: string;
  nickname: string;
}

export interface UserResponse {
  id: number;
  email: string;
  nickname: string;
}

export type UserListResponse = UserResponse[];

export interface KkogKKogResponse {
  id: number;
  sender: User;
  receiver: User;
  modifier: KKOGKKOG_MODIFIERS;
  message: string;
  backgroundColor: KKOGKKOG_COLORS;
  couponType: KKOGKKOG_ENG_TYPE;
  couponStatus: COUPON_STATUS;
}

export type KkogKkogListResponse = Record<'received' | 'sent', KkogKKogResponse[]>;
