export default {
  current: [
    { id: 0, email: 'wnsgur8397@naver.com', password: '1234', nickname: '준찌' },
    { id: 1, email: 'lll@naver.com', password: '1234', nickname: '시지프' },
  ],

  findLoggedUser(headers) {
    return this.current.find(
      ({ email: userToken }) => headers.headers.authorization === `Bearer ${userToken}`
    );
  },

  findUser(id: number) {
    return this.current.find(({ userId }) => userId === id);
  },
};
