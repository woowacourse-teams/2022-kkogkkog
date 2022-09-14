import { useMemo } from 'react';
import { useQueryClient } from 'react-query';

import { useLoading } from '@/@hooks/@common/useLoading';
import { client } from '@/apis';
import { AddSlackApp } from '@/apis/service';
import {
  editMe,
  getMe,
  getUserHistoryList,
  getUserList,
  login,
  OAuthLogin,
  readAllHistory,
} from '@/apis/user';
import { UserHistoryResponse } from '@/types/remote/response';

import { useToast } from '../@common/useToast';
import { signUpToken } from './../../apis/user';
import { useMutation, useQuery } from './utils';

const QUERY_KEY = {
  me: 'me',
  getUserList: 'getUserList',
  getUserHistoryList: 'getUserHistoryList',
};

/** Query */
export const useFetchMe = () => {
  const { data, isLoading, remove } = useQuery([QUERY_KEY.me], getMe, {
    suspense: false,
    refetchOnWindowFocus: false,
    staleTime: 10000,
    useErrorBoundary: false,
  });

  const logout = () => {
    client.defaults.headers['Authorization'] = '';

    localStorage.removeItem('user-token');

    remove();
  };

  return {
    me: data?.data,
    isLoading,
    logout,
  };
};

export const useFetchUserList = () => {
  const { data } = useQuery([QUERY_KEY.getUserList], getUserList, {
    suspense: false,
  });

  return {
    userList: data?.data?.data,
  };
};

export const useFetchUserHistoryList = () => {
  const { data, refetch } = useQuery([QUERY_KEY.getUserHistoryList], getUserHistoryList, {
    suspense: false,
    staleTime: Infinity,
  });

  const historyList = useMemo(() => data?.data ?? [], [data?.data]);

  const checkIsReadAll = () => {
    return historyList.every(history => history.isRead);
  };

  const synchronizeServerUserHistory = () => {
    refetch();
  };

  return {
    historyList: data?.data ?? [],
    synchronizeServerUserHistory,
    checkIsReadAll,
  };
};

/** Mutation */
export const useEditMeMutation = () => {
  const queryClient = useQueryClient();

  const { showLoading, hideLoading } = useLoading();

  return useMutation(editMe, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.me);
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });
};

export const useSlackOAuthLoginMutation = () => {
  const { displayMessage } = useToast();

  const { showLoading, hideLoading } = useLoading();

  return useMutation(OAuthLogin, {
    onSuccess(response) {
      const { isNew, accessToken } = response.data;

      if (isNew) {
        localStorage.setItem('slack-signup-token', accessToken);
      } else {
        localStorage.setItem('user-token', accessToken);

        client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;

        displayMessage('로그인에 성공하였습니다.', false);
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

export const useSlackSignupMutation = () => {
  return useMutation(signUpToken, {
    onSuccess(response) {
      const { accessToken } = response.data;

      localStorage.removeItem('slack-signup-token');

      localStorage.setItem('user-token', accessToken);

      client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
    },
  });
};

export const useAddSlackAppMutation = () => {
  return useMutation(AddSlackApp);
};

export const useLoginMutation = () => {
  return useMutation(login, {
    onSuccess: data => {
      const {
        data: { accessToken },
      } = data;

      localStorage.setItem('user-token', accessToken);

      client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
    },
  });
};

export const useReadAllHistoryMutation = () => {
  const queryClient = useQueryClient();

  const { showLoading, hideLoading } = useLoading();

  return useMutation(readAllHistory, {
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

/** Only Mutate Client Query */

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

  return { readHistory };
};
