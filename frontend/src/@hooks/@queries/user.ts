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
  readHistory,
} from '@/apis/user';
import { ReadHistoryRequest } from '@/types/remote/request';
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
  });

  return {
    historyList: data?.data,
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

export const useReadHistoryMutation = () => {
  const queryClient = useQueryClient();

  const onMutate = async ({ id }: ReadHistoryRequest) => {
    await queryClient.cancelQueries([QUERY_KEY.getUserHistoryList]);

    const previousQueryData = queryClient.getQueryData<UserHistoryResponse>([
      QUERY_KEY.getUserHistoryList,
    ]);

    // Typing 어려워.. 2
    queryClient.setQueryData<UserHistoryResponse | undefined>(
      [QUERY_KEY.getUserHistoryList],
      oldData => {
        if (!oldData) {
          return oldData;
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

    return previousQueryData;
  };

  const onSuccess = () => {
    queryClient.invalidateQueries([QUERY_KEY.me]);
  };

  const onError = (
    error: unknown,
    variables: ReadHistoryRequest,
    context: UserHistoryResponse | undefined
  ) => {
    queryClient.setQueryData([QUERY_KEY.getUserHistoryList], context);
  };

  return useMutation(readHistory, {
    onMutate,
    onSuccess,
    onError,
  });
};
