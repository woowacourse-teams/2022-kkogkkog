import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import kkogkkogs from '@/mocks/fixtures/kkogkkogs';
import users from '@/mocks/fixtures/users';
import { CreateKkogKkogRequest } from '@/types/remote/request';

export const kkogkkogHandler = [
  rest.get(`${BASE_URL}/coupons`, (req, res, ctx) => {
    const { headers } = req;

    try {
      const loggedUser = users.findLoggedUser(headers.get('authorization'));

      const receivedKkogKkogList = kkogkkogs.findReceivedKkogKkogList(loggedUser);

      const sentKkogKkogList = kkogkkogs.findSentKkogKkogList(loggedUser);

      return res(
        ctx.status(200),
        ctx.json({ data: { received: receivedKkogKkogList, sent: sentKkogKkogList } })
      );
    } catch ({ message }) {
      return res(ctx.status(400), ctx.json({ error: message }));
    }
  }),

  rest.get(`${BASE_URL}/coupons/:id`, (req, res, ctx) => {
    const {
      params: { id },
    } = req;

    try {
      const kkogkkog = kkogkkogs.findKkogKkog(id);

      return res(ctx.status(200), ctx.json(kkogkkog));
    } catch ({ message }) {
      return res(ctx.status(400), ctx.json({ error: message }));
    }
  }),

  rest.post<CreateKkogKkogRequest>(`${BASE_URL}/coupons`, (req, res, ctx) => {
    const {
      body: { receivers, ...body },
      headers,
    } = req;

    const loggedUser = users.findLoggedUser(headers.get('authorization'));

    try {
      const newKkogKkogList = receivers.map(receiverId => {
        const receiver = users.findUser(receiverId);

        const newKkogkkog = {
          id: kkogkkogs.current.length + 1,
          sender: loggedUser,
          receiver,
          ...body,
        };

        return newKkogkkog;
      });

      kkogkkogs.current = [...kkogkkogs.current, ...newKkogKkogList];

      return res(ctx.status(200), ctx.json({ data: newKkogKkogList }));
    } catch ({ message }) {
      return res(ctx.status(400), ctx.json({ error: message }));
    }
  }),
];
