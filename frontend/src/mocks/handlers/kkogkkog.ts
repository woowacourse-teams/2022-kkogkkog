import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import kkogkkogs from '@/mocks/fixtures/kkogkkogs';
import users from '@/mocks/fixtures/users';

export const kkogkkogHandler = [
  rest.get(`${BASE_URL}/coupons`, (req, res, ctx) => {
    const { headers } = req;

    const loggedUser = users.findLoggedUser(headers);

    const receivedKkogKkogList = kkogkkogs.findReceivedKkogKkogList(loggedUser);

    const sentKkogKkogList = kkogkkogs.findSentKkogKkogList(loggedUser);

    return res(
      ctx.status(200),
      ctx.json({ data: { received: receivedKkogKkogList, sent: sentKkogKkogList } })
    );
  }),

  rest.get(`${BASE_URL}/coupons/:id`, (req, res, ctx) => {
    const {
      params: { id },
    } = req;

    try {
      const kkogkkog = kkogkkogs.findKkogKkog(id);

      return res(ctx.status(200), ctx.json(kkogkkog));
    } catch ({ message }) {
      return res(ctx.status(400), ctx.json(message));
    }
  }),

  rest.post<any>(`${BASE_URL}/coupons`, (req, res, ctx) => {
    const { body, headers } = req;

    const loggedUser = users.findLoggedUser(headers);

    const receivers = body.receivers.map(receiverId => users.findUserById(receiverId));

    const newKkogKkogList = receivers.map(receiver => {
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
  }),
];
