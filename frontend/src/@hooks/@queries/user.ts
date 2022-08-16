import { AxiosError } from 'axios';
import { useMutation, useQuery, useQueryClient } from 'react-query';

import { client } from '@/apis';
import {
  editMe,
  getMe,
  getUserHistoryList,
  getUserList,
  join,
  login,
  OAuthLogin,
  readAllHistory,
} from '@/apis/user';
import { UserHistoryResponse } from '@/types/remote/response';

import { useToast } from '../@common/useToast';

const QUERY_KEY = {
  me: 'me',
  getUserList: 'getUserList',
  getUserHistoryList: 'getUserHistoryList',
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
  const { displayMessage } = useToast();
  const { data, ...rest } = useQuery([QUERY_KEY.getUserList], getUserList, {
    suspense: false,
    onError(error) {
      if (error instanceof AxiosError) {
        displayMessage(error?.response?.data?.message, true);
      }
    },
  });

  return {
    userList: data?.data?.data,
    ...rest,
  };
};

export const useFetchUserHistoryList = () => {
  const { displayMessage } = useToast();
  const { data, ...rest } = useQuery([QUERY_KEY.getUserHistoryList], getUserHistoryList, {
    suspense: false,
    onError(error) {
      if (error instanceof AxiosError) {
        displayMessage(error?.response?.data?.message, true);
      }
    },
  });

  return {
    historyList: data?.data,
    ...rest,
  };
};

/** Mutation */
export const useEditMeMutation = () => {
  const { displayMessage } = useToast();
  const queryClient = useQueryClient();

  return useMutation(editMe, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.me);
    },
    onError(error) {
      if (error instanceof AxiosError) {
        displayMessage(error?.response?.data?.message, true);
      }
    },
  });
};

export const useOAuthLoginMutation = () => {
  const { displayMessage } = useToast();

  return useMutation(OAuthLogin, {
    onSuccess(response) {
      const { accessToken } = response.data;

      localStorage.setItem('user-token', accessToken);

      client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
    },
    onError(error) {
      if (error instanceof AxiosError) {
        displayMessage(error?.response?.data?.message, true);
      }
    },
  });
};

export const useJoinMutation = () => {
  const { displayMessage } = useToast();

  return useMutation(join, {
    onSuccess: () => {},
    onError(error) {
      if (error instanceof AxiosError) {
        displayMessage(error?.response?.data?.message, true);
      }
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
    onError(error) {
      if (error instanceof AxiosError) {
        displayMessage(error?.response?.data?.message, true);
      }
    },
  });
};

export const useReadAllHistoryMutation = () => {
  const queryClient = useQueryClient();

  return useMutation(readAllHistory, {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.me]);
    },
  });
};

export const useReadHistory = () => {
  const queryClient = useQueryClient();

  const readHistory = (id: number) => {
    queryClient.setQueryData<UserHistoryResponse | undefined>(
      [QUERY_KEY.getUserHistoryList],
      oldData => {
        if (oldData === undefined) {
          return;
        }

        const newData = {
          ...oldData,
          data: oldData?.data?.map(history =>
            history.id === id ? { ...history, isRead: true } : { ...history }
          ),
        };

        return newData;
      }
    );
  };

  return readHistory;
};
