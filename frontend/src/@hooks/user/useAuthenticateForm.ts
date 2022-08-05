import { FormEventHandler } from 'react';
import { useNavigate } from 'react-router-dom';

import useInput from '@/@hooks/@common/useInput';
import { PATH } from '@/Router';

import { useJoinMutation, useLoginMutation } from '../@queries/user';

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

  const [email, onChangeEmail] = useInput(defaultEmail);
  const [password, onChangePassword] = useInput(defaultPassword);
  const [confirmPassword, onChangeConfirmPassword] = useInput(defaultConfirmPassword);
  const [name, onChangeName] = useInput(defaultName);

  const joinMutate = useJoinMutation();
  const loginMutate = useLoginMutation();

  const onSubmitJoinForm: FormEventHandler<HTMLFormElement> = e => {
    e.preventDefault();

    joinMutate.mutate(
      {
        email,
        password,
        nickname: name,
      },
      {
        onSuccess() {
          // navigate(PATH.JOIN);
        },
      }
    );
  };

  const onSubmitLoginForm: FormEventHandler<HTMLFormElement> = e => {
    e.preventDefault();

    loginMutate.mutate(
      {
        email,
        password,
      },
      {
        onSuccess() {
          navigate(PATH.LANDING);
        },
      }
    );
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
