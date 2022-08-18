import { AxiosError } from 'axios';
import { useMutation, useQuery, useQueryClient } from 'react-query';

import { useLoading } from '@/@hooks/@common/useLoading';
import { useTokenQuery } from '@/@hooks/@common/useTokenQuery';
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
import { useTokenMutation } from '../@common/useTokenMutation';

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
    staleTime: 10000,
  });

  return {
    me: data?.data,
    ...rest,
  };
};

export const useFetchUserList = () => {
  const { displayMessage } = useToast();
  const { data, ...rest } = useTokenQuery([QUERY_KEY.getUserList], getUserList, {
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
  const { data, ...rest } = useTokenQuery([QUERY_KEY.getUserHistoryList], getUserHistoryList, {
    suspense: false,
    staleTime: 10000,
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
  const queryClient = useQueryClient();

  const { displayMessage } = useToast();
  const { showLoading, hideLoading } = useLoading();

  return useTokenMutation(editMe, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.me);
    },
    onError(error) {
      if (error instanceof AxiosError) {
        displayMessage(error?.response?.data?.message, true);
      }
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });
};

export const useOAuthLoginMutation = () => {
  const { displayMessage } = useToast();
  const { showLoading, hideLoading } = useLoading();

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
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
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

  const { showLoading, hideLoading } = useLoading();

  return useTokenMutation(readAllHistory, {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.me]);
      // QUERY_KEY.getUserHistoryList 은 재요청하지 않기위해 구식으로 만들지 않는다.
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
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
