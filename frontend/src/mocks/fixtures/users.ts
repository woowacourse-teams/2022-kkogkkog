export default {
  current: [
    { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
    { id: 1, email: 'euijinkk97@gmail.com', password: '1234', nickname: '시지프' },
    { id: 2, email: 'lllafssfa@naver.com', password: '1234', nickname: '아서' },
    { id: 3, email: 'lllafasdfasdfasdfssfa@naver.com', password: '1234', nickname: '정' },
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
