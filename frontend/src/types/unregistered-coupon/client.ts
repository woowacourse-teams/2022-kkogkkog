import { Valueof } from '../utils';

export const unregisteredCouponStatus = ['ISSUED', 'REGISTERED', 'EXPIRED'] as const;

export type UNREGISTERED_COUPON_STATUS = Valueof<typeof unregisteredCouponStatus>;
