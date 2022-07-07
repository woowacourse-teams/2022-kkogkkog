import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import kkogkkogList from '@/mocks/fixtures/kkogkkogList';

export const kkogkkogHandler = [
  rest.get(`${BASE_URL}/coupons`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(kkogkkogList.current));
  }),

  rest.post<any>(`${BASE_URL}/coupons`, (req, res, ctx) => {
    const { body: info } = req;

    const newKkogkkog = {
      id: kkogkkogList.current.data.length + 1,
      ...info,
    };

    kkogkkogList.current.data = [newKkogkkog, ...kkogkkogList.current.data];

    return res(ctx.status(200), ctx.json(newKkogkkog));
  }),
];
