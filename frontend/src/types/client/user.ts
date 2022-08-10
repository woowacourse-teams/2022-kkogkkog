import { CommonHistory } from './common';

export interface User {
  id: number;
  email: string;
  nickname: string;
}

export interface UserHistory extends CommonHistory {
  couponId: number;
  isRead: boolean;
}
