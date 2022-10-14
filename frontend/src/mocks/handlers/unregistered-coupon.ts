import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import { UNREGISTERED_COUPON_STATUS } from '@/types/unregistered-coupon/client';
import { RegisterUnregisteredCouponRequest } from '@/types/unregistered-coupon/remote';

import unregisteredCouponMock from '../fixtures/unregistered-coupon';

export const unregisteredCouponHandler = [
  rest.get(`${BASE_URL}/coupons/unregistered/status`, (req, res, ctx) => {
    const type = req.url.searchParams.get('type') as UNREGISTERED_COUPON_STATUS;

    return res(
      ctx.status(200),
      ctx.json({ data: unregisteredCouponMock.findUnregisteredCouponListByStatus(type) })
    );
  }),

  rest.get(`${BASE_URL}/coupons/unregistered/:unregisteredCouponId`, (req, res, ctx) => {
    const {
      params: { unregisteredCouponId },
    } = req;

    try {
      const coupon = unregisteredCouponMock.findUnregisteredCoupon(Number(unregisteredCouponId));

      return res(ctx.status(200), ctx.json(coupon));
    } catch ({ message }) {
      return res(ctx.status(400), ctx.json({ message }));
    }
  }),

  // @TODO: coupon/:couponId 가 먼저 인식되는 현상 해결
  rest.get(`${BASE_URL}/coupons/unregistered`, (req, res, ctx) => {
    const couponCode = req.url.searchParams.get('couponCode');

    if (!couponCode) {
      return res(ctx.status(400), ctx.json({ message: '해당 쿠폰이 없습니다.' }));
    }

    try {
      const coupon = unregisteredCouponMock.findUnregisteredCouponByCode(couponCode);

      return res(ctx.status(200), ctx.json(coupon));
    } catch ({ message }) {
      return res(ctx.status(400), ctx.json({ message }));
    }
  }),

  rest.post<RegisterUnregisteredCouponRequest>(`${BASE_URL}/coupons/code`, (req, res, ctx) => {
    const {
      body: { couponCode },
    } = req;

    try {
      const coupon = unregisteredCouponMock.findUnregisteredCouponByCode(couponCode);

      if (coupon.unregisteredCouponStatus !== 'ISSUED') {
        return res(ctx.status(400), ctx.json({ message: '유효하지 않은 쿠폰입니다.' }));
      }

      unregisteredCouponMock.current = unregisteredCouponMock.current.map(coupon =>
        coupon.unregisteredCouponStatus === 'ISSUED'
          ? { ...coupon, unregisteredCouponStatus: 'REGISTERED' }
          : coupon
      );

      return res(ctx.status(200), ctx.json(coupon));
    } catch ({ message }) {
      return res(ctx.status(400), ctx.json({ message }));
    }
  }),
];
