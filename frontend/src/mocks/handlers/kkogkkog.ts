import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import kkogkkogs from '@/mocks/fixtures/kkogkkogs';
import users from '@/mocks/fixtures/users';
import { ChangeKkogKkogStatusRequest, CreateKkogKkogRequest } from '@/types/remote/request';

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

    try {
      const loggedUser = users.findLoggedUser(headers.get('authorization'));

      const newKkogKkogList = receivers.map(receiverId => {
        const receiver = users.findUser(receiverId);

        const newKkogkkog = {
          id: kkogkkogs.current.length + 1,
          sender: loggedUser,
          receiver,
          couponStatus: 'READY',
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

  rest.post<ChangeKkogKkogStatusRequest>(`${BASE_URL}/coupons/:couponId/event`, (req, res, ctx) => {
    const {
      body: { couponEvent },
      params: { couponId },
    } = req;

    const newKkogKkogList = kkogkkogs.current.map(kkogkkog =>
      kkogkkog.id === Number(couponId)
        ? { ...kkogkkog, couponStatus: kkogkkogs.onEvent(couponEvent) }
        : kkogkkog
    );

    const a = newKkogKkogList.map(kkogkkog => kkogkkog.couponStatus);

    console.log('a', a);

    kkogkkogs.current = newKkogKkogList;

    return res(ctx.status(200), ctx.json({ data: newKkogKkogList }));
  }),
];
