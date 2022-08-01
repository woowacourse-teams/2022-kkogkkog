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

export const useMe = () => {
  return useQuery([QUERY_KEY.me], getMe, {
    suspense: false,
    refetchOnWindowFocus: false,
    select(data) {
      return data.data;
    },
  });
};

export const useUserList = () => {
  return useQuery([QUERY_KEY.getUserList], getUserList, {
    suspense: false,
    select(data) {
      return data.data;
    },
  });
};

export const useOAuthLoginMutation = () => {
  return useMutation(OAuthLogin);
};

export const useJoinMutation = () => {
  const navigate = useNavigate();

  return useMutation(join, {
    onSuccess: () => {
      navigate(PATH.LOGIN);
    },
    onError() {
      alert('회원가입에 실패했습니다.');
    },
  });
};

export const useLoginMutation = () => {
  const navigate = useNavigate();
  const { remove } = useMe();
  const { displayMessage } = useToast();

  return useMutation(login, {
    onSuccess: data => {
      const {
        data: { accessToken },
      } = data;

      localStorage.setItem('user-token', accessToken);

      client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;

      remove();

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
