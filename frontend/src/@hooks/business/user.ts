import { AxiosError } from 'axios';

import { useToast } from '@/@hooks/@common/useToast';
import {
  useAddSlackAppMutation,
  useEditMeMutation,
  useLoginMutation,
  useReadAllHistoryMutation,
  useSlackOAuthLoginMutation,
  useSlackSignupMutation,
} from '@/@hooks/@queries/user';

export const useSlackSignUp = () => {
  const { displayMessage } = useToast();

  const slackSignupMutate = useSlackSignupMutation();

  const slackSignup = ({ name, slackSignupToken }: { name: string; slackSignupToken: string }) => {
    return slackSignupMutate.mutateAsync(
      {
        nickname: name,
        accessToken: slackSignupToken,
      },
      {
        onSuccess() {},
        onError(error) {
          if (error instanceof AxiosError) {
            displayMessage(error?.response?.data?.message, true);
          }
        },
      }
    );
  };

  return {
    slackSignup,
  };
};

export const useLogin = () => {
  const loginMutate = useLoginMutation();

  const login = ({ email, password }: { email: string; password: string }) => {
    return loginMutate.mutateAsync(
      {
        email,
        password,
      },
      {
        onSuccess() {},
      }
    );
  };

  return {
    login,
  };
};

export const useEditMe = () => {
  const { displayMessage } = useToast();

  const editMeMutation = useEditMeMutation();

  const editMe = ({ nickname }: { nickname: string }) => {
    return editMeMutation.mutateAsync(
      { nickname },
      {
        onSuccess() {
          displayMessage('닉네임이 변경되었습니다', false);
        },
      }
    );
  };

  return { editMe };
};

export const useSlackOAuthLogin = () => {
  const { displayMessage } = useToast();

  const loginMutate = useSlackOAuthLoginMutation();

  const loginBySlackOAuth = async (slackOAuthCode: string) => {
    const response = await loginMutate.mutateAsync(slackOAuthCode, {
      onError(error) {
        if (error instanceof AxiosError) {
          displayMessage(error?.response?.data?.message, true);
        }
      },
    });

    return response?.data;
  };

  return {
    loginBySlackOAuth,
  };
};

export const useAddSlackApp = () => {
  const { displayMessage } = useToast();

  const addSlackAppMutate = useAddSlackAppMutation();

  const addSlackApp = (code: string) => {
    return addSlackAppMutate.mutateAsync(code, {
      onError(error) {
        if (error instanceof AxiosError) {
          displayMessage(error?.response?.data?.message, true);
        }
      },
    });
  };

  return {
    addSlackApp,
  };
};

export const useReadAllHistory = () => {
  const readAllHistoryMutation = useReadAllHistoryMutation();

  const readAllHistory = () => {
    return readAllHistoryMutation.mutateAsync();
  };

  return {
    readAllHistory,
  };
};
