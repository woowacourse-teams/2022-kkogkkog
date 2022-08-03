import { useMutation, useQuery, useQueryClient } from 'react-query';

import { client } from '@/apis';
import { editMe, getHistoryList, getMe, getUserList, join, login, OAuthLogin } from '@/apis/user';

import { useToast } from '../@common/useToast';

const QUERY_KEY = {
  me: 'me',
  getUserList: 'getUserList',
  getHistoryList: 'getHistoryList',
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

export const useFetchHistoryList = () => {
  const { data, ...rest } = useQuery([QUERY_KEY.getHistoryList], getHistoryList, {
    suspense: false,
  });

  return {
    historyList: data?.data?.data,
    ...rest,
  };
};

/** Mutation */
export const useEditMeMutation = () => {
  const queryClient = useQueryClient();

  return useMutation(editMe, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.me);
    },
  });
};

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
