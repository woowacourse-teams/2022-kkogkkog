import { COUPON_EVENT, COUPON_STATUS } from '@/types/client/kkogkkog';
import { UserResponse } from '@/types/remote/response';

export default {
  current: [
    {
      id: 0,
      sender: { id: 1, email: 'euijinkk97@gmail.com', password: '1234', nickname: '시지프' },
      receiver: { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
      backgroundColor: '#FFFFFF',
      modifier: '한턱쏘는',
      message: '시지프가 준찌에게 보낸 1번째 쿠폰',
      couponType: 'COFFEE',
      couponStatus: 'READY',
    },
    {
      id: 1,
      sender: { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
      receiver: { id: 1, email: 'euijinkk97@gmail.com', password: '1234', nickname: '시지프' },
      backgroundColor: '#FFFFFF',
      modifier: '한턱쏘는',
      message: '준찌가 시지프에게 보낸 1번째 쿠폰',
      couponType: 'COFFEE',
      couponStatus: 'READY',
    },
    {
      id: 2,
      sender: { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
      receiver: { id: 1, email: 'euijinkk97@gmail.com', password: '1234', nickname: '시지프' },
      backgroundColor: '#FFFFFF',
      modifier: '한턱쏘는',
      message: '준찌가 시지프에게 보낸 2번째 쿠폰',
      couponType: 'MEAL',
      couponStatus: 'REQUESTED',
    },
    {
      id: 3,
      sender: { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
      receiver: { id: 1, email: 'euijinkk97@gmail.com', password: '1234', nickname: '시지프' },
      backgroundColor: '#FFFFFF',
      modifier: '한턱쏘는',
      message: '준찌가 시지프에게 보낸 2번째 쿠폰',
      couponType: 'DRINK',
      couponStatus: 'ACCEPTED',
    },
    {
      id: 4,
      sender: { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
      receiver: { id: 1, email: 'euijinkk97@gmail.com', password: '1234', nickname: '시지프' },
      backgroundColor: '#FFFFFF',
      modifier: '한턱쏘는',
      message: '준찌가 시지프에게 보낸 2번째 쿠폰',
      couponType: 'DRINK',
      couponStatus: 'FINISHED',
    },
    {
      id: 5,
      sender: { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
      receiver: { id: 1, email: 'euijinkk97@gmail.com', password: '1234', nickname: '시지프' },
      backgroundColor: '#FFFFFF',
      modifier: '한턱쏘는',
      message: '준찌가 시지프에게 보낸 2번째 쿠폰',
      couponType: 'COFFEE',
      couponStatus: 'READY',
    },
    {
      id: 6,
      sender: { id: 1, email: 'euijinkk97@gmail.com', password: '1234', nickname: '시지프' },
      receiver: { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
      backgroundColor: '#FFFFFF',
      modifier: '한턱쏘는',
      message: '시지프가 준찌에게 보낸 1번째 쿠폰',
      couponType: 'COFFEE',
      couponStatus: 'READY',
    },
    {
      id: 7,
      sender: { id: 1, email: 'euijinkk97@gmail.com', password: '1234', nickname: '시지프' },
      receiver: { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
      backgroundColor: '#FFFFFF',
      modifier: '한턱쏘는',
      message: '시지프가 준찌에게 보낸 1번째 쿠폰',
      couponType: 'DRINK',
      couponStatus: 'FINISHED',
    },
    {
      id: 8,
      sender: { id: 1, email: 'euijinkk97@gmail.com', password: '1234', nickname: '시지프' },
      receiver: { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
      backgroundColor: '#FFFFFF',
      modifier: '한턱쏘는',
      message: '시지프가 준찌에게 보낸 1번째 쿠폰',
      couponType: 'MEAL',
      couponStatus: 'ACCEPTED',
    },
    {
      id: 9,
      sender: { id: 1, email: 'euijinkk97@gmail.com', password: '1234', nickname: '시지프' },
      receiver: { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
      backgroundColor: '#FFFFFF',
      modifier: '한턱쏘는',
      message: '시지프가 준찌에게 보낸 1번째 쿠폰',
      couponType: 'COFFEE',
      couponStatus: 'REQUESTED',
    },
  ],

  findReceivedKkogKkogList(loggedUser: UserResponse) {
    return this.current.filter(({ receiver: { id: receiverId } }) => loggedUser.id === receiverId);
  },

  findSentKkogKkogList(loggedUser: UserResponse) {
    return this.current.filter(({ sender: { id: senderId } }) => loggedUser.id === senderId);
  },

  findKkogKkog(id: string | readonly string[]) {
    if (typeof id !== 'string') {
      throw new Error('잘못된 접근입니다.');
    }

    return this.current.find(({ id: kkogkkogId }) => Number(id) === kkogkkogId);
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
