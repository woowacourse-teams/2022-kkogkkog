import { useMutation, useQueryClient } from 'react-query';

import { useLoading } from '@/@hooks/@common/useLoading';
import { client } from '@/apis';
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
import { useTokenMutation } from '../@common/useTokenMutation';
import { signUpToken } from './../../apis/user';
import { useQuery } from './utils';

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
    /** get method 중, 에러 경계를 사용하고 싶지 않은 경우 onError를 추가해준다. */
    onError() {},
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

export const useFetchUserHistoryList = () => {
  const { data, ...rest } = useQuery([QUERY_KEY.getUserHistoryList], getUserHistoryList, {
    suspense: false,
    staleTime: 10000,
  });

  return {
    historyList: data?.data,
    ...rest,
  };
};

/** Mutation */
export const useEditMeMutation = () => {
  const queryClient = useQueryClient();

  const { showLoading, hideLoading } = useLoading();

  return useTokenMutation(editMe, {
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
    useErrorBoundary: false,
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

export const useSignupMutation = () => {
  return useMutation(signUpToken, {
    onSuccess(response) {
      const { accessToken } = response.data;

      localStorage.removeItem('slack-signup-token');

      localStorage.setItem('user-token', accessToken);

      client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
    },
  });
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
