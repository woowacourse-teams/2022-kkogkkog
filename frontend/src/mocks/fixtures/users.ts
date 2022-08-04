export default {
  current: [
    {
      id: 0,
      email: 'wnsgur8397@naver.com',
      userId: 'aaa123',
      workspaceId: 'woowacourse',
      imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
      password: '1234',
      nickname: '준찌',
      unReadCount: 3,
      histories: [
        {
          id: 1,
          nickname: '시지프',
          imageUrl: '/assets/images/logo.png',
          couponId: 1,
          couponType: 'COFFEE',
          couponEvent: 'REQUEST',
          meetingTime: '2022-08-08',
          isRead: false,
          createdAt: '2022-08-08',
        },
        {
          id: 2,
          nickname: '시지프',
          imageUrl: '/assets/images/logo.png',
          couponId: 1,
          couponType: 'COFFEE',
          couponEvent: 'INIT',
          meetingTime: '2022-08-08',
          isRead: false,
          createdAt: '2022-08-08',
        },
        {
          id: 3,
          nickname: '시지프',
          imageUrl: '/assets/images/logo.png',
          couponId: 1,
          couponType: 'COFFEE',
          couponEvent: 'REQUEST',
          meetingTime: '2022-08-08',
          isRead: false,
          createdAt: '2022-08-08',
        },
      ],
    },
    {
      id: 1,
      email: 'euijinkk97@gmail.com',
      userId: 'aaa1234',
      workspaceId: 'woowacourse',
      imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
      password: '1234',
      nickname: '시지프',
      unReadCount: 3,
      histories: [
        {
          id: 1,
          nickname: '준찌',
          imageUrl: '/assets/images/logo.png',
          couponId: 1,
          couponType: 'COFFEE',
          couponEvent: 'REQUEST',
          meetingTime: '2022-08-08',
          isRead: false,
        },
        {
          id: 2,
          nickname: '준찌',
          imageUrl: '/assets/images/logo.png',
          couponId: 1,
          couponType: 'COFFEE',
          couponEvent: 'INIT',
          meetingTime: '2022-08-08',
          isRead: false,
        },
        {
          id: 3,
          nickname: '준찌',
          imageUrl: '/assets/images/logo.png',
          couponId: 1,
          couponType: 'COFFEE',
          couponEvent: 'REQUEST',
          meetingTime: '2022-08-08',
          isRead: false,
        },
      ],
    },
    {
      id: 2,
      email: 'lllafssfa@naver.com',
      userId: 'aaa1235',
      workspaceId: 'woowacourse',
      imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
      password: '1234',
      nickname: '아서',
      unReadCount: 0,
      histories: [],
    },
    {
      id: 3,
      email: 'lllafasdfasdfasdfssfa@naver.com',
      userId: 'aaa1236',
      workspaceId: 'woowacourse',
      imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
      password: '1234',
      nickname: '정',
      unReadCount: 0,
      histories: [],
    },
  ],

  findLoggedUser(authorizationToken: string | null) {
    const loggedUser = this.current.find(
      ({ email: userToken }) => authorizationToken === `Bearer ${userToken}`
    );

    if (!loggedUser) {
      throw new Error('인증 정보가 없습니다.');
    }

    return loggedUser;
  },

  findUser(id: number) {
    const user = this.current.find(({ id: userId }) => id === userId);

    if (!user) {
      throw new Error('사용자를 찾을 수 없습니다.');
    }

    return user;
  },
};
