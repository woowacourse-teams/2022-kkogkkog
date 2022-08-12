import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import coupons from '@/mocks/fixtures/coupon';
import users from '@/mocks/fixtures/users';
import { ChangeCouponStatusRequest, CreateCouponRequest } from '@/types/remote/request';

export const couponHandler = [
  rest.get(`${BASE_URL}/coupons`, (req, res, ctx) => {
    const { headers } = req;

    try {
      const loggedUser = users.findLoggedUser(headers.get('authorization'));

      const receivedCouponList = coupons.findReceivedCouponList(loggedUser);

      const sentCouponList = coupons.findSentCouponList(loggedUser);

      return res(
        ctx.status(200),
        ctx.json({ data: { received: receivedCouponList, sent: sentCouponList } })
      );
    } catch ({ message }) {
      return res(ctx.status(400), ctx.json({ error: message }));
    }
  }),

  rest.get(`${BASE_URL}/coupons/:couponId`, (req, res, ctx) => {
    const {
      params: { couponId },
    } = req;

    try {
      const coupon = coupons.findCoupon(couponId);

      return res(ctx.status(200), ctx.json(coupon));
    } catch ({ message }) {
      return res(ctx.status(400), ctx.json({ error: message }));
    }
  }),

  rest.post<CreateCouponRequest>(`${BASE_URL}/coupons`, (req, res, ctx) => {
    const {
      body: { receivers, ...body },
      headers,
    } = req;

    try {
      const loggedUser = users.findLoggedUser(headers.get('authorization'));

      const newCouponList = receivers.map(receiverId => {
        const receiver = users.findUser(receiverId);

        const newCoupon = {
          id: coupons.current.length + 1,
          sender: loggedUser,
          receiver,
          couponStatus: 'READY',
          ...body,
        };

        return newCoupon;
      });

      coupons.current = [...coupons.current, ...newCouponList];

      return res(ctx.status(200), ctx.json({ data: newCouponList }));
    } catch ({ message }) {
      return res(ctx.status(400), ctx.json({ error: message }));
    }
  }),

  rest.post<ChangeCouponStatusRequest>(`${BASE_URL}/coupons/:couponId/event`, (req, res, ctx) => {
    const {
      body: { couponEvent, meetingDate },
      params: { couponId },
    } = req;

    const newCouponList = coupons.current.map(coupon => {
      if (coupon.id === Number(couponId)) {
        if (meetingDate && couponEvent === 'REQUEST') {
          return {
            ...coupon,
            couponStatus: coupons.getStatusAfterEvent(couponEvent),
            meetingDate,
          };
        }

        if (couponEvent === 'CANCEL') {
          const omittedCoupon = { ...coupon };

          delete omittedCoupon.meetingDate;

          return { ...omittedCoupon, couponStatus: coupons.getStatusAfterEvent(couponEvent) };
        }

        return { ...coupon, couponStatus: coupons.getStatusAfterEvent(couponEvent) };
      }

      return coupon;
    });

    coupons.current = newCouponList;

    return res(ctx.status(200), ctx.json({ data: newCouponList }));
  }),

  rest.post<ChangeCouponStatusRequest>(
    `${BASE_URL}/coupons/:couponId/event/request`,
    (req, res, ctx) => {
      const {
        body: { couponEvent, meetingDate },
        params: { couponId },
      } = req;

      const newCouponList = coupons.current.map(coupon => {
        if (coupon.id === Number(couponId)) {
          return {
            ...coupon,
            couponStatus: coupons.getStatusAfterEvent(couponEvent),
            meetingDate,
          };
        }

        return coupon;
      });

      coupons.current = newCouponList;

      return res(ctx.status(200), ctx.json({ data: newCouponList }));
    }
  ),
];
