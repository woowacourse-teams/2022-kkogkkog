import { couponHandler } from '@/mocks/handlers/coupon';
import { userHandler } from '@/mocks/handlers/user';

import { unregisteredCouponHandler } from './unregistered-coupon';

export const handlers = [...userHandler, ...couponHandler, ...unregisteredCouponHandler];
