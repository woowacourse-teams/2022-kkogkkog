import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import users from '@/mocks/fixtures/users';
import { EditMeRequest } from '@/types/user/remote';

export const userHandler = [
  rest.get(`${BASE_URL}/members/me`, (req, res, ctx) => {
    const { headers } = req;
    const token = headers.get('authorization');

    try {
      const user = users.findLoggedUser(token);

      return res(ctx.status(200, 'authorized'), ctx.json(user));
    } catch ({ message }) {
      return res(ctx.status(401, 'unauthorized'), ctx.json({ message }));
    }
  }),

  rest.put<EditMeRequest>(`${BASE_URL}/members/me`, (req, res, ctx) => {
    const { body, headers } = req;
    const token = headers.get('authorization');

    try {
      const loggedUser = users.findLoggedUser(token);

      users.current = users.current.map(user =>
        user.id === loggedUser.id ? { ...user, ...body } : user
      );

      return res(ctx.status(200, '정보 수정에 성공하였습니다.'));
    } catch ({ message }) {
      return res(ctx.status(401, 'unauthorized'), ctx.json({ message }));
    }
  }),

  rest.get<any>(`${BASE_URL}/members`, (req, res, ctx) => {
    const allUser = users.current.map(user => {
      const { id, userId, nickname, email, imageUrl } = user;

      return {
        id,
        userId,
        nickname,
        email,
        imageUrl,
      };
    });

    return res(ctx.status(200), ctx.json({ data: allUser }));
  }),

  rest.get<any>(`${BASE_URL}/members/me/histories`, (req, res, ctx) => {
    const { headers } = req;
    const token = headers.get('authorization');

    try {
      const { histories } = users.findLoggedUser(token);

      return res(ctx.status(200, 'authorized'), ctx.json({ data: histories }));
    } catch ({ message }) {
      return res(ctx.status(400, 'unauthorized'), ctx.json({ message }));
    }
  }),

  rest.put(`${BASE_URL}/members/me/histories`, (req, res, ctx) => {
    const { headers } = req;
    const token = headers.get('authorization');

    try {
      const user = users.findLoggedUser(token);

      user.histories = user.histories.map(history => ({
        ...history,
        isRead: true,
      }));

      user.unReadCount = 0;

      return res(ctx.status(200, 'authorized'));
    } catch ({ message }) {
      return res(ctx.status(400, 'unauthorized'), ctx.json({ message }));
    }
  }),

  rest.put<any>(`${BASE_URL}/members/me/histories/:historyId`, (req, res, ctx) => {
    const {
      headers,
      params: { historyId },
    } = req;
    const token = headers.get('authorization');

    try {
      const user = users.findLoggedUser(token);

      user.histories = user.histories.map(history => {
        if (history.id === Number(historyId)) {
          return {
            ...history,
            isRead: true,
          };
        }

        return history;
      });

      if (user.unReadCount > 0) {
        user.unReadCount = user.unReadCount - 1;
      }

      return res(ctx.status(200, 'authorized'));
    } catch ({ message }) {
      return res(ctx.status(400, 'unauthorized'), ctx.json({ message }));
    }
  }),
];
