import { UNREGISTERED_COUPON_STATUS } from '@/types/unregistered-coupon/client';
import { CreateUnregisteredCouponRequest } from '@/types/unregistered-coupon/remote';

import { Valueof } from './../../types/utils';

export default {
  current: [
    {
      id: 1,
      couponCode: 'asdfghjkqwertyui',
      couponId: null,
      sender: {
        id: 1,
        nickname: '시지프',
        imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
      },
      receiver: null,
      couponTag: '즐코!',
      couponMessage: '하하하',
      couponType: 'COFFEE',
      unregisteredCouponStatus: 'ISSUED',
      createdTime: '2022-10-12T14:32:22',
    },
    {
      id: 2,
      couponCode: 'rkfkfjgmdoemdldk',
      couponId: 1,
      sender: {
        id: 1,
        nickname: '시지프',
        imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
      },
      receiver: {
        id: 2,
        nickname: '준찌',
        imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
      },
      couponTag: '즐코!',
      couponMessage: '하하하',
      couponType: 'COFFEE',
      unregisteredCouponStatus: 'REGISTERED',
      createdTime: '2022-10-12T14:32:22',
    },
    {
      id: 3,
      couponCode: 'dlfoekermldodeoe',
      couponId: null,
      sender: {
        id: 1,
        nickname: '시지프',
        imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
      },
      receiver: null,
      couponTag: '즐코!',
      couponMessage: '하하하',
      couponType: 'COFFEE',
      unregisteredCouponStatus: 'EXPIRED',
      createdTime: '2022-10-12T14:32:22',
    },
  ],
  findUnregisteredCouponListByStatus(status: UNREGISTERED_COUPON_STATUS) {
    const unregisteredCouponList = this.current.filter(
      ({ unregisteredCouponStatus }) => unregisteredCouponStatus === status
    );

    return unregisteredCouponList;
  },

  findUnregisteredCoupon(unregisteredCouponId: number) {
    const coupon = this.current.find(({ id }) => id === unregisteredCouponId);

    if (!coupon) {
      throw new Error('쿠폰이 없습니다.');
    }

    return coupon;
  },

  findUnregisteredCouponByCode(couponCode: string) {
    const coupon = this.current.find(coupon => coupon.couponCode === couponCode);

    if (!coupon) {
      throw new Error('쿠폰이 없습니다.');
    }

    return coupon;
  },

  deleteUnregisteredCoupon(unregisteredCouponId: number) {
    const newUnregisteredCouponList = this.current.filter(({ id }) => id !== unregisteredCouponId);

    this.current = newUnregisteredCouponList;
  },

  createUnregisteredCoupon({ id, body }: { id: number; body: CreateUnregisteredCouponRequest }) {
    const { couponMessage, couponTag, couponType } = body;

    return {
      id,
      couponCode: `asdfghjkqwertyui${id}`,
      couponId: null,
      sender: {
        id: 1,
        nickname: '시지프',
        imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
      },
      receiver: null,
      unregisteredCouponStatus: 'ISSUED',
      createdTime: '2022-10-12T14:32:22',
      couponTag,
      couponMessage,
      couponType,
    };
  },

  addUnregisteredCoupon(unregisteredCoupon: Valueof<typeof this.current>[]) {
    this.current = [...this.current, ...unregisteredCoupon];
  },
};
