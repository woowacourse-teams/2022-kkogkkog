import { AxiosResponse } from 'axios';
import { useNavigate } from 'react-router-dom';

import { useToast } from '@/@hooks/@common/useToast';
import {
  useAddSlackAppMutation,
  useEditMeMutation,
  useFetchUserHistoryList,
  useOAuthLoginMutation,
  useOAuthSignupMutation,
  useReadAllHistoryMutation,
} from '@/@hooks/@queries/user';
import { PATH } from '@/Router';
import { OAuthType } from '@/types/user/client';
import { SignupRequest, SignupResponse, SlackSignupRequest } from '@/types/user/remote';

type SignupFunc = (body: SignupRequest) => Promise<AxiosResponse<SignupResponse>>;

export function useOAuthSignup(oAuthType: 'slack'): { slackSignup: SignupFunc };
export function useOAuthSignup(oAuthType: 'google'): { googleSignup: SignupFunc };
export function useOAuthSignup(oAuthType: OAuthType) {
  const slackSignupMutate = useOAuthSignupMutation(oAuthType);

  const signupByOAuth = ({ nickname, accessToken }: SlackSignupRequest) => {
    return slackSignupMutate.mutateAsync({
      nickname,
      accessToken,
    });
  };

  return {
    [`${oAuthType}Signup`]: signupByOAuth,
  };
}

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

type LoginRedirectFunc = (code: string) => Promise<void>;

export function useOAuthLogin(oAuthType: 'slack'): { slackLoginRedirect: LoginRedirectFunc };
export function useOAuthLogin(oAuthType: 'google'): { googleLoginRedirect: LoginRedirectFunc };
export function useOAuthLogin(oAuthType: OAuthType) {
  const loginMutate = useOAuthLoginMutation(oAuthType);
  const navigate = useNavigate();

  const loginByOAuth = async (code: string) => {
    const response = await loginMutate.mutateAsync({ code });

    return response?.data;
  };

  const loginRedirect = async (code: string) => {
    try {
      const response = await loginByOAuth(code);

      if (response.isNew) {
        navigate(PATH.SIGNUP, { state: oAuthType });
      } else {
        navigate(PATH.MAIN, { replace: true });
      }
    } catch (error) {
      navigate(PATH.MAIN, { replace: true });

      throw error;
    }
  };

  return {
    [`${oAuthType}LoginRedirect`]: loginRedirect,
  };
}

export const useAddSlackApp = () => {
  const addSlackAppMutate = useAddSlackAppMutation();

  const addSlackApp = (code: string) => {
    return addSlackAppMutate.mutateAsync({ code });
  };

  return {
    addSlackApp,
  };
};

export const useReadAllHistory = () => {
  const { checkIsReadAll } = useFetchUserHistoryList();

  const readAllHistoryMutation = useReadAllHistoryMutation();

  const readAllHistory = () => {
    const isReadAll = checkIsReadAll();

    if (isReadAll) {
      return;
    }

    return readAllHistoryMutation.mutateAsync();
  };

  return {
    readAllHistory,
  };
};
