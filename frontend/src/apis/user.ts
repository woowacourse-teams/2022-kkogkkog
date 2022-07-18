import { client } from '@/apis';
import { JoinRequest, LoginRequest } from '@/types/remote/request';
import { MeResponse, UserListResponse } from '@/types/remote/response';

export const getMe = () =>
  client.get<MeResponse>('/members/me', {
    transformResponse: stringResponse => {
      const parsedData = JSON.parse(stringResponse);

      if (parsedData.error) {
        return parsedData;
      }

      return parsedData.data;
    },
  });

export const getUserList = () =>
  client.get<UserListResponse>('/members', {
    transformResponse: stringResponse => {
      const parsedData = JSON.parse(stringResponse);

      if (parsedData.error) {
        return parsedData;
      }

      return parsedData.data;
    },
  });

export const join = (args: JoinRequest) => client.post('/members', args);

export const login = (args: LoginRequest) => client.post('/login', args);
