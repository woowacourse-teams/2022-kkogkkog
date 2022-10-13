import { Coupon, COUPON_EVENT, COUPON_STATUS, CouponHistory } from '@/types/coupon/client';
import { YYYYMMDDhhmmss } from '@/types/utils';

export type FixtureCouponType = Coupon & { couponHistories: CouponHistory[] };

type FixtureType = {
  current: FixtureCouponType[];
  findAcceptedCouponList: () => Coupon[];
  findReceivedCouponList: () => Coupon[];
  findSentCouponList: () => Coupon[];
  findSentCouponListByStatus: (status: COUPON_STATUS) => Coupon[];
  findReceivedCouponListByStatus: (status: COUPON_STATUS) => Coupon[];
  findCoupon: (couponId: string) => Coupon;
  getStatusAfterEvent: (couponEvent: COUPON_EVENT) => COUPON_STATUS;
  updateFixture: (newFixtureCurrent: FixtureCouponType[]) => void;
  generateReservationList: () => { meetingDate: YYYYMMDDhhmmss; coupons: Coupon[] }[];
};

const fixture: FixtureType = {
  current: [
    {
      id: 1,
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
      couponTag: '한턱쏘는',
      couponMessage: '시지프가 준찌에게 보낸 1번째 쿠폰',
      couponType: 'COFFEE',
      couponStatus: 'READY',
      createdTime: '2022-10-12T10:10:10',
      meetingDate: null,
      couponHistories: [
        {
          id: 1,
          nickname: '시지프',
          imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
          couponType: 'COFFEE',
          couponEvent: 'REQUEST',
          meetingMessage: '이때 만나자!',
          meetingDate: '2022-08-08T10:10:10',
          createdTime: '2022-08-08T10:10:10',
        },
      ],
    },
    {
      id: 2,
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
      couponTag: '한턱쏘는',
      couponMessage: '시지프가 준찌에게 보낸 2번째 쿠폰',
      couponType: 'COFFEE',
      couponStatus: 'REQUESTED',
      meetingDate: '2022-10-12T10:10:10',
      createdTime: '2022-10-12T10:10:10',
      couponHistories: [
        {
          id: 1,
          nickname: '시지프',
          imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
          couponType: 'COFFEE',
          couponEvent: 'REQUEST',
          meetingMessage: '이때 만나자!',
          meetingDate: '2022-08-08T10:10:10',
          createdTime: '2022-08-08T10:10:10',
        },
      ],
    },
    {
      id: 3,
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
      couponTag: '한턱쏘는',
      couponMessage: '시지프가 준찌에게 보낸 2번째 쿠폰',
      couponType: 'COFFEE',
      couponStatus: 'ACCEPTED',
      meetingDate: '2022-10-12T10:10:10',
      createdTime: '2022-10-12T10:10:10',
      couponHistories: [
        {
          id: 1,
          nickname: '시지프',
          imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
          couponType: 'COFFEE',
          couponEvent: 'ACCEPT',
          meetingMessage: '이때 만나자!',
          meetingDate: '2022-08-08T10:10:10',
          createdTime: '2022-08-08T10:10:10',
        },
      ],
    },
    {
      id: 4,
      sender: {
        id: 2,
        imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
        nickname: '시지프',
      },
      receiver: {
        id: 1,
        imageUrl:
          'https://avatars.slack-edge.com/2022-06-18/3684887341301_d2148dd98666590092cd_512.png',
        nickname: '준찌',
      },
      couponTag: '한턱쏘는',
      couponMessage: '시지프가 준찌에게 보낸 2번째 쿠폰',
      couponType: 'COFFEE',
      couponStatus: 'REQUESTED',
      meetingDate: '2022-10-12T10:10:10',
      createdTime: '2022-10-12T10:10:10',
      couponHistories: [],
    },
  ],

  findAcceptedCouponList() {
    return this.current.filter(({ couponStatus }) => couponStatus === 'ACCEPTED');
  },

  findReceivedCouponList() {
    return this.current.filter(({ receiver }) => receiver.id === 1);
  },

  findSentCouponList() {
    return this.current.filter(({ sender }) => sender.id === 1);
  },

  findSentCouponListByStatus(status) {
    const sentCouponList = this.findSentCouponList();

    return sentCouponList.filter(({ couponStatus }) => couponStatus === status);
  },

  findReceivedCouponListByStatus(status) {
    const receivedCouponList = this.findReceivedCouponList();

    return receivedCouponList.filter(({ couponStatus }) => couponStatus === status);
  },

  findCoupon(couponId) {
    const coupon = this.current.find(({ id }) => Number(couponId) === id);

    if (!coupon) {
      throw new Error('잘못된 접근입니다.');
    }

    return coupon;
  },

  getStatusAfterEvent(couponEvent) {
    const mapper: Record<COUPON_EVENT, COUPON_STATUS> = {
      INIT: 'READY',
      REQUEST: 'REQUESTED',
      CANCEL: 'READY',
      DECLINE: 'READY',
      ACCEPT: 'ACCEPTED',
      FINISH: 'FINISHED',
    };

    return mapper[couponEvent] ?? 'READY';
  },

  updateFixture(newCouponList) {
    this.current = newCouponList;
  },

  generateReservationList() {
    const acceptedCoupons = this.findAcceptedCouponList();

    const reservationMap = acceptedCoupons.reduce((prev, current) => {
      if (!current.meetingDate) {
        return prev;
      }

      const prevACceptedCoupons = prev[current.meetingDate] ?? [];

      return { ...prev, [current.meetingDate]: [...prevACceptedCoupons, current] };
    }, {} as Record<string, any[]>);

    const reservationList = Object.entries(reservationMap).map(([key, value]) => ({
      meetingDate: key as YYYYMMDDhhmmss,
      coupons: value as Coupon[],
    }));

    return reservationList;
  },
};

export default fixture;
