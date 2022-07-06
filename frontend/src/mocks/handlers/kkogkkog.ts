import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import kkogkkogList from '@/mocks/fixtures/kkogkkogList';

export const kkogkkogHandler = [
  rest.get(`${BASE_URL}/coupons`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(kkogkkogList.current));
  }),
];
