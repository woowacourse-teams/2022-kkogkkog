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
        const { isNew } = response.data;

        if (isNew) {
          navigate(PATH.SIGNUP);
        } else {
          navigate(PATH.LANDING, { replace: true });
        }
      },
      onError() {
        navigate(PATH.LANDING, { replace: true });
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
