import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import users from '@/mocks/fixtures/users';

export const userHandler = [
  rest.get<any>(`${BASE_URL}/members/me`, (req, res, ctx) => {
    const { headers } = req;

    try {
      const user = users.findLoggedUser(headers.get('authorization'));

      return res(ctx.status(200, 'authorized'), ctx.json(user));
    } catch ({ message }) {
      return res(ctx.status(400, 'unauthorized'), ctx.json({ error: message }));
    }
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

    return res(ctx.status(400, 'unauthorized'), ctx.json({ error: 'login failed' }));
  }),
];
