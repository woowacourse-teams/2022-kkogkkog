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

// 역할이 없는 비지니스 로직 훅
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

// displayMessage가 모든 EditMe 요청에 발생한다면 Mutation단으로 올리고, 아니라면 컴포넌트 단에서 처리.
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

// 역할이 없는 비지니스 로직 훅
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

// 역할이 없는 비지니스 로직 훅
export const useAddSlackApp = () => {
  const addSlackAppMutate = useAddSlackAppMutation();

  const addSlackApp = (code: string) => {
    return addSlackAppMutate.mutateAsync({ code });
  };

  return {
    addSlackApp,
  };
};

// mutate 를 이용해 새로운 비즈니스 로직을 정의. 단순히 가공만 하지 않기 때문에, 의미가 있는 훅이라고 생각합니다.
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
