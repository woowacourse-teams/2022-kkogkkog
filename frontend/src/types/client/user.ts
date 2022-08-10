import { CommonHistory } from './common';

export interface UserHistory extends CommonHistory {
  couponId: number;
  isRead: boolean;
}
