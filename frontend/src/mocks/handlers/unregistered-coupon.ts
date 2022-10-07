import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import { UNREGISTERED_COUPON_STATUS } from '@/types/unregistered-coupon/client';

import unregisteredCouponMock from '../fixtures/unregistered-coupon';

export const unregisteredCouponHandler = [
  rest.get(`${BASE_URL}/coupons/unregistered/status`, (req, res, ctx) => {
    const type = req.url.searchParams.get('type') as UNREGISTERED_COUPON_STATUS;

    return res(ctx.status(200), ctx.json({ data: unregisteredCouponMock.current[type] }));
  }),
];
