export default {
  current: [
    {
      id: 0,
      email: 'wnsgur8397@naver.com',
      userId: 'aaa123',
      imageUrl:
        'https://avatars.slack-edge.com/2022-06-18/3684887341301_d2148dd98666590092cd_512.png',
      nickname: '준찌',
      workspaceId: 'fduashdf',
      workspaceName: 'woowacourse',
      unReadCount: 3,
      histories: [
        {
          id: 1,
          nickname: '시지프',
          imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
          couponId: 1,
          couponType: 'COFFEE',
          couponEvent: 'REQUEST',
          isRead: false,
          meetingDate: '2022-08-08T13:50:33',
          createdTime: '2022-08-08T13:50:33',
        },
        {
          id: 2,
          nickname: '시지프',
          imageUrl: 'https://avatars.githubusercontent.com/u/24906022?s=48&v=4',
          couponId: 2,
          couponType: 'COFFEE',
          couponEvent: 'REQUEST',
          isRead: false,
          meetingDate: '2022-08-08T13:50:33',
          createdTime: '2022-08-08T13:50:33',
        },
      ],
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
