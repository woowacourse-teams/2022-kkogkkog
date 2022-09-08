import { AxiosError } from 'axios';
import { useNavigate } from 'react-router-dom';

import { useToast } from '@/@hooks/@common/useToast';
import { useAddSlackAppMutation } from '@/@hooks/@queries/service';
import { useSlackOAuthLoginMutation } from '@/@hooks/@queries/user';
import { PATH } from '@/Router';

export const useSlackOAuthLogin = () => {
  const navigate = useNavigate();

  const { displayMessage } = useToast();

  const loginMutate = useSlackOAuthLoginMutation();

  const loginBySlackOAuth = (slackOAuthCode: string) => {
    loginMutate.mutate(slackOAuthCode, {
      onSuccess(response) {
        const { isNew, accessToken } = response.data;

        if (isNew) {
          localStorage.setItem('slack-signup-token', accessToken);

          navigate(PATH.JOIN);
        } else {
          localStorage.setItem('user-token', accessToken);

          navigate(PATH.LANDING, { replace: true });

          displayMessage('로그인에 성공하였습니다.', false);
        }
      },
      onError(error) {
        if (error instanceof AxiosError) {
          displayMessage(error?.response?.data?.message, true);

          navigate(PATH.LANDING, { replace: true });
        }
      },
    });
  };

  return {
    loginBySlackOAuth,
  };
};

export const useAddSlackApp = () => {
  const navigate = useNavigate();

  const addSlackAppMutate = useAddSlackAppMutation();

  const addSlackApp = (code: string) => {
    addSlackAppMutate.mutate(code, {
      onSuccess() {
        navigate(PATH.LANDING, { replace: true });
      },
    });
  };

  return {
    addSlackApp,
  };
};
