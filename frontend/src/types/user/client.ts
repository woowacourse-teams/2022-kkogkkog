import { Coupon } from '../coupon/client';

export interface UserCouponHistory extends Coupon {
  isRead: boolean;
}
