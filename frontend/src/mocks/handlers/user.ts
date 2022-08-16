import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import users from '@/mocks/fixtures/users';
import { EditMeRequest } from '@/types/remote/request';

export const userHandler = [
  rest.get<any>(`${BASE_URL}/members/me`, (req, res, ctx) => {
    const { headers } = req;

    try {
      const user = users.findLoggedUser(headers.get('authorization'));

      return res(ctx.status(200, 'authorized'), ctx.json(user));
    } catch ({ message }) {
      return res(ctx.status(400, 'unauthorized'), ctx.json({ message }));
    }
  }),

  rest.put<EditMeRequest>(`${BASE_URL}/members/me`, (req, res, ctx) => {
    const { body, headers } = req;

    const loggedUser = users.findLoggedUser(headers.get('authorization'));

    users.current = users.current.map(user =>
      user.id === loggedUser.id ? { ...user, ...body } : user
    );

    return res(ctx.status(200));
  }),

  rest.get<any>(`${BASE_URL}/members`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json({ data: users.current }));
  }),

  rest.post<any>(`${BASE_URL}/members`, (req, res, ctx) => {
    const { body: info } = req;

    const user = { id: users.current.length + 1, ...info };

    users.current.push(user);

    return res(ctx.status(201));
  }),

  rest.post<any>(`${BASE_URL}/login`, (req, res, ctx) => {
    const { password, email } = req.body;

    if (
      users.current.some(customer => customer.email === email && customer.password === password)
    ) {
      return res(ctx.status(200, 'ok'), ctx.json({ accessToken: email }));
    }

    return res(ctx.status(400, 'unauthorized'), ctx.json({ message: 'login failed' }));
  }),

  rest.get<any>(`${BASE_URL}/members/me/histories`, (req, res, ctx) => {
    const { headers } = req;

    try {
      const { histories } = users.findLoggedUser(headers.get('authorization'));

      return res(ctx.status(200, 'authorized'), ctx.json({ data: histories }));
    } catch ({ message }) {
      return res(ctx.status(400, 'unauthorized'), ctx.json({ message }));
    }
  }),

  rest.put(`${BASE_URL}/members/me/histories`, (req, res, ctx) => {
    const { headers } = req;

    try {
      const user = users.findLoggedUser(headers.get('authorization'));

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

    try {
      const user = users.findLoggedUser(headers.get('authorization'));

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
