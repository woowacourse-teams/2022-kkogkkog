import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import users from '@/mocks/fixtures/users';

export const userHandler = [
  rest.post<any>(`${BASE_URL}/members`, (req, res, ctx) => {
    const { body: info } = req;

    const user = { id: users.current.data.length + 1, ...info };

    users.current.data.push(user);

    return res(ctx.status(201));
  }),

  rest.post<any>(`${BASE_URL}/login`, (req, res, ctx) => {
    const { password, email } = req.body;

    if (
      users.current.data.some(
        customer => customer.email === email && customer.password === password
      )
    ) {
      return res(ctx.status(200, 'ok'), ctx.json({ accessToken: email }));
    }

    return res(
      ctx.status(400, 'unauthorized'),
      ctx.json({ error: { messages: ['login failed'] } })
    );
  }),

  rest.get<any>(`${BASE_URL}/members/me`, (req, res, ctx) => {
    const { headers } = req;

    const user = users.current.data.find(
      ({ email: userToken }) => headers.headers.authorization === `Bearer ${userToken}`
    );

    if (user) {
      return res(ctx.status(200, 'authorized'), ctx.json({ data: user }));
    }

    return res(
      ctx.status(400, 'unauthorized'),
      ctx.json({ error: { messages: ['login failed'] } })
    );
  }),
];
