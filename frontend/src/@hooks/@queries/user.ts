import { useMemo } from 'react';
import { useQueryClient } from 'react-query';

import { useLoading } from '@/@hooks/@common/useLoading';
import { client } from '@/apis';
import {
  editMe,
  getMe,
  getUserHistoryList,
  getUserList,
  oAuthLogin,
  oAuthSignup,
  readAllHistory,
  slackAppDownload,
} from '@/apis/user';
import { OAuthType } from '@/types/user/client';
import { UserHistoryListResponse } from '@/types/user/remote';

import { useToast } from '../@common/useToast';
import { useMutation, useQuery } from './utils';

const QUERY_KEY = {
  me: 'me',
  userList: 'userList',
  userHistoryList: 'userHistoryList',
};

/** Query */
export const useFetchMe = () => {
  const { data, isLoading, remove, isError } = useQuery([QUERY_KEY.me], getMe, {
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
    me: isError ? undefined : data,
    isLoading,
    logout,
  };
};

export const useFetchUserList = () => {
  const { data } = useQuery([QUERY_KEY.userList], getUserList, {
    suspense: false,
  });

  return {
    userList: data?.data,
  };
};

export const useFetchUserHistoryList = () => {
  const { data, refetch } = useQuery([QUERY_KEY.userHistoryList], getUserHistoryList, {
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

export const useOAuthLoginMutation = (oAuthType: OAuthType) => {
  const { displayMessage } = useToast();

  const { showLoading, hideLoading } = useLoading();

  return useMutation(oAuthLogin(oAuthType), {
    onSuccess(response) {
      const { isNew, accessToken } = response.data;

      if (isNew) {
        localStorage.setItem('signup-token', accessToken);
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

export const useOAuthSignupMutation = (oAuthType: OAuthType) => {
  return useMutation(oAuthSignup(oAuthType), {
    onSuccess(response) {
      const { accessToken } = response.data;

      localStorage.removeItem('signup-token');

      localStorage.setItem('user-token', accessToken);

      client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
    },
  });
};

export const useAddSlackAppMutation = () => {
  return useMutation(slackAppDownload);
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
    queryClient.setQueryData<UserHistoryListResponse>([QUERY_KEY.userHistoryList], oldData => {
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
    });
  };

  return { readHistory };
};
