import { client } from '@/apis';
import { User } from '@/types/domain';

export const getMe = () =>
  client.get<User>('/members/me', {
    transformResponse: stringResponse => {
      const parsedData = JSON.parse(stringResponse);

      if (parsedData.error) {
        return parsedData;
      }

      return parsedData.data;
    },
  });

export const getUserList = () =>
  client.get('/members', {
    transformResponse: stringResponse => {
      const parsedData = JSON.parse(stringResponse);

      if (parsedData.error) {
        return parsedData;
      }

      return parsedData.data;
    },
  });

export const join = (args: { nickname: string; email: string; password: string }) =>
  client.post('/members', args);

export const login = (args: { email: string; password: string }) => client.post('/login', args);
