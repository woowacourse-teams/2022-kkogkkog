import { AxiosResponse } from 'axios';

import { useToast } from '@/@hooks/@common/useToast';
import {
  useAddSlackAppMutation,
  useEditMeMutation,
  useFetchUserHistoryList,
  useOAuthLoginMutation,
  useOAuthSignupMutation,
  useReadAllHistoryMutation,
} from '@/@hooks/@queries/user';
import {
  LoginResponse,
  SignupRequest,
  SignupResponse,
  SlackSignupRequest,
} from '@/types/user/remote';

type SignupFunc = (body: SignupRequest) => Promise<AxiosResponse<SignupResponse>>;

export function useOAuthSignup(oAuthType: 'slack'): { slackSignup: SignupFunc };
export function useOAuthSignup(oAuthType: 'google'): { googleSignup: SignupFunc };
export function useOAuthSignup(oAuthType: 'slack' | 'google') {
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

type LoginFunc = (code: string) => Promise<LoginResponse>;

export function useOAuthLogin(oAuthType: 'slack'): { slackLogin: LoginFunc };
export function useOAuthLogin(oAuthType: 'google'): { googleLogin: LoginFunc };
export function useOAuthLogin(oAuthType: 'slack' | 'google') {
  const loginMutate = useOAuthLoginMutation(oAuthType);

  const loginByOAuth = async (code: string) => {
    const response = await loginMutate.mutateAsync({ code });

    return response?.data;
  };

  return {
    [`${oAuthType}Login`]: loginByOAuth,
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
