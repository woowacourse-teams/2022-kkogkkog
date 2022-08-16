import { COUPON_EVENT, COUPON_STATUS } from '@/types/client/coupon';
import { UserResponse } from '@/types/remote/response';

export default {
  current: [
    {
      couponId: 0,
      sender: {
        id: 1,
        email: 'euijinkk97@gmail.com',
        userId: 'aaa1234',
        workspaceId: 'woowacourse',
        imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
        password: '1234',
        nickname: '시지프',
      },
      receiver: {
        id: 0,
        email: 'wnsgur8397@naver.com',
        userId: 'aaa123',
        workspaceId: 'woowacourse',
        imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
        password: '1234',
        nickname: '준찌',
      },
      memberId: 1,
      nickname: '시지프',
      hashtag: '한턱쏘는',
      imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
      description: '시지프가 준찌에게 보낸 1번째 쿠폰',
      couponType: 'COFFEE',
      couponStatus: 'ACCEPTED',
      meetingDate: '2022-08-19',
      couponHistories: [
        {
          id: 3,
          nickname: '시지프',
          imageUrl: '/assets/images/logo.png',
          couponType: 'COFFEE',
          couponEvent: 'REQUEST',
          meetingDate: '2022-08-08',
          createdTime: '2022-08-08',
          message: '이때 만나자!',
        },
        {
          id: 2,
          nickname: '시지프',
          imageUrl: '/assets/images/logo.png',
          couponType: 'COFFEE',
          couponEvent: 'INIT',
          meetingDate: '2022-08-08',
          createdTime: '2022-08-08',
          message: '',
        },
        {
          id: 1,
          nickname: '시지프',
          imageUrl: '/assets/images/logo.png',
          couponType: 'COFFEE',
          couponEvent: 'REQUEST',
          meetingDate: '2022-08-08',
          createdTime: '2022-08-08',
          message: '선릉에서 만나자!',
        },
      ],
    },

    {
      couponId: 0,
      sender: {
        id: 1,
        email: 'euijinkk97@gmail.com',
        userId: 'aaa1234',
        workspaceId: 'woowacourse',
        imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
        password: '1234',
        nickname: '시지프',
      },
      receiver: {
        id: 0,
        email: 'wnsgur8397@naver.com',
        userId: 'aaa123',
        workspaceId: 'woowacourse',
        imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
        password: '1234',
        nickname: '준찌',
      },
      memberId: 1,
      nickname: '시지프',
      hashtag: '한턱쏘는',
      imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
      description: '시지프가 준찌에게 보낸 1번째 쿠폰',
      couponType: 'COFFEE',
      couponStatus: 'ACCEPTED',
      meetingDate: '2022-08-16',
      couponHistories: [
        {
          id: 3,
          nickname: '시지프',
          imageUrl: '/assets/images/logo.png',
          couponType: 'COFFEE',
          couponEvent: 'REQUEST',
          meetingDate: '2022-08-08',
          createdTime: '2022-08-08',
          message: '이때 만나자!',
        },
        {
          id: 2,
          nickname: '시지프',
          imageUrl: '/assets/images/logo.png',
          couponType: 'COFFEE',
          couponEvent: 'INIT',
          meetingDate: '2022-08-08',
          createdTime: '2022-08-08',
          message: '',
        },
        {
          id: 1,
          nickname: '시지프',
          imageUrl: '/assets/images/logo.png',
          couponType: 'COFFEE',
          couponEvent: 'REQUEST',
          meetingDate: '2022-08-08',
          createdTime: '2022-08-08',
          message: '선릉에서 만나자!',
        },
      ],
    },
  ],

  findReceivedCouponList(loggedUser: UserResponse) {
    return this.current.filter(({ receiver: { id: receiverId } }) => loggedUser.id === receiverId);
  },

  findSentCouponList(loggedUser: UserResponse) {
    return this.current.filter(({ sender: { id: senderId } }) => loggedUser.id === senderId);
  },

  findCoupon(id: string | readonly string[]) {
    if (typeof id !== 'string') {
      throw new Error('잘못된 접근입니다.');
    }

    return this.current.find(({ couponId }) => Number(id) === couponId);
  },

  getStatusAfterEvent(couponEvent: COUPON_EVENT): COUPON_STATUS {
    switch (couponEvent) {
      case 'REQUEST':
        return 'REQUESTED';
      case 'CANCEL':
        return 'READY';
      case 'DECLINE':
        return 'READY';
      case 'ACCEPT':
        return 'ACCEPTED';
      case 'FINISH':
        return 'FINISHED';
      default:
        return 'READY';
    }
  },
};
