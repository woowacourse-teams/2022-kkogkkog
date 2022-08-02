import { useMutation, useQuery } from 'react-query';

import { client } from '@/apis';
import { getMe, getUserList, join, login, OAuthLogin } from '@/apis/user';

import { useToast } from '../@common/useToast';

const QUERY_KEY = {
  me: 'me',
  getUserList: 'getUserList',
};

/** Query */

export const useFetchMe = () => {
  const { data, ...rest } = useQuery([QUERY_KEY.me], getMe, {
    suspense: false,
    refetchOnWindowFocus: false,
  });

  return {
    me: data?.data,
    ...rest,
  };
};

export const useFetchUserList = () => {
  const { data, ...rest } = useQuery([QUERY_KEY.getUserList], getUserList, {
    suspense: false,
  });

  return {
    userList: data?.data?.data,
    ...rest,
  };
};

/** Mutation */

export const useOAuthLoginMutation = () => {
  return useMutation(OAuthLogin, {
    onSuccess(response) {
      const { accessToken } = response.data;

      localStorage.setItem('user-token', accessToken);

      client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
    },
  });
};

export const useJoinMutation = () => {
  const { displayMessage } = useToast();

  return useMutation(join, {
    onSuccess: () => {},
    onError({
      response: {
        data: { error },
      },
    }) {
      displayMessage(error, true);
    },
  });
};

export const useLoginMutation = () => {
  const { displayMessage } = useToast();

  return useMutation(login, {
    onSuccess: data => {
      const {
        data: { accessToken },
      } = data;

      localStorage.setItem('user-token', accessToken);

      client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
    },
    onError({
      response: {
        data: { error },
      },
    }) {
      displayMessage(error, true);
    },
  });
};
