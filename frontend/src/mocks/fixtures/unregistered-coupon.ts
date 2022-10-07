export default {
  current: {
    ISSUED: [
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
        createdTime: '2022-10-07T14:32:22',
      },
    ],
    REGISTERED: [
      {
        id: 1,
        couponCode: 'asdfghjkqwertyui',
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
        createdTime: '2022-10-07T14:32:22',
      },
    ],
    EXPIRED: [
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
        unregisteredCouponStatus: 'EXPIRED',
        createdTime: '2022-10-07T14:32:22',
      },
    ],
  },
};
