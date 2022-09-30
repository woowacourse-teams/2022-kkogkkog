import { useToast } from '@/@hooks/@common/useToast';
import {
  useAddSlackAppMutation,
  useEditMeMutation,
  useFetchUserHistoryList,
  useGoogleLoginMutation,
  useGoogleSignupMutation,
  useReadAllHistoryMutation,
  useSlackLoginMutation,
  useSlackSignupMutation,
} from '@/@hooks/@queries/user';
import { GoogleSignupRequest, SlackSignupRequest } from '@/types/user/remote';

export const useSlackSignUp = () => {
  const slackSignupMutate = useSlackSignupMutation();

  const slackSignup = ({ nickname, accessToken }: SlackSignupRequest) => {
    return slackSignupMutate.mutateAsync({
      nickname,
      accessToken,
    });
  };

  return {
    slackSignup,
  };
};

export const useGoogleSignUp = () => {
  const googleSignupMutate = useGoogleSignupMutation();

  const googleSignup = ({ nickname, accessToken }: GoogleSignupRequest) => {
    return googleSignupMutate.mutateAsync({
      nickname,
      accessToken,
    });
  };

  return {
    googleSignup,
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
  const loginMutate = useSlackLoginMutation();

  const loginBySlackOAuth = async (code: string) => {
    const response = await loginMutate.mutateAsync({ code });

    return response?.data;
  };

  return {
    loginBySlackOAuth,
  };
};

export const useGoogleOAuthLogin = () => {
  const loginMutate = useGoogleLoginMutation();

  const loginByGoogleOAuth = async (code: string) => {
    const response = await loginMutate.mutateAsync({ code });

    return response?.data;
  };

  return {
    loginByGoogleOAuth,
  };
};

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
