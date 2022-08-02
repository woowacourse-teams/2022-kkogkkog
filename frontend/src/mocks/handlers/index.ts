import { couponHandler } from '@/mocks/handlers/coupon';
import { userHandler } from '@/mocks/handlers/user';

export const handlers = [...couponHandler, ...userHandler];
