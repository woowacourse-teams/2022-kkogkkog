import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import coupons,  from '@/mocks/fixtures/coupon';
import { COUPON_STATUS } from '@/types/coupon/client';
import { ChangeCouponStatusRequest, CouponDetailResponse, CreateCouponRequest } from '@/types/coupon/remote';

export const couponHandler = [
  rest.get(`${BASE_URL}/coupons/accept`, (req, res, ctx) => {
    try {
      const reservationList = coupons.generateReservationList();

      return res(ctx.status(200), ctx.json({ data: reservationList }));
    } catch ({ message }) {
      return res(ctx.status(401), ctx.json({ message }));
    }
  }),

  rest.get(`${BASE_URL}/coupons/sent`, (req, res, ctx) => {
    try {
      const sentCoupons = coupons.findSentCouponList();

      return res(ctx.status(200), ctx.json({ data: sentCoupons }));
    } catch ({ message }) {
      return res(ctx.status(401), ctx.json({ message }));
    }
  }),

  rest.get(`${BASE_URL}/coupons/received`, (req, res, ctx) => {
    try {
      const receivedCoupons = coupons.findReceivedCouponList();

      return res(ctx.status(200), ctx.json({ data: receivedCoupons }));
    } catch ({ message }) {
      return res(ctx.status(401), ctx.json({ message }));
    }
  }),

  rest.get(`${BASE_URL}/coupons/:couponId`, (req, res, ctx) => {
    const {
      params: { couponId },
    } = req;

    try {
      const coupon = coupons.findCoupon(couponId as string);

      return res(ctx.status(200), ctx.json(coupon));
    } catch ({ message }) {
      return res(ctx.status(400), ctx.json({ message }));
    }
  }),

  rest.get(`${BASE_URL}/coupons/sent/status`, (req, res, ctx) => {
    const { url } = req;

    const searchCouponType = url.searchParams.get('type') as COUPON_STATUS;

    try {
      const sentCoupons = coupons.findSentCouponListByStatus(searchCouponType);

      return res(ctx.status(200), ctx.json({ data: sentCoupons }));
    } catch ({ message }) {
      return res(ctx.status(401), ctx.json({ message }));
    }
  }),

  rest.get(`${BASE_URL}/coupons/received/status`, (req, res, ctx) => {
    const { url } = req;

    const searchCouponType = url.searchParams.get('type') as COUPON_STATUS;

    try {
      const receivedCoupons = coupons.findReceivedCouponListByStatus(searchCouponType);

      return res(ctx.status(200), ctx.json({ data: receivedCoupons }));
    } catch ({ message }) {
      return res(ctx.status(401), ctx.json({ message }));
    }
  }),

  rest.post<CreateCouponRequest>(`${BASE_URL}/coupons`, (req, res, ctx) => {
    const {
      body: { receiverIds, ...body },
    } = req;

    try {
      const newCouponList: CouponDetailResponse[] = receiverIds.map(() => {
        const newCoupon: CouponDetailResponse = {
          id: coupons.current.length + 1,
          sender: {
            id: 1,
            imageUrl:
              'https://avatars.slack-edge.com/2022-06-18/3684887341301_d2148dd98666590092cd_512.png',
            nickname: '준찌',
          },
          receiver: {
            id: 2,
            imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
            nickname: '시지프',
          },
          couponStatus: 'READY',
          meetingDate: null,
          createdTime: '2022-08-08T10:10:10',
          couponHistories: [],
          ...body,
        };

        return newCoupon;
      });

      coupons.updateFixture([...coupons.current, ...newCouponList]);

      return res(ctx.status(200), ctx.json({ data: newCouponList }));
    } catch ({ message }) {
      return res(ctx.status(401), ctx.json({ message }));
    }
  }),

  rest.post<ChangeCouponStatusRequest>(`${BASE_URL}/coupons/:couponId/event`, (req, res, ctx) => {
    const {
      body: { couponEvent, meetingDate },
      params: { couponId },
    } = req;

    const newCouponList: CouponDetailResponse[] = coupons.current.map(coupon => {
      if (coupon.id !== Number(couponId)) {
        return coupon;
      }

      const couponStatus = coupons.getStatusAfterEvent(couponEvent);

      return {
        ...coupon,
        couponStatus,
        meetingDate: meetingDate ? `${meetingDate}T10:10:10` : null,
      };
    });

    coupons.updateFixture([...coupons.current, ...newCouponList]);

    return res(ctx.status(200), ctx.json({ data: newCouponList }));
  }),
];
