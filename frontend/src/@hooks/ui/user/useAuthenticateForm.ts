import { FormEventHandler } from 'react';
import { useNavigate } from 'react-router-dom';

import useInput from '@/@hooks/@common/useInput';
import { useToast } from '@/@hooks/@common/useToast';
import { useLogin, useSlackSignUp } from '@/@hooks/business/user';
import { PATH } from '@/Router';
import { nicknameRegularExpression } from '@/utils/regularExpression';

type UseAuthenticateFormProps = {
  defaultEmail?: string;
  defaultPassword?: string;
  defaultConfirmPassword?: string;
  defaultName?: string;
};

export const useAuthenticateForm = (props: UseAuthenticateFormProps = {}) => {
  const {
    defaultEmail = '',
    defaultPassword = '',
    defaultConfirmPassword = '',
    defaultName = '',
  } = props;

  const navigate = useNavigate();

  const { displayMessage } = useToast();

  const [email, onChangeEmail] = useInput(defaultEmail);
  const [password, onChangePassword] = useInput(defaultPassword);
  const [confirmPassword, onChangeConfirmPassword] = useInput(defaultConfirmPassword);
  const [name, onChangeName] = useInput(defaultName, [(value: string) => value.length > 6]);

  const { slackSignup } = useSlackSignUp();
  const { login } = useLogin();

  const onSubmitJoinForm: FormEventHandler<HTMLFormElement> = async e => {
    e.preventDefault();

    const slackSignupToken = localStorage.getItem('slack-signup-token');

    if (!slackSignupToken) {
      return;
    }

    if (!name.match(nicknameRegularExpression)) {
      displayMessage('잘못된 닉네임 형식입니다. (한글, 숫자, 영문자로 구성된 1~6글자)', true);

      return;
    }

    await slackSignup({ name, slackSignupToken });

    navigate(PATH.LANDING);
  };

  const onSubmitLoginForm: FormEventHandler<HTMLFormElement> = async e => {
    e.preventDefault();

    await login({ email, password });

    navigate(PATH.LANDING);
  };

  return {
    state: {
      email,
      password,
      confirmPassword,
      name,
    },
    changeHandler: {
      onChangeEmail,
      onChangePassword,
      onChangeConfirmPassword,
      onChangeName,
    },
    submitHandler: {
      join: onSubmitJoinForm,
      login: onSubmitLoginForm,
    },
  };
};
