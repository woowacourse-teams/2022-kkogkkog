import { User } from '@/types/domain';

export default {
  current: [
    {
      id: 0,
      sender: { id: 1, email: 'lll@naver.com', password: '1234', nickname: '시지프' },
      receiver: { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
      backgroundColor: '#FFFFFF',
      modifier: '한턱쏘는',
      message: 'hi',
      couponType: 'COFFEE',
    },
    {
      id: 1,
      sender: { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
      receiver: { id: 1, email: 'lll@naver.com', password: '1234', nickname: '시지프' },
      backgroundColor: '#FFFFFF',
      modifier: '한턱쏘는',
      message: 'hi',
      couponType: 'COFFEE',
    },
  ],

  findReceivedKkogKkogList(loggedUser: User) {
    return this.current.filter(({ receiver: { id: receiverId } }) => receiverId === loggedUser.id);
  },

  findSentKkogKkogList(loggedUser: User) {
    return this.current.filter(({ sender: { id: senderId } }) => senderId === loggedUser.id);
  },

  findKkogKkog(id: string | readonly string[]) {
    if (typeof id !== 'string') {
      throw new Error('잘못된 접근입니다.');
    }

    return this.current.find(({ id: kkogkkogId }) => kkogkkogId === Number(id));
  },
};
