import { useToast } from '@/@hooks/@common/useToast';
import {
  useAddSlackAppMutation,
  useEditMeMutation,
  useFetchUserHistoryList,
  useLoginMutation,
  useReadAllHistoryMutation,
  useSlackOAuthLoginMutation,
  useSlackSignupMutation,
} from '@/@hooks/@queries/user';

export const useSlackSignUp = () => {
  const slackSignupMutate = useSlackSignupMutation();

  const slackSignup = ({ name, slackSignupToken }: { name: string; slackSignupToken: string }) => {
    return slackSignupMutate.mutateAsync({
      nickname: name,
      accessToken: slackSignupToken,
    });
  };

  return {
    slackSignup,
  };
};

export const useLogin = () => {
  const loginMutate = useLoginMutation();

  const login = ({ email, password }: { email: string; password: string }) => {
    return loginMutate.mutateAsync({
      email,
      password,
    });
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
  const loginMutate = useSlackOAuthLoginMutation();

  const loginBySlackOAuth = async (slackOAuthCode: string) => {
    const response = await loginMutate.mutateAsync(slackOAuthCode);

    return response?.data;
  };

  return {
    loginBySlackOAuth,
  };
};

export const useAddSlackApp = () => {
  const addSlackAppMutate = useAddSlackAppMutation();

  const addSlackApp = (code: string) => {
    return addSlackAppMutate.mutateAsync(code);
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
