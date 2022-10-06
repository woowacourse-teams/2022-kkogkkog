import { useToast } from '@/@hooks/@common/useToast';
import {
  useAddSlackAppMutation,
  useEditMeMutation,
  useFetchUserHistoryList,
  useOAuthLoginMutation,
  useOAuthSignupMutation,
  useReadAllHistoryMutation,
} from '@/@hooks/@queries/user';
import { OAuthType } from '@/types/user/client';
import { SignupRequest } from '@/types/user/remote';

export function useOAuthSignup(oAuthType: OAuthType) {
  const slackSignupMutate = useOAuthSignupMutation(oAuthType);

  const signupByOAuth = ({ nickname, accessToken }: SignupRequest) => {
    return slackSignupMutate.mutateAsync({
      nickname,
      accessToken,
    });
  };

  return {
    signupByOAuth,
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

export function useOAuthLogin(oAuthType: OAuthType) {
  const loginMutate = useOAuthLoginMutation(oAuthType);

  const loginByOAuth = async (code: string) => {
    const response = await loginMutate.mutateAsync({ code });

    return response?.data;
  };

  return {
    loginByOAuth,
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
