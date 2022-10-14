import { FormEventHandler } from 'react';
import { useNavigate } from 'react-router-dom';

import useInput from '@/@hooks/@common/useInput';
import { useToast } from '@/@hooks/@common/useToast';
import { useOAuthSignup } from '@/@hooks/business/user';
import { PATH } from '@/Router';
import { OAuthType } from '@/types/user/client';
import { nicknameRegularExpression } from '@/utils/regularExpression';

type UseAuthenticateFormProps = {
  oAuthType?: OAuthType;
  defaultEmail?: string;
  defaultPassword?: string;
  defaultConfirmPassword?: string;
  defaultNickname?: string;
};

export const useAuthenticateForm = (props: UseAuthenticateFormProps = {}) => {
  const {
    oAuthType,
    defaultEmail = '',
    defaultPassword = '',
    defaultConfirmPassword = '',
    defaultNickname = '',
  } = props;

  const navigate = useNavigate();

  const { displayMessage } = useToast();

  const [email, onChangeEmail] = useInput(defaultEmail);
  const [password, onChangePassword] = useInput(defaultPassword);
  const [confirmPassword, onChangeConfirmPassword] = useInput(defaultConfirmPassword);
  const [nickname, onChangeNickname] = useInput(defaultNickname, [
    (value: string) => value.length > 6,
  ]);

  const { signupByOAuth: slackSignup } = useOAuthSignup('slack');
  const { signupByOAuth: googleSignup } = useOAuthSignup('google');

  const onSubmitJoinForm: FormEventHandler<HTMLFormElement> = async e => {
    e.preventDefault();

    const signupToken = localStorage.getItem('signup-token');

    if (!signupToken) {
      return;
    }

    if (!nickname.match(nicknameRegularExpression)) {
      displayMessage('잘못된 닉네임 형식입니다. (한글, 숫자, 영문자로 구성된 1~6글자)', true);

      return;
    }

    if (oAuthType === 'slack') {
      await slackSignup({ nickname, accessToken: signupToken });
    }

    if (oAuthType === 'google') {
      await googleSignup({ nickname, accessToken: signupToken });
    }

    navigate(PATH.MAIN, { replace: true });
  };

  // const onSubmitLoginForm: FormEventHandler<HTMLFormElement> = async e => {
  //   e.preventDefault();

  //   await login({ email, password });

  //   navigate(PATH.MAIN);
  // };

  return {
    state: {
      email,
      password,
      confirmPassword,
      nickname,
    },
    changeHandler: {
      onChangeEmail,
      onChangePassword,
      onChangeConfirmPassword,
      onChangeNickname,
    },
    submitHandler: {
      join: onSubmitJoinForm,
      // login: onSubmitLoginForm,
    },
  };
};
