import { COUPON_ENG_TYPE, COUPON_HASHTAGS } from '../coupon/client';
import { Member } from '../user/client';
import { Valueof, YYYYMMDDhhmmss } from '../utils';

export const unregisteredCouponStatus = ['ISSUED', 'REGISTERED', 'EXPIRED'] as const;

export type UNREGISTERED_COUPON_STATUS = Valueof<typeof unregisteredCouponStatus>;

export interface UnregisteredCoupon {
  id: number;
  couponCode: string;
  couponId: number | null;
  sender: Member;
  receiver: Member | null;
  couponTag: COUPON_HASHTAGS;
  couponMessage: string;
  couponType: COUPON_ENG_TYPE;
  unregisteredCouponStatus: UNREGISTERED_COUPON_STATUS;
  createdTime: YYYYMMDDhhmmss;
}
