import { useMutation, useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { client } from '@/apis';
import { getMe, getUserList, join, login, OAuthLogin } from '@/apis/user';
import { PATH } from '@/Router';

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
  const navigate = useNavigate();

  return useMutation(OAuthLogin, {
    onSuccess(response) {
      const { accessToken } = response.data;

      localStorage.setItem('user-token', accessToken);

      client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;

      navigate(PATH.LANDING);
    },
  });
};

export const useJoinMutation = () => {
  const { displayMessage } = useToast();

  const navigate = useNavigate();

  return useMutation(join, {
    onSuccess: () => {
      navigate(PATH.LOGIN);
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

export const useLoginMutation = () => {
  const navigate = useNavigate();

  const { displayMessage } = useToast();

  return useMutation(login, {
    onSuccess: data => {
      const {
        data: { accessToken },
      } = data;

      localStorage.setItem('user-token', accessToken);

      client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;

      navigate(PATH.LANDING);
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
