import { setupServer } from 'msw/node';
import { QueryCache } from 'react-query';

import { client } from '@/apis';
import users from '@/mocks/fixtures/users';
import { handlers } from '@/mocks/handlers';

const queryCache = new QueryCache();

const worker = setupServer(...handlers);

export const testUser = users.current[0]; // 준찌 mock

beforeAll(() => {
  client.defaults.headers['Authorization'] = `Bearer ${testUser.email}`;
  localStorage.setItem('user-token', testUser.email);

  worker.listen();
});

afterEach(() => {
  queryCache.clear();
  worker.resetHandlers();
});

afterAll(() => {
  worker.close();
});
