import { KKOGKKOG_ENG_TYPE } from '@/types/client/kkogkkog';

export interface JoinRequest {
  nickname: string;
  email: string;
  password: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface CreateKkogKkogRequest {
  receivers: number[];
  backgroundColor: string;
  modifier: string;
  couponType: KKOGKKOG_ENG_TYPE;
  message: string;
}
